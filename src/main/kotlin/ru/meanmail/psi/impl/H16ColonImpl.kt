package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.H16
import ru.meanmail.psi.H16Colon
import ru.meanmail.psi.Visitor

class H16ColonImpl(node: ASTNode) : ASTWrapperPsiElement(node), H16Colon {

    override val h16: H16
        get() = findNotNullChildByClass(H16::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitH16Colon(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
