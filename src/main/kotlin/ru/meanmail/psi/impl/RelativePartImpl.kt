package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class RelativePartImpl(node: ASTNode) : ASTWrapperPsiElement(node), RelativePart {

    override val authority: Authority?
        get() = findChildByClass(Authority::class.java)

    override val pathAbempty: PathAbempty?
        get() = findChildByClass(PathAbempty::class.java)

    override val pathAbsolute: PathAbsolute?
        get() = findChildByClass(PathAbsolute::class.java)

    override val pathEmpty: PathEmpty?
        get() = findChildByClass(PathEmpty::class.java)

    override val pathNoscheme: PathNoscheme?
        get() = findChildByClass(PathNoscheme::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitRelativePart(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
