package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.VersionCmpStmt
import ru.meanmail.psi.VersionOne
import ru.meanmail.psi.VersionStmt
import ru.meanmail.psi.Visitor

class VersionOneImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), VersionOne {

    override val version: VersionStmt
        get() = findNotNullChildByClass(VersionStmt::class.java)

    override val versionCmp: VersionCmpStmt
        get() = findNotNullChildByClass(VersionCmpStmt::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitVersionOne(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
