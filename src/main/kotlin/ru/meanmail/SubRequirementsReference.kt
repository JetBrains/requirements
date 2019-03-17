package ru.meanmail

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.jetbrains.extensions.python.toPsi
import ru.meanmail.psi.RequirementsFilenameStmt

class SubRequirementsReference(pathStmt: RequirementsFilenameStmt) :
        PsiReferenceBase<RequirementsFilenameStmt>(pathStmt, null, false) {
    override fun resolve(): PsiElement? {
        val filename = myElement.filename ?: return null
        val directory = myElement.containingFile.containingDirectory.virtualFile
        return directory.findFileByRelativePath(filename)?.toPsi(myElement.project)
    }
    
    override fun getRangeInElement(): TextRange {
        return TextRange(0, myElement.textLength)
    }
}
