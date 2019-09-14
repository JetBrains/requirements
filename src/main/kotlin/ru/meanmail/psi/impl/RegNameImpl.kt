package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.PctEncoded
import ru.meanmail.psi.RegName
import ru.meanmail.psi.Unreserved
import ru.meanmail.psi.Visitor

class RegNameImpl(node: ASTNode) : ASTWrapperPsiElement(node), RegName {

    override val pctEncodedList: List<PctEncoded>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, PctEncoded::class.java)

    override val unreservedList: List<Unreserved>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Unreserved::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitRegName(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
