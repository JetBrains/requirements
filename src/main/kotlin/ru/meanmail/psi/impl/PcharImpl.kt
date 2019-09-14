package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Pchar
import ru.meanmail.psi.PctEncoded
import ru.meanmail.psi.Unreserved
import ru.meanmail.psi.Visitor

class PcharImpl(node: ASTNode) : ASTWrapperPsiElement(node), Pchar {

    override val pctEncoded: PctEncoded?
        get() = findChildByClass(PctEncoded::class.java)

    override val unreserved: Unreserved?
        get() = findChildByClass(Unreserved::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPchar(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
