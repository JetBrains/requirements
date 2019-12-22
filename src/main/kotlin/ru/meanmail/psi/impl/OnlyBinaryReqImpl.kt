package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.IncorrectOperationException
import ru.meanmail.psi.BinaryList
import ru.meanmail.psi.OnlyBinaryReq
import ru.meanmail.psi.Visitor

class OnlyBinaryReqImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), OnlyBinaryReq {
    fun accept(visitor: Visitor) {
        visitor.visitOnlyBinaryReq(this)
    }

    override fun getNameIdentifier(): PsiElement? {
        return this
    }

    override fun setName(name: String): PsiElement {
        throw IncorrectOperationException()
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) {
            accept(visitor)
        } else super.accept(visitor)
    }

    override val binaryList: BinaryList?
        get() = findChildByClass(BinaryList::class.java)
}
