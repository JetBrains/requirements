package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.PathRootless
import ru.meanmail.psi.Segment
import ru.meanmail.psi.SegmentNz
import ru.meanmail.psi.Visitor

class PathRootlessImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), PathRootless {

    override val segmentList: List<Segment>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Segment::class.java)

    override val segmentNz: SegmentNz
        get() = findNotNullChildByClass(SegmentNz::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPathRootless(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
