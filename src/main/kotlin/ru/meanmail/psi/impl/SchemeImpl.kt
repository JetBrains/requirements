package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Scheme
import ru.meanmail.psi.Visitor

class SchemeImpl(node: ASTNode) : ASTWrapperPsiElement(node), Scheme {

    fun accept(visitor: Visitor) {
        visitor.visitScheme(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
