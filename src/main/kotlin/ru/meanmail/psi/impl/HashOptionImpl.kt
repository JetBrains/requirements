package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil.getChildrenOfTypeAsList
import ru.meanmail.psi.HashOption
import ru.meanmail.psi.Hexdig
import ru.meanmail.psi.Visitor

class HashOptionImpl(node: ASTNode) : ASTWrapperPsiElement(node), HashOption {

    override val hexdigList: List<Hexdig>
        get() = getChildrenOfTypeAsList(this, Hexdig::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitHashOption(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
