package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Hexdig
import ru.meanmail.psi.Visitor

class HexdigImpl(node: ASTNode) : ASTWrapperPsiElement(node), Hexdig {

    fun accept(visitor: Visitor) {
        visitor.visitHexdig(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
