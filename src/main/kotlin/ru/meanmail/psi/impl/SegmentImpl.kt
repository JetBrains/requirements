package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.Pchar
import ru.meanmail.psi.Segment
import ru.meanmail.psi.Visitor

class SegmentImpl(node: ASTNode) : ASTWrapperPsiElement(node), Segment {

    override val pcharList: List<Pchar>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Pchar::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitSegment(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
