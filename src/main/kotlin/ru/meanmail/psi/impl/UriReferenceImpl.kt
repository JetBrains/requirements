package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
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

}
