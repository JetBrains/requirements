package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.PathReq
import ru.meanmail.psi.UriReference
import ru.meanmail.psi.Visitor

class PathReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), PathReq {

    override val uriReference: UriReference
        get() = findNotNullChildByClass(UriReference::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPathReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
