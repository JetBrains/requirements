package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.RequirementsFilenameStmt
import ru.meanmail.psi.RequirementsPackageStmt

open class BaseInspectionVisitor(val holder: ProblemsHolder,
                                 val onTheFly: Boolean,
                                 val session: LocalInspectionToolSession) : PsiElementVisitor() {
    override fun visitElement(element: PsiElement?) {
        if (element is RequirementsPackageStmt) {
            visitPackageStmt(element)
        } else if (element is RequirementsFilenameStmt) {
            visitRequirementsFilenameStmt(element)
        } else {
            super.visitElement(element)
        }
    }
    
    open fun visitPackageStmt(element: RequirementsPackageStmt) {
        super.visitElement(element)
    }
    
    open fun visitRequirementsFilenameStmt(element: RequirementsFilenameStmt) {
        super.visitElement(element)
    }
}
