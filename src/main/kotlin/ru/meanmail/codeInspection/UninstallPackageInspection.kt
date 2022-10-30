package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.isInstalled
import ru.meanmail.psi.NameReq
import ru.meanmail.quickfix.UninstallPackageQuickFix

class UninstalledPackageInspection : RequirementsInspection() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor {
        return UninstalledPackageInspectionVisitor(holder, isOnTheFly, session)
    }
}

class UninstalledPackageInspectionVisitor(
    holder: ProblemsHolder,
    onTheFly: Boolean,
    session: LocalInspectionToolSession
) : BaseInspectionVisitor(holder, onTheFly, session) {
    override fun visitNameReq(element: NameReq) {
        if (!onTheFly) {
            return
        }
        val packageName = element.name.text ?: return
        val sdk = sdk ?: return
        if (!isInstalled(sdk, packageName)) {
            return
        }

        val message = "Uninstall '$packageName'"
        holder.registerProblem(
            element,
            message,
            ProblemHighlightType.INFORMATION,
            UninstallPackageQuickFix(element, message)
        )
    }
}
