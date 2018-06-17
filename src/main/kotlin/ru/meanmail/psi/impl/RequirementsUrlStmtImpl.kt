package ru.meanmail.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class RequirementsUrlStmtImpl(node: ASTNode) : RequirementsNamedElementImpl(node), RequirementsUrlStmt {
    
    override val pathStmt: RequirementsPathStmt
        get() = findNotNullChildByClass(RequirementsPathStmt::class.java)

    override val simplePackageStmt: RequirementsSimplePackageStmt?
        get() = findChildByClass(RequirementsSimplePackageStmt::class.java)

    override val url: String?
        get() = RequirementsPsiImplUtil.getURL(this)

    override val branch: String?
        get() = RequirementsPsiImplUtil.getBranch(this)

    override val egg: String?
        get() = RequirementsPsiImplUtil.getEgg(this)

    fun accept(visitor: RequirementsVisitor) {
        visitor.visitUrlStmt(this)
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
