package ru.meanmail

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase
import ru.meanmail.psi.RelativeRef
import ru.meanmail.psi.UriReference

class Reference(uriReference: UriReference) :
    PsiReferenceBase<UriReference>(uriReference, null, false) {
    override fun resolve(): PsiElement? {
        val relativeRef = element.relativeRef

        if (relativeRef != null) {
            return resolveFile(relativeRef)
        }

        return null
    }

    private fun resolveFile(relativeRef: RelativeRef): PsiElement? {
        val directory = element.containingFile
            ?.containingDirectory?.virtualFile ?: return null
        val file = resolveFile(relativeRef.text, directory) ?: return null

        return PsiManager.getInstance(element.project).findFile(file)
    }

    override fun getRangeInElement(): TextRange {
        return TextRange(0, element.textLength)
    }

    override fun handleElementRename(newElementName: String): PsiElement? {
        val newElement = element.setName(newElementName)
        element.parent.node.replaceChild(element.node, newElement.node)
        return newElement
    }
}
