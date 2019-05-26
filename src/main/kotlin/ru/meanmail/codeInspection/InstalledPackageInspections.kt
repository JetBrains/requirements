package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.annotations.Nls
import ru.meanmail.getCurrentVersion
import ru.meanmail.getLatestVersion
import ru.meanmail.isInstalled
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
                val project = element.project
                val packageName = element.packageName ?: return
                val version = element.version
                
                val task = buildDescription(project, packageName, version)
                
                val description = task.get() ?: return
                
                holder.registerProblem(element, description,
                        InstallPackageQuickFix(element, description))
            }
            
            private fun buildDescription(project: Project,
                                         packageName: String,
                                         version: String?): Future<String?> {
                return ApplicationManager.getApplication()
                        .executeOnPooledThread<String?> {
                            if (isInstalled(project, packageName, version)) {
                                return@executeOnPooledThread null
                            }
                            
                            var description = "Install '$packageName'"
                            if (version != null) {
                                description += " version '$version'"
                            }
                            
                            val installedVersion = getCurrentVersion(project, packageName)
                            
                            val versions = mutableListOf<String>()
                            var installed = "installed: "
                            installed += if (installedVersion != null) {
                                "'${installedVersion.presentableText}'"
                            } else {
                                "<not installed>"
                            }
                            
                            versions.add(installed)
                            
                            val latestVersion = getLatestVersion(project, packageName)
                            
                            var latest = "latest: "
                            latest += if (latestVersion != null) {
                                "'${latestVersion.presentableText}'"
                            } else {
                                "<unknown>"
                            }
                            
                            versions.add(latest)
                            val joined = versions.joinToString(prefix = "(", postfix = ")")
                            description += " $joined"
                            
                            return@executeOnPooledThread description
                        }
            }
        }
    }
}
