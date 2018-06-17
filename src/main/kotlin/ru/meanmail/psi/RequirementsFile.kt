package ru.meanmail.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import ru.meanmail.RequirementsFileType
import ru.meanmail.RequirementsLanguage

class RequirementsFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, RequirementsLanguage) {
    
    override fun getFileType(): FileType {
        return RequirementsFileType
    }
    
    override fun toString(): String {
        return "Requirements File"
    }
    
}
