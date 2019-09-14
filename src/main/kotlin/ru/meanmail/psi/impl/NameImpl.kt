package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Name
import ru.meanmail.psi.Visitor

class NameImpl(node: ASTNode) : ASTWrapperPsiElement(node), Name {

    fun accept(visitor: Visitor) {
        visitor.visitName(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
