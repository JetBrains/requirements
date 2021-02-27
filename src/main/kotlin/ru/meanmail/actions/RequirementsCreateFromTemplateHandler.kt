package ru.meanmail.actions

import com.intellij.ide.fileTemplates.DefaultCreateFromTemplateHandler
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.openapi.project.Project
import ru.meanmail.getInstalledPackages

class RequirementsCreateFromTemplateHandler : DefaultCreateFromTemplateHandler() {
    override fun prepareProperties(
        props: MutableMap<String, Any>,
        filename: String?,
        template: FileTemplate,
        project: Project
    ) {
        super.prepareProperties(props, filename, template, project)

        props["packages"] = getInstalledPackages(project).map {
            it.name + "==" + it.version
        }
            .sortedBy { it.toLowerCase() }
            .joinToString("\n")
    }
}
