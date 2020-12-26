package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.PreReq
import ru.meanmail.psi.Visitor

class PreReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), PreReq {
    fun accept(visitor: Visitor) {
        visitor.visitPreReq(this)
    }


    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else {
            super.accept(visitor)
        }
    }
}
