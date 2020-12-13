package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.PathEmpty
import ru.meanmail.psi.Pchar
import ru.meanmail.psi.Visitor

class PathEmptyImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), PathEmpty {

    override val pchar: Pchar
        get() = findNotNullChildByClass(Pchar::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPathEmpty(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
