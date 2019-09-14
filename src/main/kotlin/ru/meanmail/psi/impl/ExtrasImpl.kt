package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.Extras
import ru.meanmail.psi.ExtrasList
import ru.meanmail.psi.Visitor

class ExtrasImpl(node: ASTNode) : ASTWrapperPsiElement(node), Extras {

    override val extrasList: ExtrasList?
        get() = findChildByClass(ExtrasList::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitExtras(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
