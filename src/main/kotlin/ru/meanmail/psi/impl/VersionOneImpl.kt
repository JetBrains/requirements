package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.VersionOne
import ru.meanmail.psi.Visitor

class VersionOneImpl(node: ASTNode) : ASTWrapperPsiElement(node), VersionOne {

    fun accept(visitor: Visitor) {
        visitor.visitVersionOne(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
