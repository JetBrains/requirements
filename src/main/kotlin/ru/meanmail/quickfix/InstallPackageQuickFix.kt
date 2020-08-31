package ru.meanmail.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.util.FileContentUtil
import com.jetbrains.python.packaging.PyRequirement
import ru.meanmail.installPackage
import ru.meanmail.psi.NameReq

class InstallPackageQuickFix(element: NameReq,
                             private val description: String,
                             private val requirement: PyRequirement,
                             private val isUpdate: Boolean) : LocalQuickFixOnPsiElement(element) {
    override fun getText(): String {
        return description
    }

    override fun invoke(project: Project, file: PsiFile,
                        startElement: PsiElement,
                        endElement: PsiElement) {
        val element = (startElement as? NameReq) ?: return
        val packageName = element.name.text ?: return
        val versionOne = element.versionspec?.versionMany?.versionOneList?.get(0) ?: return

        installPackage(project, packageName, requirement) {
            if (isUpdate) {
                val application = ApplicationManager.getApplication()
                application.invokeLater {
                    application.runWriteAction {
                        versionOne.setVersion(it.version)
                        val virtualFile = element.containingFile.virtualFile
                        FileContentUtil.reparseFiles(project, listOf(virtualFile),
                                true)
                    }
                }
            }
        }
    }

    override fun getFamilyName(): String {
        return "Install packages"
    }
}
