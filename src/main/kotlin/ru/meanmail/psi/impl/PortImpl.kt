package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Port
import ru.meanmail.psi.Visitor

class PortImpl(node: ASTNode) : ASTWrapperPsiElement(node), Port {

    fun accept(visitor: Visitor) {
        visitor.visitPort(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
