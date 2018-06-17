package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.RequirementsSimplePackageStmt
import ru.meanmail.psi.RequirementsVisitor

class RequirementsSimplePackageStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), RequirementsSimplePackageStmt {
    
    fun accept(visitor: RequirementsVisitor) {
        visitor.visitSimplePackageStmt(this)
    }
    
    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is RequirementsVisitor)
            accept(visitor)
        else
            super.accept(visitor)
    }
    
}
