package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.IncorrectOperationException
import ru.meanmail.psi.Host
import ru.meanmail.psi.Port
import ru.meanmail.psi.TrustedHostReq
import ru.meanmail.psi.Visitor

class TrustedHostReqImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), TrustedHostReq {
    fun accept(visitor: Visitor) {
        visitor.visitTrustedHostReq(this)
    }

    override fun getNameIdentifier(): PsiElement? {
        return this
    }

    override fun setName(name: String): PsiElement {
        throw IncorrectOperationException()
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else super.accept(visitor)
    }

    override val host: Host?
        get() = findChildByClass(Host::class.java)

    override val port: Port?
        get() = findChildByClass(Port::class.java)
}
