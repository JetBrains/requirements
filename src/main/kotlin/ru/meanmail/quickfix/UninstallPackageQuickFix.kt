package ru.meanmail.quickfix

import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import ru.meanmail.getPythonSdk
import ru.meanmail.psi.NameReq
import ru.meanmail.reparseOpenedFiles
import ru.meanmail.uninstallPackage

class UninstallPackageQuickFix(
    element: NameReq, private val description: String
) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }

    override fun generatePreview(project: Project, previewDescriptor: ProblemDescriptor): IntentionPreviewInfo {
        return IntentionPreviewInfo.EMPTY
    }

    override fun invoke(
        project: Project,
        file: PsiFile,
        startElement: PsiElement,
        endElement: PsiElement
    ) {
        val element = (startElement as? NameReq) ?: return
        val packageName = element.name.text ?: return
        val sdk = getPythonSdk(file) ?: return

        uninstallPackage(project, sdk, packageName) {
            reparseOpenedFiles(project)
        }
    }

    override fun getFamilyName(): String {
        return "Uninstall packages"
    }
}
