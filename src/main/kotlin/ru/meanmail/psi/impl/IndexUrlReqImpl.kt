package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.IncorrectOperationException
import ru.meanmail.psi.IndexUrlReq
import ru.meanmail.psi.UriReference
import ru.meanmail.psi.Visitor

class IndexUrlReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), IndexUrlReq {
    fun accept(visitor: Visitor) {
        visitor.visitIndexUrlReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else super.accept(visitor)
    }

    override fun getNameIdentifier(): PsiElement? {
        return uriReference
    }

    override fun setName(name: String): PsiElement {
        throw IncorrectOperationException()
    }

    override val uriReference: UriReference?
        get() = findChildByClass(UriReference::class.java)
}
