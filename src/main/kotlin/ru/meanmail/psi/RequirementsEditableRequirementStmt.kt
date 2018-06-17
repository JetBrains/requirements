package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface RequirementsEditableRequirementStmt : RequirementsNamedElement {

    val urlStmt: RequirementsUrlStmt

    val url: String?

    val branch: String?

    val egg: String?

    override fun getName(): String?

    override fun setName(newName: String): PsiElement
    
    override fun getNameIdentifier(): PsiElement?

}
