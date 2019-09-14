package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.H16
import ru.meanmail.psi.Hexdig
import ru.meanmail.psi.Visitor

class H16Impl(node: ASTNode) : ASTWrapperPsiElement(node), H16 {

    override val hexdigList: List<Hexdig>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Hexdig::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitH16(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
