package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import ru.meanmail.canonicalizeName
import ru.meanmail.deleteElementWithEol
import ru.meanmail.fileTypes.createExtras
import ru.meanmail.psi.NameReq
import ru.meanmail.psi.Requirement
import ru.meanmail.psi.RequirementsFile

class MergeExtrasQuickFix(
    element: Requirement,
    private val description: String
) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }

    override fun invoke(
        project: Project,
        file: PsiFile,
        startElement: PsiElement,
        endElement: PsiElement
    ) {
        if (startElement !is NameReq) {
            return
        }
        val packageName = canonicalizeName(startElement.displayName)
        val firstRequirement = (startElement.containingFile as RequirementsFile).enabledRequirements().mapNotNull {
            it as? NameReq
        }.first {
            it.extras != null && canonicalizeName(it.displayName) == packageName && it != startElement
        }
        val extras = setOf(
            *(firstRequirement.extras?.extrasList?.text?.split(',')?.toTypedArray() ?: emptyArray()),
            *(startElement.extras?.extrasList?.text?.split(',')?.toTypedArray() ?: emptyArray())
        )
        ApplicationManager.getApplication().invokeLater {
            WriteAction.run<Throwable> {
                WriteCommandAction.runWriteCommandAction(project,
                    text, "Requirements", {
                        val newExtras = createExtras(project, extras.sorted())?.node
                        if (newExtras != null) {
                            val oldExtras = firstRequirement.extras?.node
                            val node = firstRequirement.node
                            if (oldExtras == null) {
                                node.addChild(newExtras)
                            } else {
                                node.replaceChild(oldExtras, newExtras)
                            }
                            deleteElementWithEol(startElement)
                        }
                    })
            }
        }
    }

    override fun getFamilyName(): String {
        return "Merge package extras"
    }
}
