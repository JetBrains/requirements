package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class RelativeRefImpl(node: ASTNode) : ASTWrapperPsiElement(node), RelativeRef {

    override val fragment: Fragment?
        get() = findChildByClass(Fragment::class.java)

    override val query: Query?
        get() = findChildByClass(Query::class.java)

    override val relativePart: RelativePart
        get() = findNotNullChildByClass(RelativePart::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitRelativeRef(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
