package ru.meanmail

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.jetbrains.extensions.python.toPsi
import ru.meanmail.psi.UriReference

class Reference(uriReference: UriReference) :
        PsiReferenceBase<UriReference>(uriReference, null, false) {
    override fun resolve(): PsiElement? {
        val relativeRef = element.relativeRef ?: return null
        val directory = element.containingFile.containingDirectory.virtualFile
        val file = resolveFile(relativeRef.text, directory)
        return file?.toPsi(element.project)
    }

    override fun getRangeInElement(): TextRange {
        return TextRange(0, element.textLength)
    }
}
