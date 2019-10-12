package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.IncorrectOperationException
import ru.meanmail.psi.*

class NameReqImpl(node: ASTNode) : ASTWrapperPsiElement(node), NameReq {

    override val extras: Extras?
        get() = findChildByClass(Extras::class.java)

    override val name: Name
        get() = findNotNullChildByClass(Name::class.java)

    override val quotedMarker: QuotedMarker?
        get() = findChildByClass(QuotedMarker::class.java)

    override val versionspec: Versionspec?
        get() = findChildByClass(Versionspec::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitNameReq(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun setName(name: String): PsiElement {
        throw IncorrectOperationException()
    }

    override fun getName(): String? {
        return nameIdentifier.text
    }

    override fun getNameIdentifier(): PsiElement {
        return name
    }

}
