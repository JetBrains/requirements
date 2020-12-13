package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.DecOctet
import ru.meanmail.psi.Nz
import ru.meanmail.psi.Visitor

class DecOctetImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), DecOctet {

    override val nz: Nz?
        get() = findChildByClass(Nz::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitDecOctet(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
