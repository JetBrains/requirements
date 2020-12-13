package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Marker
import ru.meanmail.psi.QuotedMarker
import ru.meanmail.psi.Visitor

class QuotedMarkerImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), QuotedMarker {

    override val marker: Marker
        get() = findNotNullChildByClass(Marker::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitQuotedMarker(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
