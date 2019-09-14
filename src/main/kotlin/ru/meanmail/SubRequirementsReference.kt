package ru.meanmail

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.jetbrains.extensions.python.toPsi

//class SubRequirementsReference(pathStmt: FilenameStmt) :
//        PsiReferenceBase<FilenameStmt>(pathStmt, null, false) {
//    override fun resolve(): PsiElement? {
//        val filename = element.filename ?: return null
//        val directory = element.containingFile.containingDirectory.virtualFile
//        val file = resolveFile(filename, directory)
//        return file?.toPsi(element.project)
//    }
//
//    override fun getRangeInElement(): TextRange {
//        return TextRange(0, element.textLength)
//    }
//}
