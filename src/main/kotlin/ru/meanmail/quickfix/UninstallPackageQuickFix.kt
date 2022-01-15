package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import ru.meanmail.getPythonSdk
import ru.meanmail.psi.NameReq
import ru.meanmail.reparseFile
import ru.meanmail.uninstallPackage

class UninstallPackageQuickFix(
    element: NameReq, private val description: String
) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }

    override fun invoke(
        project: Project, file: PsiFile,
        startElement: PsiElement,
        endElement: PsiElement
    ) {
        val element = (startElement as? NameReq) ?: return
        val packageName = element.name.text ?: return
        val virtualFile = element.containingFile.virtualFile
        val sdk = getPythonSdk(project, virtualFile) ?: return

        uninstallPackage(project, sdk, packageName) {
            reparseFile(project, virtualFile)
        }
    }

    override fun getFamilyName(): String {
        return "Uninstall packages"
    }
}
