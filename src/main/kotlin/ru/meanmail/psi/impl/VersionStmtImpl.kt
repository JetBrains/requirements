package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.VersionStmt
import ru.meanmail.psi.Visitor

class VersionStmtImpl(node: ASTNode) : ASTWrapperPsiElement(node), VersionStmt {

    fun accept(visitor: Visitor) {
        visitor.visitVersionStmt(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
