package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.MarkerVar
import ru.meanmail.psi.PythonStr
import ru.meanmail.psi.Visitor

class MarkerVarImpl(node: ASTNode) :
        ASTWrapperPsiElement(node), MarkerVar {

    override val pythonStr: PythonStr?
        get() = findChildByClass(PythonStr::class.java)

    fun accept(visitor: Visitor) {
        visitor.visitMarkerVar(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

}
