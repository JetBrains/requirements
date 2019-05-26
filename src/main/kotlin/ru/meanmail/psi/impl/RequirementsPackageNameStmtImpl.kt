package ru.meanmail.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.openapi.paths.WebReference
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiReference
import com.jetbrains.python.packaging.PyPIPackageUtil
import ru.meanmail.psi.RequirementsPackageNameStmt
import ru.meanmail.psi.RequirementsPsiImplUtil
import ru.meanmail.psi.RequirementsVisitor

class RequirementsPackageNameStmtImpl(node: ASTNode) : RequirementsNamedElementImpl(node),
        RequirementsPackageNameStmt {
    
    override val packageName: String?
        get() = RequirementsPsiImplUtil.getPackageName(this)
    
    private fun accept(visitor: RequirementsVisitor) {
        visitor.visitPackageNameStmt(this)
    }
    
    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is RequirementsVisitor)
            accept(visitor)
        else
            super.accept(visitor)
    }
    
    override fun getReference(): PsiReference? {
        val packageName = packageName ?: return null
        val url = "${PyPIPackageUtil.PYPI_URL}/$packageName"
        val textRange = TextRange(0, textLength)
        return WebReference(this, textRange, url)
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
