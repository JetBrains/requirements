package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.*

class MarkerExprImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), MarkerExpr {

    override val markerOr: MarkerOr?
        get() = findChildByClass(MarkerOr::class.java)

    override val markerOp: MarkerOp?
        get() = findChildByClass(MarkerOp::class.java)

    override val markerVarList: List<MarkerVar>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, MarkerVar::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitMarkerExpr(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
