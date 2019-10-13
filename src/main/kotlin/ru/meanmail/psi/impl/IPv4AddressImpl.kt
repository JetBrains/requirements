package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.DecOctet
import ru.meanmail.psi.IPv4Address
import ru.meanmail.psi.Visitor

class IPv4AddressImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), IPv4Address {

    override val decOctetList: List<DecOctet>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, DecOctet::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitIPv4Address(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
