package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface RequirementsVersionStmt : RequirementsNamedElement {
    
    val version: String?
    
    fun setVersion(newVersion: String)
    
    override fun getName(): String?
    
    override fun setName(newName: String): PsiElement
    
    override fun getNameIdentifier(): PsiElement?
    
}
