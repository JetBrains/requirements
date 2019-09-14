package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Marker
import ru.meanmail.psi.MarkerOr
import ru.meanmail.psi.Visitor

class MarkerImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), Marker {

    override val markerOr: MarkerOr
        get() = findNotNullChildByClass(MarkerOr::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitMarker(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
