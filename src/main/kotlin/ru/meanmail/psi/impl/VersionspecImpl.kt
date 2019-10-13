package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.VersionMany
import ru.meanmail.psi.Versionspec
import ru.meanmail.psi.Visitor

class VersionspecImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), Versionspec {

    override val versionMany: VersionMany
        get() = findNotNullChildByClass(VersionMany::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitVersionspec(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
