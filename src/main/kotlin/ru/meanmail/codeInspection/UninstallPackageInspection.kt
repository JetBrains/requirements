package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.getInstalledVersion
import ru.meanmail.getPythonSdk
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
                if (!onTheFly) {
                    return
                }
                val packageName = element.name.text ?: return
                val sdk = getPythonSdk(holder.file) ?: return
                val installed = getInstalledVersion(sdk, packageName)

                if (installed != null) {
                    val message = "Uninstall '$packageName'"
                    holder.registerProblem(
                        element,
                        message,
                        ProblemHighlightType.INFORMATION,
                        UninstallPackageQuickFix(element, message)
                    )
                }
            }
        }
    }
}
