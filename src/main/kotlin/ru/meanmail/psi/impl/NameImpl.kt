package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.paths.WebReference
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiReference
import com.jetbrains.python.packaging.PyPIPackageUtil
import ru.meanmail.psi.Name
import ru.meanmail.psi.Visitor

class NameImpl(node: ASTNode) : ASTWrapperPsiElement(node), Name {

    fun accept(visitor: Visitor) {
        visitor.visitName(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun getReference(): PsiReference? {
        val url = "${PyPIPackageUtil.PYPI_URL}/$text"
        val textRange = TextRange(0, textLength)
        return WebReference(this, textRange, url)
    }

}
