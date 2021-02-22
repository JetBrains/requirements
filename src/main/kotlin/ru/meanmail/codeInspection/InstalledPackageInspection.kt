package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.python.packaging.PyPackageVersion
import com.jetbrains.python.packaging.PyPackageVersionNormalizer
import ru.meanmail.compareTo
import ru.meanmail.getInstalledVersion
import ru.meanmail.getMarkers
import ru.meanmail.psi.NameReq
import ru.meanmail.pypi.getPythonVersion
import ru.meanmail.pypi.getVersionsAsync
import ru.meanmail.quickfix.InstallPackageQuickFix

class InstalledPackageInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor {
        return Visitor(holder, isOnTheFly, session)
    }

    companion object {
        class Visitor(
            holder: ProblemsHolder,
            onTheFly: Boolean,
            session: LocalInspectionToolSession
        ) :
            BaseInspectionVisitor(holder, onTheFly, session) {

            private fun isVersionActual(
                element: NameReq,
                installedVersion: PyPackageVersion
            ): Boolean {
                return element.versionspec
                    ?.isActual(installedVersion.presentableText) ?: return true
            }

            override fun visitNameReq(element: NameReq) {
                val markers = getMarkers(element.project)
                if (!element.enabled(markers)) {
                    return
                }
                val packageName = element.name.text ?: return
                val pythonVersion = getPythonVersion(markers)
                val task = getVersionsAsync(
                    element.project, packageName, pythonVersion
                )
                val versions = task.get() ?: return

                val suitableVersion: PyPackageVersion? = versions.find {
                    it.pre == null && isVersionActual(element, it)
                }

                val latest = versions.firstOrNull {
                    it.pre == null
                }

                val installed = getInstalledVersion(element.project, packageName)

                val versionOneList = element.versionspec?.versionMany?.versionOneList
                var exactRequired: PyPackageVersion? = null
                if (versionOneList?.size == 1 && versionOneList[0].versionCmp.isExact) {
                    exactRequired = PyPackageVersionNormalizer.normalize(versionOneList[0].version.text)
                }

                if (exactRequired != null) {
                    if (exactRequired != installed) {
                        exactRequiredIsNotInstalled(element, packageName, exactRequired, installed, latest)
                    }

                    if (latest != null && exactRequired != latest) {
                        exactRequiredIsOutdated(element, packageName, exactRequired, installed, latest)
                    }
                } else {
                    if (suitableVersion != null && installed < suitableVersion) {
                        requiredIsNotInstalled(element, packageName, installed, latest, suitableVersion)
                    }
                    if (latest != null && latest != suitableVersion && installed < latest) {
                        requiredIsOutdated(element, packageName, installed, latest)
                    }
                }

                if (suitableVersion == null) {
                    noSuitableVersion(element, packageName, installed, latest, versions)
                }
            }

            private fun exactRequiredIsNotInstalled(
                element: NameReq,
                packageName: @NlsSafe String,
                exactRequired: PyPackageVersion,
                installed: PyPackageVersion?,
                latest: PyPackageVersion?
            ) {
                val versionsInfo = getVersionsInfo(
                    exactRequired.presentableText, installed, latest
                )
                val message = "'$packageName' version '" +
                        "${exactRequired.presentableText}' " +
                        "is not installed ($versionsInfo)"
                holder.registerProblem(
                    element,
                    message,
                    InstallPackageQuickFix(
                        element,
                        "Install '$packageName' " +
                                "version '${exactRequired.presentableText}' " +
                                "($versionsInfo)",
                        exactRequired.presentableText
                    )
                )
            }

            private fun exactRequiredIsOutdated(
                element: NameReq,
                packageName: @NlsSafe String,
                exactRequired: PyPackageVersion,
                installed: PyPackageVersion?,
                latest: PyPackageVersion
            ) {
                val versionsInfo = getVersionsInfo(
                    exactRequired.presentableText, installed, latest
                )

                val message = "'$packageName' version '" +
                        "${exactRequired.presentableText}' " +
                        "is outdated ($versionsInfo)"
                holder.registerProblem(
                    element,
                    message,
                    InstallPackageQuickFix(
                        element,
                        "Upgrade '$packageName' " +
                                "to version '${latest.presentableText}' " +
                                "and set new version " +
                                "($versionsInfo)",
                        latest.presentableText,
                        true
                    )
                )
            }

            private fun requiredIsNotInstalled(
                element: NameReq,
                packageName: @NlsSafe String,
                installed: PyPackageVersion?,
                latest: PyPackageVersion?,
                suitableVersion: PyPackageVersion
            ) {
                val versionsInfo = getVersionsInfo(
                    element.versionspec?.text, installed, latest
                )
                val prefix = if (installed != null) {
                    "'$packageName${installed.presentableText}'"
                } else {
                    "'$packageName'"
                }

                holder.registerProblem(
                    element,
                    "$prefix is not installed ($versionsInfo)",
                    InstallPackageQuickFix(
                        element,
                        "Install '$packageName' " +
                                "version '${suitableVersion.presentableText}' " +
                                "($versionsInfo)",
                        suitableVersion.presentableText
                    )
                )
            }

            private fun requiredIsOutdated(
                element: NameReq,
                packageName: @NlsSafe String,
                installed: PyPackageVersion?,
                latest: PyPackageVersion
            ) {
                val versionsInfo = getVersionsInfo(
                    element.versionspec?.text, installed, latest
                )
                val prefix = if (installed != null) {
                    "'$packageName${installed.presentableText}'"
                } else {
                    "'$packageName'"
                }
                val message = "$prefix is outdated ($versionsInfo)"
                holder.registerProblem(
                    element,
                    message,
                    InstallPackageQuickFix(
                        element,
                        "Upgrade '$packageName' " +
                                "to version '${latest.presentableText}' " +
                                "and set new version " +
                                "($versionsInfo)",
                        latest.presentableText,
                        true
                    )
                )
            }

            private fun noSuitableVersion(
                element: NameReq,
                packageName: @NlsSafe String,
                installed: PyPackageVersion?,
                latest: PyPackageVersion?,
                versions: List<PyPackageVersion>
            ) {
                val versionsInfo = getVersionsInfo(
                    element.versionspec?.text, installed, latest
                )
                val errorMessage = if (versions.isEmpty()) {
                    "Package not found:"
                } else {
                    "No matching package version found:"
                }
                val fixes = if (latest != null) {
                    arrayOf(
                        InstallPackageQuickFix(
                            element,
                            "Install '$packageName' " +
                                    "version '${latest.presentableText}' " +
                                    "and set new version " +
                                    "($versionsInfo)",
                            latest.presentableText,
                            true
                        )
                    )
                } else {
                    emptyArray()
                }
                holder.registerProblem(
                    element,
                    "$errorMessage '$packageName${element.versionspec?.text}' ($versionsInfo)",
                    ProblemHighlightType.ERROR,
                    *fixes
                )
            }

            private fun getVersionsInfo(
                required: String?,
                installed: PyPackageVersion?,
                latest: PyPackageVersion?
            ): String {
                return "required: ${required ?: "<unknown>"}, " +
                        "installed: ${installed?.presentableText ?: "<nothing>"}, " +
                        "latest: ${latest?.presentableText ?: "<nothing>"}"
            }


        }
    }
}
