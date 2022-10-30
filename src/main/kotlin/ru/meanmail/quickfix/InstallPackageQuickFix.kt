package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import ru.meanmail.getPythonSdk
import ru.meanmail.installPackage
import ru.meanmail.psi.NameReq
import ru.meanmail.reparseOpenedFiles

class InstallPackageQuickFix(
    element: NameReq,
    private val description: String,
    private val version: String,
    private val setVersion: Boolean = false
) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }

    override fun invoke(
        project: Project,
        file: PsiFile,
        startElement: PsiElement,
        endElement: PsiElement
    ) {
        val element = (startElement as? NameReq) ?: return
        val packageName = element.name.text ?: return
        if (setVersion) {
            ApplicationManager.getApplication().runWriteAction {
                element.setVersion("==${version}")
            }
        }
        val sdk = getPythonSdk(file) ?: return

        installPackage(project, sdk, packageName, version) {
            reparseOpenedFiles(project)
        }
    }

    override fun getFamilyName(): String {
        return "Install packages"
    }
}
