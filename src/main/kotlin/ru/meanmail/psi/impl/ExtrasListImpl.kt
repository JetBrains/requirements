package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.ExtrasList
import ru.meanmail.psi.Visitor

class ExtrasListImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), ExtrasList {

    fun accept(visitor: Visitor) {
        visitor.visitExtrasList(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
