package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.*

class IPv6AddressImpl(node: ASTNode) : ASTWrapperPsiElement(node), IPv6Address {

    override val h16: H16?
        get() = findChildByClass(H16::class.java)

    override val h16ColonList: List<H16Colon>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, H16Colon::class.java)

    override val ls32: Ls32?
        get() = findChildByClass(Ls32::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitIPv6Address(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
