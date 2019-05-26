package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.util.FileContentUtil
import ru.meanmail.installPackage
import ru.meanmail.psi.RequirementsPackageStmt

class InstallPackageQuickFix(element: RequirementsPackageStmt,
                             private val description: String,
                             private val version: String) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }
    
    override fun invoke(project: Project, file: PsiFile,
                        startElement: PsiElement,
                        endElement: PsiElement) {
        val element = (startElement as? RequirementsPackageStmt) ?: return
        val packageName = element.packageName ?: return
        val relation = element.relation ?: ""
        
        installPackage(project, packageName, version, relation) {
            val application = ApplicationManager.getApplication()
            application.invokeLater {
                application.runWriteAction {
                    val virtualFile = element.containingFile.virtualFile
                    FileContentUtil.reparseFiles(virtualFile)
                }
            }
        }
    }
    
    override fun getFamilyName(): String {
        return "Install packages"
    }
}
