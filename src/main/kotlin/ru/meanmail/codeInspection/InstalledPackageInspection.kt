package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.application.ReadAction
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.python.packaging.PyPackageVersion
import ru.meanmail.compareTo
import ru.meanmail.getInstalledVersion
import ru.meanmail.psi.NameReq
import ru.meanmail.pypi.getVersionsAsync
import ru.meanmail.quickfix.InstallPackageQuickFix

class InstalledPackageInspection : RequirementsInspection() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor {
        return InstalledPackageInspectionVisitor(holder, isOnTheFly, session)
    }
}

private class InstalledPackageInspectionVisitor(
    holder: ProblemsHolder,
    onTheFly: Boolean,
    session: LocalInspectionToolSession
) : BaseInspectionVisitor(holder, onTheFly, session) {
    override fun visitNameReq(element: NameReq) {
        val sdk = sdk ?: return
        val pythonInfo = pythonInfo ?: return
        if (!element.enabled(pythonInfo.map)) {
            return
        }
        val packageName = ReadAction.compute<String?, Throwable> {
            element.name.text
        } ?: return
        val project = holder.project
        val task = getVersionsAsync(project, sdk, packageName)
        val versions = task.get() ?: return
        val suitableVersion: PyPackageVersion? = versions.find {
            isVersionActual(element, it)
        }

        val latest = versions.firstOrNull {
            it.pre == null
        }

        val installed = getInstalledVersion(sdk, packageName)
        val isInstalledIsActual = installed != null && isVersionActual(element, installed)
        val versionsInfo = getVersionsInfo(
            suitableVersion, installed, latest
        )

        if (suitableVersion == null) {
            if (latest != null) {
                noSuitableVersion(element, packageName, versionsInfo, latest)
            } else {
                noSuitablePackage(element, packageName)
            }
        } else {
            if (isInstalledIsActual) {
                if (installed < suitableVersion) {
                    installedPackageIsOutdated(
                        element, packageName, versionsInfo, installed, suitableVersion
                    )
                }
            } else {
                requiredPackageIsNotInstalled(
                    element, packageName, versionsInfo, suitableVersion
                )
            }
        }
        if (latest != null && suitableVersion < latest) {
            requirementIsOutdated(
                element, packageName, versionsInfo, suitableVersion, latest
            )
        }
    }

    private fun requirementIsOutdated(
        element: NameReq,
        packageName: String,
        versionsInfo: String,
        required: PyPackageVersion?,
        latest: PyPackageVersion
    ) {
        val message = "'$packageName " +
                "${required?.presentableText}': " +
                "the required version is outdated ($versionsInfo)"
        holder.registerProblem(
            element,
            message,
            ProblemHighlightType.WEAK_WARNING,
            InstallPackageQuickFix(
                element,
                "Upgrade '$packageName' requirement " +
                        "to '==${latest.presentableText}' " +
                        "and install the latest version " +
                        "($versionsInfo)",
                latest.presentableText,
                true
            )
        )
    }

    private fun requiredPackageIsNotInstalled(
        element: NameReq,
        packageName: String,
        versionsInfo: String,
        suitableVersion: PyPackageVersion
    ) {
        holder.registerProblem(
            element,
            "'$packageName ${suitableVersion.presentableText}' " +
                    "is not installed ($versionsInfo)",
            ProblemHighlightType.WARNING,
            InstallPackageQuickFix(
                element,
                "Install '$packageName' " +
                        "version '${suitableVersion.presentableText}' " +
                        "($versionsInfo)",
                suitableVersion.presentableText
            )
        )
    }

    private fun installedPackageIsOutdated(
        element: NameReq,
        packageName: String,
        versionsInfo: String,
        installed: PyPackageVersion?,
        latest: PyPackageVersion
    ) {
        val prefix = if (installed != null) {
            "'$packageName ${installed.presentableText}'"
        } else {
            "'$packageName'"
        }
        val message = "$prefix: installed package is outdated ($versionsInfo)"
        holder.registerProblem(
            element,
            message,
            ProblemHighlightType.WEAK_WARNING,
            InstallPackageQuickFix(
                element,
                "Upgrade installed '$packageName' " +
                        "to version '${latest.presentableText}' " +
                        "($versionsInfo)",
                latest.presentableText,
                true
            )
        )
    }

    private fun noSuitableVersion(
        element: NameReq,
        packageName: String,
        versionsInfo: String,
        latest: PyPackageVersion
    ) {
        holder.registerProblem(
            element,
            "No matching package version found: '$packageName${element.versionspec?.text}' ($versionsInfo)",
            ProblemHighlightType.ERROR,
            InstallPackageQuickFix(
                element,
                "Install '$packageName' " +
                        "version '${latest.presentableText}' " +
                        "and set latest version " +
                        "($versionsInfo)",
                latest.presentableText,
                true
            )
        )
    }

    private fun noSuitablePackage(
        element: NameReq,
        packageName: String
    ) {
        holder.registerProblem(
            element,
            "Package not found: '$packageName'",
            ProblemHighlightType.ERROR
        )
    }

    private fun getVersionsInfo(
        required: PyPackageVersion?,
        installed: PyPackageVersion?,
        latest: PyPackageVersion?
    ): String {
        return "required: ${required?.presentableText ?: "<nothing>"}, " +
                "installed: ${installed?.presentableText ?: "<nothing>"}, " +
                "latest: ${latest?.presentableText ?: "<nothing>"}"
    }

}

private fun isVersionActual(
    element: NameReq,
    installedVersion: PyPackageVersion
): Boolean {
    return element.versionspec?.isActual(installedVersion.presentableText) ?: true
}
