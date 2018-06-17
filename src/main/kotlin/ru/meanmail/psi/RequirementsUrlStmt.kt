package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface RequirementsUrlStmt : RequirementsNamedElement {
    
    val pathStmt: RequirementsPathStmt
    
    val simplePackageStmt: RequirementsSimplePackageStmt?
    
    val url: String?
    
    val branch: String?
    
    val egg: String?
    
    override fun getName(): String?
    
    override fun setName(newName: String): PsiElement
    
    override fun getNameIdentifier(): PsiElement?
    
}
