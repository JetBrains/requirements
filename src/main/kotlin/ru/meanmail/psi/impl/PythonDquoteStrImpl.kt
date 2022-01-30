package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.PythonDquoteStr
import ru.meanmail.psi.Visitor

class PythonDquoteStrImpl(node: ASTNode) : ASTWrapperPsiElement(node), PythonDquoteStr {
    fun accept(visitor: Visitor) {
        visitor.visitPythonDquoteStr(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) accept(visitor) else super.accept(visitor)
    }
}
