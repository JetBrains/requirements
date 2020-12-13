package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Hexdig
import ru.meanmail.psi.PctEncoded
import ru.meanmail.psi.Visitor

class PctEncodedImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), PctEncoded {

    override val hexdig: Hexdig
        get() = findNotNullChildByClass(Hexdig::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPctEncoded(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
