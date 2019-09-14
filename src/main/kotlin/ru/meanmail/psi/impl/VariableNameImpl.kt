package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.VariableName
import ru.meanmail.psi.Visitor

class VariableNameImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), VariableName {

    fun accept(visitor: Visitor) {
        visitor.visitVariableName(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
