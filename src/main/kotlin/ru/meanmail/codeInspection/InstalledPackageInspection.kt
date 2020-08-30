package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.python.packaging.PyPackage
import com.jetbrains.python.packaging.PyPackageVersion
import com.jetbrains.python.packaging.PyPackageVersionNormalizer
import com.jetbrains.python.packaging.PyRequirementParser
import ru.meanmail.compareTo
import ru.meanmail.getMarkers
import ru.meanmail.getVersions
import ru.meanmail.psi.NameReq
import ru.meanmail.quickfix.InstallPackageQuickFix
import java.util.concurrent.Future

class InstalledPackageInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean,
                              session: LocalInspectionToolSession): PsiElementVisitor {
        return Visitor(holder, isOnTheFly, session)
    }

    companion object {
        class Visitor(holder: ProblemsHolder,
                      onTheFly: Boolean,
                      session: LocalInspectionToolSession) :
                BaseInspectionVisitor(holder, onTheFly, session) {

            override fun visitNameReq(element: NameReq) {
                if (!element.enabled(getMarkers(element.project))) {
                    return
                }

                val packageName = element.name.text ?: return
                val versionSpec = element.versionspec
                val requirement = PyRequirementParser.fromLine(element.text)

                val task = getVersionAsync(element.project, packageName)
                val versions = task.get() ?: return
                val installed = versions.first
                val latest = versions.second

                val versionsRepresentation =
                        "required: ${versionSpec?.text ?: "<unknown>"}, " +
                                "installed: ${installed?.version ?: "<nothing>"}, " +
                                "latest: ${latest?.presentableText ?: "<unknown>"}"


                if (requirement != null && installed?.matches(requirement) != true) {
                    val message = "'${requirement.presentableText}' " +
                            "is not installed ($versionsRepresentation)"
                    holder.registerProblem(element,
                            message,
                            InstallPackageQuickFix(element,
                                    "Install '${requirement.presentableText}' " +
                                            "($versionsRepresentation)",
                                    requirement))

                } else if (latest != null && installed != null && PyPackageVersionNormalizer.normalize(installed.version) < latest) {
                    val message = "'$packageName' version '${installed.version}' " +
                            "is outdated ($versionsRepresentation)"
                    holder.registerProblem(element,
                            message,
                            InstallPackageQuickFix(element,
                                    "Install '$packageName' " +
                                            "version '${latest.presentableText}' " +
                                            "($versionsRepresentation)",
                                    PyRequirementParser.fromLine("$packageName==${latest.presentableText}")!!))
                }
            }

            private fun getVersionAsync(project: Project,
                                        packageName: String): Future<Pair<PyPackage?, PyPackageVersion?>> {
                return ApplicationManager.getApplication()
                        .executeOnPooledThread<Pair<PyPackage?, PyPackageVersion?>> {
                            return@executeOnPooledThread getVersions(project, packageName)
                        }
            }
        }
    }
}
