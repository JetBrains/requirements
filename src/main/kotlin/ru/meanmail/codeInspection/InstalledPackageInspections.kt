package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.annotations.Nls
import ru.meanmail.isInstalled
import ru.meanmail.psi.RequirementsPackageStmt
import ru.meanmail.quickfix.InstallPackageQuickFix

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
                val packageName = element.packageName
                val version = element.version
                
                if (!isInstalled(project, packageName, version)) {
                    var description = "Install '$packageName'"
                    if (element.version != null) {
                        description += " version '$version'"
                    }
                    holder.registerProblem(element, description,
                            InstallPackageQuickFix(element))
                }
            }
        }
    }
}
