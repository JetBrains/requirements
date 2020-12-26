package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.UseFeatureReq
import ru.meanmail.psi.Visitor

class UseFeatureReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), UseFeatureReq {
    fun accept(visitor: Visitor) {
        visitor.visitUseFeatureReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else {
            super.accept(visitor)
        }
    }
}
