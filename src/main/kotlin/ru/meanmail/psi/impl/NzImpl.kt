package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Nz
import ru.meanmail.psi.Visitor

class NzImpl(node: ASTNode) : ASTWrapperPsiElement(node), Nz {

    fun accept(visitor: Visitor) {
        visitor.visitNz(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
