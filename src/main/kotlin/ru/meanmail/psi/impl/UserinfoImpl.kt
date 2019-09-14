package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.*

class UserinfoImpl(node: ASTNode) : ASTWrapperPsiElement(node), Userinfo {

    override val envVariableList: List<EnvVariable>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, EnvVariable::class.java)

    override val pctEncodedList: List<PctEncoded>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, PctEncoded::class.java)

    override val unreservedList: List<Unreserved>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, Unreserved::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitUserinfo(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
