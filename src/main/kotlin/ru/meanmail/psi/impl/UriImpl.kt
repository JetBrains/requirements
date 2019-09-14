package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class UriImpl(node: ASTNode) : ASTWrapperPsiElement(node), Uri {

    override val fragment: Fragment?
        get() = findChildByClass(Fragment::class.java)

    override val hierPart: HierPart?
        get() = findChildByClass(HierPart::class.java)

    override val query: Query?
        get() = findChildByClass(Query::class.java)

    override val scheme: Scheme
        get() = findNotNullChildByClass(Scheme::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitUri(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
