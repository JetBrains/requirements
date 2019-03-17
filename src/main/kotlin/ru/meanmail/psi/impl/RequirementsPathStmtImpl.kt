package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.RequirementsPathStmt
import ru.meanmail.psi.RequirementsVisitor

class RequirementsPathStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RequirementsPathStmt {
    
    private fun accept(visitor: RequirementsVisitor) {
        visitor.visitPathStmt(this)
    }
    
    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is RequirementsVisitor)
            accept(visitor)
        else
            super.accept(visitor)
    }
    
}
