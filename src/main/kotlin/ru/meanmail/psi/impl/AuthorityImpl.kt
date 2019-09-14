package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class AuthorityImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), Authority {

    override val host: Host
        get() = findNotNullChildByClass(Host::class.java)

    override val port: Port?
        get() = findChildByClass(Port::class.java)

    override val userinfo: Userinfo?
        get() = findChildByClass(Userinfo::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitAuthority(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
