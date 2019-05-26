package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.python.packaging.PyPackageVersion
import org.jetbrains.annotations.Nls
import ru.meanmail.compareTo
import ru.meanmail.getVersions
import ru.meanmail.psi.RequirementsPackageStmt
import ru.meanmail.quickfix.InstallPackageQuickFix
import java.util.concurrent.Future

class InstalledPackageInspections : LocalInspectionTool() {
    @Nls
    override fun getDisplayName(): String {
        return "Package is not installed"
    }
    
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
            
            override fun visitPackageStmt(element: RequirementsPackageStmt) {
                val packageName = element.packageName ?: return
                
                val task = getVersionAsync(element.project,
                        packageName, element.version)
                val versions = task.get() ?: return
                
                val required = versions.first
                val installed = versions.second
                val latest = versions.third
                
                val versionsRepresentation =
                        "required: ${required?.presentableText ?: "<unknown>"}, " +
                                "installed: ${installed?.presentableText ?: "<nothing>"}, " +
                                "latest: ${latest?.presentableText ?: "<unknown>"}"
                
                
                if (required != null && required != installed) {
                    val message = "'$packageName' version '${required.presentableText}' " +
                            "is not installed ($versionsRepresentation)"
                    holder.registerProblem(element,
                            message,
                            InstallPackageQuickFix(element,
                                    "Install '${required.presentableText}' " +
                                            "version ($versionsRepresentation)",
                                    required.presentableText))
                }
                
                if (latest != null && required != null && required < latest) {
                    val message = "'$packageName' version '${required.presentableText}' " +
                            "is outdated ($versionsRepresentation)"
                    holder.registerProblem(element,
                            message,
                            InstallPackageQuickFix(element,
                                    "Install '${latest.presentableText}' " +
                                            "version ($versionsRepresentation)",
                                    latest.presentableText))
                }
            }
            
            private fun getVersionAsync(project: Project,
                                        packageName: String,
                                        version: String?): Future<Triple<PyPackageVersion?, PyPackageVersion?, PyPackageVersion?>> {
                return ApplicationManager.getApplication()
                        .executeOnPooledThread<Triple<PyPackageVersion?, PyPackageVersion?, PyPackageVersion?>> {
                            return@executeOnPooledThread getVersions(project, packageName, version)
                        }
            }
        }
    }
}
