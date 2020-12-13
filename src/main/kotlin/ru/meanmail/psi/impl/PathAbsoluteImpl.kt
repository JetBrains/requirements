package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.PathAbsolute
import ru.meanmail.psi.Segment
import ru.meanmail.psi.SegmentNz
import ru.meanmail.psi.Visitor

class PathAbsoluteImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), PathAbsolute {

    override val segmentList: List<Segment>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Segment::class.java)

    override val segmentNz: SegmentNz?
        get() = findChildByClass(SegmentNz::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitPathAbsolute(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
