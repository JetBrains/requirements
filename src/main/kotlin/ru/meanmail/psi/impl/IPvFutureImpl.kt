package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.Hexdig
import ru.meanmail.psi.IPvFuture
import ru.meanmail.psi.Unreserved
import ru.meanmail.psi.Visitor

class IPvFutureImpl(node: ASTNode) : ASTWrapperPsiElement(node), IPvFuture {

    override val hexdigList: List<Hexdig>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Hexdig::class.java)

    override val unreservedList: List<Unreserved>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Unreserved::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitIPvFuture(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
