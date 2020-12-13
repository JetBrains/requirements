package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.PathNoscheme
import ru.meanmail.psi.Segment
import ru.meanmail.psi.SegmentNzNc
import ru.meanmail.psi.Visitor

class PathNoschemeImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), PathNoscheme {

    override val segmentList: List<Segment>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Segment::class.java)

    override val segmentNzNc: SegmentNzNc
        get() = findNotNullChildByClass(SegmentNzNc::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPathNoscheme(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
