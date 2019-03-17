package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import ru.meanmail.psi.RequirementsPackageStmt

class InstallPackageQuickFix(element: RequirementsPackageStmt) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return "Install package"
    }
    
    override fun invoke(project: Project, file: PsiFile,
                        startElement: PsiElement,
                        endElement: PsiElement) {
        (startElement as? RequirementsPackageStmt)?.install()
    }
    
    override fun getFamilyName(): String {
        return "Install packages"
    }
}
