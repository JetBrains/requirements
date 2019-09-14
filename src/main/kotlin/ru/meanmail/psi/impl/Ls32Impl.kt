package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class Ls32Impl(node: ASTNode) : ASTWrapperPsiElement(node), Ls32 {

    override val iPv4Address: IPv4Address?
        get() = findChildByClass(IPv4Address::class.java)

    override val h16: H16?
        get() = findChildByClass(H16::class.java)

    override val h16Colon: H16Colon?
        get() = findChildByClass(H16Colon::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitLs32(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
