package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.canonicalizeName
import ru.meanmail.notification.Notifier
import ru.meanmail.psi.NameReq
import ru.meanmail.psi.Requirement
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.quickfix.RemoveUnusedQuickFix


class DuplicatedInspection : RequirementsInspection() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor {
        return DuplicatedInspectionVisitor(holder, isOnTheFly, session)
    }
}


private class DuplicatedInspectionVisitor(
    holder: ProblemsHolder,
    onTheFly: Boolean,
    session: LocalInspectionToolSession
) : BaseInspectionVisitor(holder, onTheFly, session) {
    override fun visitRequirementsFile(element: RequirementsFile) {
        val names = mutableMapOf<String, Int>()
        val psiDocumentManager = PsiDocumentManager.getInstance(element.project)
        val document = psiDocumentManager.getDocument(element.containingFile)
        element.enabledRequirements().mapNotNull {
            it as? NameReq
        }.filter {
            it.extras != null
        }.forEach {
            names.putIfAbsent(getName(it), getLineNumber(it, document))
        }

        for (req in element.enabledRequirements()) {
            val name = getName(req)
            val type = req.type.kind
            val lineNumber = getLineNumber(req, document)

            if (name in names) {
                if ((req as? NameReq)?.extras == null) {
                    val line = names[name]
                    val message = "The '$name' $type on line $lineNumber " +
                            "is already defined on line $line"
                    holder.registerProblem(
                        req, message,
                        RemoveUnusedQuickFix(req, "Remove duplicate")
                    )
                }
            } else {
                names[name] = lineNumber
            }
        }
    }

    private fun getLineNumber(req: Requirement, document: Document?): Int {
        val textOffset = req.textOffset
        val lineNumber = document!!.getLineNumber(textOffset) + 1
        return lineNumber
    }

    private fun getName(req: Requirement): String {
        val name = if (req is NameReq) {
            canonicalizeName(req.displayName)
        } else {
            req.displayName
        }
        return name
    }
}
