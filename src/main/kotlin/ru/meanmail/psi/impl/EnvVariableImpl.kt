package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.EnvVariable
import ru.meanmail.psi.VariableName
import ru.meanmail.psi.Visitor

class EnvVariableImpl(node: ASTNode) : ASTWrapperPsiElement(node), EnvVariable {

    override val variableName: VariableName
        get() = findNotNullChildByClass(VariableName::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitEnvVariable(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
