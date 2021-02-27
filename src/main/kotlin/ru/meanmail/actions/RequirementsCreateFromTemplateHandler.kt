package ru.meanmail.actions

import com.intellij.ide.fileTemplates.DefaultCreateFromTemplateHandler
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.openapi.project.ProjectManager
import ru.meanmail.getInstalledPackages

class RequirementsCreateFromTemplateHandler : DefaultCreateFromTemplateHandler() {
    override fun prepareProperties(
        props: MutableMap<String, Any>,
        filename: String?, template: FileTemplate
    ) {
        super.prepareProperties(props, filename, template)
        val projectName = props.getOrDefault("PROJECT_NAME", null) ?: return
        val project = ProjectManager.getInstance()
            .openProjects
            .find { it.name == projectName } ?: return

        props["packages"] = getInstalledPackages(project).map {
            it.name + "==" + it.version
        }
            .sortedBy { it.toLowerCase() }
            .joinToString("\n")
    }
}
