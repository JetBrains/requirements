package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.EditableReq
import ru.meanmail.psi.UriReference
import ru.meanmail.psi.Visitor

class EditableReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), EditableReq {

    override val uriReference: UriReference?
        get() = findChildByClass(UriReference::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitEditableReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
