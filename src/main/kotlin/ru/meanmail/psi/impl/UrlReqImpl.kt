package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class UrlReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), UrlReq {

    override val extras: Extras?
        get() = findChildByClass(Extras::class.java)

    override val name: Name
        get() = findNotNullChildByClass(Name::class.java)

    override val quotedMarker: QuotedMarker?
        get() = findChildByClass(QuotedMarker::class.java)

    override val urlspec: Urlspec
        get() = findNotNullChildByClass(Urlspec::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitUrlReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
