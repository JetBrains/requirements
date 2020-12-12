package ru.meanmail.actions

import com.intellij.execution.ExecutionException
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import ru.meanmail.RequirementsLanguage
import ru.meanmail.getPackageManager
import ru.meanmail.psi.*
import ru.meanmail.reparseFile


class InstallAllAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val psiFile = e.getData(LangDataKeys.PSI_FILE)
        e.presentation.isEnabledAndVisible = psiFile?.language == RequirementsLanguage
    }

    override fun actionPerformed(e: AnActionEvent) {
        val psiFile = e.getData(LangDataKeys.PSI_FILE) as? RequirementsFile ?: return
        val project: Project = e.project ?: return
        val virtualFile = psiFile.virtualFile

        val requirements = psiFile.enabledRequirements().mapNotNull {
            return@mapNotNull when (it) {
                is NameReq -> it.requirement
                is PathReq -> it.text
                is UrlReq -> it.text
                else -> null
            }
        }

        val title = "Installing ${virtualFile.name}"
        val task = InstallTask(project, requirements, title, virtualFile)
        ProgressManager.getInstance().run(task)
    }

    class InstallTask(
        project: Project, val requirements: List<String>,
        title: String, private val virtualFile: VirtualFile
    ) :
        Task.Backgroundable(project, title, false) {
        override fun run(indicator: ProgressIndicator) {
            indicator.text = this.title
            indicator.isIndeterminate = true

            val packageManager = getPackageManager(project)

            if (packageManager == null) {
                Notification(
                    "pip",
                    title,
                    "Package manager is not available",
                    NotificationType.ERROR
                ).notify(project)
                return
            }
            for (requirement in requirements) {
                indicator.text = requirement
                try {
                    val requirementString = packageManager.parseRequirement(requirement)
                    if (requirementString != null) {
                        packageManager.install(listOf(requirementString), emptyList())
                        showSuccess(requirement)
                    } else {
                        showError(requirement, "Can not install")
                    }
                } catch (e: ExecutionException) {
                    showError(requirement, e.localizedMessage)
                }
            }
            reparseFile(project, virtualFile)
        }

        private fun showSuccess(requirement: String) {
            val notification = Notification(
                "pip",
                requirement, "Successfully installed",
                NotificationType.INFORMATION
            )
            notification.notify(project)
        }

        private fun showError(requirement: String, message: String) {
            val notification = Notification(
                "pip.failed",
                requirement, message, NotificationType.ERROR
            )
            notification.notify(project)
        }
    }
}
