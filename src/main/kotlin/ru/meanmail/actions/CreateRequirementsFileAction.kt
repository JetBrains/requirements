package ru.meanmail.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import ru.meanmail.AllIcons
import ru.meanmail.getPythonSdk

class CreateRequirementsFileAction : CreateFileFromTemplateAction(
    "requirements.txt",
    "Create a new requirements.txt file",
    AllIcons.FILE
), DumbAware {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("Create a new requirements.txt file").addKind(
            "Blank Requirements file",
            AllIcons.FILE,
            "Blank Requirements File"
        ).addKind(
            "Requirements with installed packages",
            AllIcons.FILE,
            "Freeze Requirements File"
        )
    }

    override fun getActionName(
        directory: PsiDirectory,
        newName: String, templateName: String
    ): String {
        return "Create A New Requirements File"
    }

    override fun isAvailable(dataContext: DataContext): Boolean {
        val project = dataContext.getData(PlatformDataKeys.PROJECT) ?: return false
        val virtualFile = project.projectFile ?: return false
        val sdk = getPythonSdk(project, virtualFile)
        return sdk != null
    }

    override fun hashCode(): Int = 0

    override fun equals(other: Any?): Boolean = other is CreateRequirementsFileAction

    override fun startInWriteAction() = false

}
