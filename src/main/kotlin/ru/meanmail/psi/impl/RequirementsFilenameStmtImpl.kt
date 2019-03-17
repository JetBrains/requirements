package ru.meanmail.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiReference
import ru.meanmail.SubRequirementsReference
import ru.meanmail.psi.RequirementsFilenameStmt
import ru.meanmail.psi.RequirementsPsiImplUtil
import ru.meanmail.psi.RequirementsVisitor

class RequirementsFilenameStmtImpl(node: ASTNode) : RequirementsNamedElementImpl(node),
        RequirementsFilenameStmt {
    
    override val filename: String?
        get() = RequirementsPsiImplUtil.getFilename(this)
    
    private fun accept(visitor: RequirementsVisitor) {
        visitor.visitFilenameStmt(this)
    }
    
    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is RequirementsVisitor)
            accept(visitor)
        else
            super.accept(visitor)
    }
    
    override fun getReference(): PsiReference? {
        return SubRequirementsReference(this)
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
