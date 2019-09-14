package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class HostImpl(node: ASTNode) : ASTWrapperPsiElement(node), Host {

    override val ipLiteral: IPLiteral?
        get() = findChildByClass(IPLiteral::class.java)

    override val iPv4Address: IPv4Address?
        get() = findChildByClass(IPv4Address::class.java)

    override val regName: RegName?
        get() = findChildByClass(RegName::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitHost(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
