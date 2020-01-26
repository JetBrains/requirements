package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.annotations.Nls
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.quickfix.RemoveUnusedQuickFix


class UnusedInspection : LocalInspectionTool() {
    @Nls
    override fun getDisplayName(): String {
        return "Unused package"
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

            override fun visitRequirementsFile(element: RequirementsFile) {
                for (req in element.disabledRequirements()) {
                    val message = "Unused ${req.displayName}"
                    holder.registerProblem(req, message,
                            ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                            RemoveUnusedQuickFix(req, "Remove unused: ${req.displayName}"))
                }
            }
        }
    }
}
