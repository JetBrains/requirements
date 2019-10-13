package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.MarkerOp
import ru.meanmail.psi.Visitor

class MarkerOpImpl(node: ASTNode) : ASTWrapperPsiElement(node), MarkerOp {

    fun accept(visitor: Visitor) {
        visitor.visitMarkerOp(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
