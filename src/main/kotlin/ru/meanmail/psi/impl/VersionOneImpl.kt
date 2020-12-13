package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.paths.WebReference
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiReference
import ru.meanmail.Expression
import ru.meanmail.Logical
import ru.meanmail.PACKAGE_VERSION
import ru.meanmail.PYPI_URL
import ru.meanmail.psi.*

class VersionOneImpl(node: ASTNode) :
    ASTWrapperPsiElement(node), VersionOne {

    override val versionCmp: VersionCmpStmt
        get() = findNotNullChildByClass(VersionCmpStmt::class.java)

    override val version: VersionStmt
        get() = findNotNullChildByClass(VersionStmt::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitVersionOne(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun getReference(): PsiReference? {
        var parent = parent
        while (parent != null) {
            if (parent is NameReq) {
                break
            }
            parent = parent.parent
        }
        val packageName = (parent as? NameReq)?.name ?: return null
        val url = "${PYPI_URL}/project/${packageName.text}/${version.text}"
        val textRange = TextRange(0, textLength)
        return WebReference(this, textRange, url)
    }

    override fun logical(): Logical {
        return Expression(PACKAGE_VERSION, versionCmp.text, version.text)
    }

}
