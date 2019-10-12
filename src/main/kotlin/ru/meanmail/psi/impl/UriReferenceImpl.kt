package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiReference
import com.intellij.util.IncorrectOperationException
import ru.meanmail.Reference
import ru.meanmail.psi.RelativeRef
import ru.meanmail.psi.Uri
import ru.meanmail.psi.UriReference
import ru.meanmail.psi.Visitor

class UriReferenceImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), UriReference {

    override val relativeRef: RelativeRef?
        get() = findChildByClass(RelativeRef::class.java)

    override val uri: Uri?
        get() = findChildByClass(Uri::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitUriReference(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun getReference(): PsiReference? {
        return Reference(this)
    }


    override fun setName(name: String): PsiElement {
        throw IncorrectOperationException()
    }

    override fun getName(): String? {
        return (uri ?: relativeRef)?.text
    }

    override fun getNameIdentifier(): PsiElement? {
        return uri ?: relativeRef
    }
}
