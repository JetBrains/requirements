package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.MarkerAnd
import ru.meanmail.psi.MarkerOr
import ru.meanmail.psi.Visitor

class MarkerOrImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), MarkerOr {

    override val markerAndList: List<MarkerAnd>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, MarkerAnd::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitMarkerOr(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
