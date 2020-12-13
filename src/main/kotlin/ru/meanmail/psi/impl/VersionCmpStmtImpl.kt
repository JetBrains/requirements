package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.VersionCmpStmt
import ru.meanmail.psi.Visitor

class VersionCmpStmtImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), VersionCmpStmt {

    fun accept(visitor: Visitor) {
        visitor.visitVersionCmpStmt(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
