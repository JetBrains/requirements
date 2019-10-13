package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.IPLiteral
import ru.meanmail.psi.IPv6Address
import ru.meanmail.psi.IPvFuture
import ru.meanmail.psi.Visitor

class IPLiteralImpl(node: ASTNode) : ASTWrapperPsiElement(node), IPLiteral {

    override val iPv6Address: IPv6Address?
        get() = findChildByClass(IPv6Address::class.java)

    override val iPvFuture: IPvFuture?
        get() = findChildByClass(IPvFuture::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitIPLiteral(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
