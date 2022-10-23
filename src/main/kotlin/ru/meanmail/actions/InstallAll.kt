package ru.meanmail.actions

import com.intellij.execution.ExecutionException
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.psi.PsiFile
import ru.meanmail.getPackageManager
import ru.meanmail.getPythonSdk
import ru.meanmail.lang.RequirementsLanguage
import ru.meanmail.notification.Notifier
import ru.meanmail.psi.NameReq
import ru.meanmail.psi.PathReq
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.psi.UrlReq
import ru.meanmail.reparseOpenedFiles


class InstallAllAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        val psiFile = e.getData(LangDataKeys.PSI_FILE)
        e.presentation.isEnabledAndVisible = psiFile?.language == RequirementsLanguage
    }

    override fun actionPerformed(e: AnActionEvent) {
        val psiFile = e.getData(LangDataKeys.PSI_FILE) as? RequirementsFile ?: return

        val requirements = psiFile.enabledRequirements().mapNotNull {
            return@mapNotNull when (it) {
                is NameReq -> it.requirement
                is PathReq -> it.text
                is UrlReq -> it.text
                else -> null
            }
        }

        val title = "Installing ${psiFile.name}"
        val task = InstallTask(requirements, title, psiFile)
        ProgressManager.getInstance().run(task)
    }

    class InstallTask(
        val requirements: List<String>,
        title: String,
        private val psiFile: PsiFile
    ) :
        Task.Backgroundable(psiFile.project, title, false) {
        override fun run(indicator: ProgressIndicator) {
            indicator.text = this.title
            indicator.isIndeterminate = true
            val sdk = getPythonSdk(psiFile)
            if (sdk == null) {
                Notifier.notifyError(
                    project, title, "No Sdk"
                )
                return
            }
            val packageManager = getPackageManager(sdk)

            for (requirement in requirements) {
                indicator.text = requirement
                try {
                    val requirementString = packageManager.parseRequirement(requirement)
                    if (requirementString != null) {
                        packageManager.install(listOf(requirementString), emptyList())
                        Notifier.notifyInformation(
                            project, requirement, "Successfully installed",
                        )
                    } else {
                        Notifier.notifyError(project, requirement, "Can't install")
                    }
                } catch (e: ExecutionException) {
                    Notifier.notifyError(project, requirement, e.localizedMessage)
                }
            }
            reparseOpenedFiles(project)
        }
    }
}
