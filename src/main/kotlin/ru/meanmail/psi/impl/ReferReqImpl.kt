package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.IncorrectOperationException
import ru.meanmail.psi.ReferReq
import ru.meanmail.psi.UriReference
import ru.meanmail.psi.Visitor

class ReferReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), ReferReq {

    override val uriReference: UriReference?
        get() = findChildByClass(UriReference::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitReferReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun setName(name: String): PsiElement {
        throw IncorrectOperationException()
    }

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    override fun getNameIdentifier(): PsiElement? {
        return uriReference
    }

}
