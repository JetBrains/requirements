package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import ru.meanmail.deleteElementWithEol
import ru.meanmail.psi.Requirement

class RemoveUnusedQuickFix(
    element: Requirement,
    private val description: String
) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }

    override fun invoke(
        project: Project, file: PsiFile,
        startElement: PsiElement,
        endElement: PsiElement
    ) {
        ApplicationManager.getApplication().invokeLater {
            WriteAction.run<Throwable> {
                WriteCommandAction.runWriteCommandAction(project,
                    text, "Requirements", {
                        deleteElementWithEol(startElement)
                    })
            }
        }
    }

    override fun getFamilyName(): String {
        return "Remove unused"
    }
}
