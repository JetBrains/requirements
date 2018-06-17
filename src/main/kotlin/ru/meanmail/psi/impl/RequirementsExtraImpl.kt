package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.RequirementsExtra
import ru.meanmail.psi.RequirementsVisitor

class RequirementsExtraImpl(node: ASTNode) : ASTWrapperPsiElement(node), RequirementsExtra {
    
    fun accept(visitor: RequirementsVisitor) {
        visitor.visitExtra(this)
    }
    
    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is RequirementsVisitor)
            accept(visitor)
        else
            super.accept(visitor)
    }
    
}
