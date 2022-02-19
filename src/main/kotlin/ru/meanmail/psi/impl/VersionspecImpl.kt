package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil.getChildrenOfTypeAsList
import ru.meanmail.And
import ru.meanmail.False
import ru.meanmail.Logical
import ru.meanmail.PACKAGE_VERSION
import ru.meanmail.psi.VersionOne
import ru.meanmail.psi.Versionspec
import ru.meanmail.psi.Visitor

class VersionspecImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), Versionspec {

    override val versionOneList: List<VersionOne?>
        get() = getChildrenOfTypeAsList(this, VersionOne::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitVersionspec(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun isActual(version: String): Boolean {
        val expr = mutableListOf<Logical>()

        for (expression in versionOneList) {
            expr.add(expression?.logical() ?: False())
        }
        return And(*expr.toTypedArray()).check(
            mapOf(
                PACKAGE_VERSION to version
            )
        )
    }

}
