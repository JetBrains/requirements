package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.BinaryList
import ru.meanmail.psi.ExtrasList
import ru.meanmail.psi.Visitor

class BinaryListImpl(node: ASTNode) : ASTWrapperPsiElement(node), BinaryList {
    fun accept(visitor: Visitor) {
        visitor.visitBinaryList(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else super.accept(visitor)
    }

    override val extrasList: ExtrasList?
        get() = findChildByClass(ExtrasList::class.java)
}
