package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.PathAbempty
import ru.meanmail.psi.Segment
import ru.meanmail.psi.Visitor

class PathAbemptyImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), PathAbempty {

    override val segmentList: List<Segment>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Segment::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPathAbempty(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
