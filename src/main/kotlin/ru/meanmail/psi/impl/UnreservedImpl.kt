package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Unreserved
import ru.meanmail.psi.Visitor

class UnreservedImpl(node: ASTNode) : ASTWrapperPsiElement(node), Unreserved {

    fun accept(visitor: Visitor) {
        visitor.visitUnreserved(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
