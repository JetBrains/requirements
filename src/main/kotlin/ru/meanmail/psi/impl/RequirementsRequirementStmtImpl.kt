package ru.meanmail.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.RequirementsPsiImplUtil
import ru.meanmail.psi.RequirementsRequirementStmt
import ru.meanmail.psi.RequirementsVisitor

class RequirementsRequirementStmtImpl(node: ASTNode) : RequirementsNamedElementImpl(node), RequirementsRequirementStmt {

    override val filename: String?
        get() = RequirementsPsiImplUtil.getFilename(this)

    fun accept(visitor: RequirementsVisitor) {
        visitor.visitRequirementStmt(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is RequirementsVisitor)
            accept(visitor)
        else
            super.accept(visitor)
    }

    override fun getName(): String? {
        return RequirementsPsiImplUtil.getName(this)
    }

    override fun setName(newName: String): PsiElement {
        return RequirementsPsiImplUtil.setName(this, newName)
    }

    override fun getNameIdentifier(): PsiElement? {
        return RequirementsPsiImplUtil.getNameIdentifier(this)
    }

}
