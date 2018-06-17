package ru.meanmail.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class RequirementsPackageStmtImpl(node: ASTNode) : RequirementsNamedElementImpl(node), RequirementsPackageStmt {

    override val extra: RequirementsExtra?
        get() = findChildByClass(RequirementsExtra::class.java)

    override val simplePackageStmt: RequirementsSimplePackageStmt
        get() = findNotNullChildByClass(RequirementsSimplePackageStmt::class.java)

    override val packageName: String?
        get() = RequirementsPsiImplUtil.getPackageName(this)

    override val version: String?
        get() = RequirementsPsiImplUtil.getVersion(this)

    override val extraPackage: String?
        get() = RequirementsPsiImplUtil.getExtraPackage(this)

    fun accept(visitor: RequirementsVisitor) {
        visitor.visitPackageStmt(this)
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
