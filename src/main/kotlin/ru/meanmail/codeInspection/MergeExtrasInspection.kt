package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.canonicalizeName
import ru.meanmail.psi.NameReq
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.quickfix.MergeExtrasQuickFix


class MergeExtrasInspection : RequirementsInspection() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor {
        return MergeExtrasInspectionVisitor(holder, isOnTheFly, session)
    }
}

private const val MAX_LENGTH: Int = 32

private class MergeExtrasInspectionVisitor(
    holder: ProblemsHolder,
    onTheFly: Boolean,
    session: LocalInspectionToolSession
) : BaseInspectionVisitor(holder, onTheFly, session) {
    override fun visitRequirementsFile(element: RequirementsFile) {
        val names = mutableMapOf<String, Int>()
        val psiDocumentManager = PsiDocumentManager.getInstance(element.project)
        val document = psiDocumentManager.getDocument(element.containingFile)
        val requirements = element.enabledRequirements().mapNotNull {
            it as? NameReq
        }.filter {
            it.extras != null
        }

        for (req in requirements) {
            val text = canonicalizeName(req.displayName)
            val name = if (text.length <= MAX_LENGTH) {
                text
            } else {
                text.substring(0, MAX_LENGTH - 3) + "..."
            }
            val textOffset = req.textOffset
            val lineNumber = document!!.getLineNumber(textOffset) + 1

            if (text in names) {
                val line = names[text]

                val message = "Merge '$name' in line $lineNumber with line $line"
                holder.registerProblem(
                    req, message,
                    MergeExtrasQuickFix(req, "Merge package extras")
                )
            } else {
                names[text] = lineNumber
            }
        }
    }
}
