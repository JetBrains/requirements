package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.PreferBinaryReq
import ru.meanmail.psi.Visitor

class PreferBinaryReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), PreferBinaryReq {
    fun accept(visitor: Visitor) {
        visitor.visitPreferBinaryReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else {
            super.accept(visitor)
        }
    }
}
