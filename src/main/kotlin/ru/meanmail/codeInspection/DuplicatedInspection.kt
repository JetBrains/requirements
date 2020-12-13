package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.quickfix.RemoveUnusedQuickFix


class DuplicatedInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor {
        return Visitor(holder, isOnTheFly, session)
    }

    companion object {
        const val MAX_LENGTH: Int = 32

        class Visitor(
            holder: ProblemsHolder,
            onTheFly: Boolean,
            session: LocalInspectionToolSession
        ) :
            BaseInspectionVisitor(holder, onTheFly, session) {

            override fun visitRequirementsFile(element: RequirementsFile) {
                val names = mutableMapOf<String, Int>()
                val psiDocumentManager = PsiDocumentManager.getInstance(element.project)
                val document = psiDocumentManager.getDocument(element.containingFile)

                for (req in element.enabledRequirements()) {
                    val text = req.displayName
                    val name = if (text.length <= MAX_LENGTH) {
                        text
                    } else {
                        text.substring(0, MAX_LENGTH - 3) + "..."
                    }
                    val type = req.type.kind
                    val textOffset = req.textOffset
                    val lineNumber = document!!.getLineNumber(textOffset) + 1

                    if (text in names) {
                        val line = names[text]

                        val message = "The '$name' $type on line $lineNumber " +
                                "is already defined on line $line"
                        holder.registerProblem(
                            req, message,
                            RemoveUnusedQuickFix(req, "Remove duplicate")
                        )
                    } else {
                        names[text] = lineNumber
                    }
                }
            }
        }
    }
}
