package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface RequirementsPackageNameStmt : RequirementsNamedElement {
    
    val packageName: String?
    
    override fun getName(): String?
    
    override fun setName(newName: String): PsiElement
    
    override fun getNameIdentifier(): PsiElement?
    
}
