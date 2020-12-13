package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.And
import ru.meanmail.Logical
import ru.meanmail.psi.VersionMany
import ru.meanmail.psi.VersionOne
import ru.meanmail.psi.Visitor

class VersionManyImpl(node: ASTNode) : ASTWrapperPsiElement(node), VersionMany {

    override val versionOneList: List<VersionOne>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, VersionOne::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitVersionMany(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun logical(): Logical {
        val expr = mutableListOf<Logical>()

        for (expression in versionOneList) {
            expr.add(expression.logical())
        }
        return And(*expr.toTypedArray())
    }
}
