package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.getInstalledVersion
import ru.meanmail.psi.NameReq
import ru.meanmail.quickfix.UninstallPackageQuickFix

class UninstalledPackageInspection : LocalInspectionTool() {
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

            override fun visitNameReq(element: NameReq) {
                val packageName = element.name.text ?: return
                val installed = getInstalledVersion(element.project, packageName)

                if (installed != null) {
                    val message = "Uninstall '$packageName' "
                    holder.registerProblem(
                        element,
                        message,
                        UninstallPackageQuickFix(element, message)
                    )
                }
            }
        }
    }
}
