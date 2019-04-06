package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface RequirementsPackageStmt : RequirementsNamedElement {
    
    val extra: RequirementsExtra?
    
    val simplePackageStmt: RequirementsSimplePackageStmt
    
    val packageName: String?
    
    val relation: String?
    
    val version: String?
    
    val extraPackage: String?
    
    override fun getName(): String?
    
    override fun setName(newName: String): PsiElement
    
    override fun getNameIdentifier(): PsiElement?

}
