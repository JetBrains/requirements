package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.MarkerAnd
import ru.meanmail.psi.MarkerExpr
import ru.meanmail.psi.Visitor

class MarkerAndImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), MarkerAnd {

    override val markerExprList: List<MarkerExpr>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, MarkerExpr::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitMarkerAnd(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else {
            super.accept(visitor)
        }
    }

}
