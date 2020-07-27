package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import ru.meanmail.installPackage
import ru.meanmail.psi.NameReq
import ru.meanmail.reparseFile

class InstallPackageQuickFix(element: NameReq,
                             private val description: String,
                             private val version: String) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }

    override fun invoke(project: Project, file: PsiFile,
                        startElement: PsiElement,
                        endElement: PsiElement) {
        val element = (startElement as? NameReq) ?: return
        val packageName = element.name.text ?: return
        val versionOne = element.versionspec?.versionMany?.versionOneList?.get(0) ?: return
        val versionCmp = versionOne.versionCmp.text ?: "=="
        versionOne.setVersion(version)
        val virtualFile = element.containingFile.virtualFile

        installPackage(project, packageName, version, versionCmp) {
            reparseFile(project, virtualFile)
        }
    }

    override fun getFamilyName(): String {
        return "Install packages"
    }
}
