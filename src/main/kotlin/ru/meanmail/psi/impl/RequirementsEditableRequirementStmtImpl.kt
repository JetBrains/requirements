package ru.meanmail.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.RequirementsEditableRequirementStmt
import ru.meanmail.psi.RequirementsPsiImplUtil
import ru.meanmail.psi.RequirementsUrlStmt
import ru.meanmail.psi.RequirementsVisitor

class RequirementsEditableRequirementStmtImpl(node: ASTNode) : RequirementsNamedElementImpl(node), RequirementsEditableRequirementStmt {

    override val urlStmt: RequirementsUrlStmt
        get() = findNotNullChildByClass(RequirementsUrlStmt::class.java)

    override val url: String?
        get() = RequirementsPsiImplUtil.getURL(this)

    override val branch: String?
        get() = RequirementsPsiImplUtil.getBranch(this)

    override val egg: String?
        get() = RequirementsPsiImplUtil.getEgg(this)

    fun accept(visitor: RequirementsVisitor) {
        visitor.visitEditableRequirementStmt(this)
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
