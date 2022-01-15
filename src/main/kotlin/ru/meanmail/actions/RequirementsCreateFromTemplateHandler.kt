package ru.meanmail.actions

import com.intellij.ide.fileTemplates.DefaultCreateFromTemplateHandler
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFileManager
import ru.meanmail.fileTypes.RequirementsFileType
import ru.meanmail.getInstalledPackages
import ru.meanmail.getPythonSdk
import java.nio.file.Path

class RequirementsCreateFromTemplateHandler : DefaultCreateFromTemplateHandler() {
    override fun handlesTemplate(template: FileTemplate): Boolean {
        return template.extension == RequirementsFileType.defaultExtension
    }

    override fun prepareProperties(
        props: MutableMap<String, Any>,
        filename: String?,
        template: FileTemplate,
        project: Project
    ) {
        super.prepareProperties(props, filename, template, project)
        val virtualFile = VirtualFileManager.getInstance()
            .findFileByNioPath(Path.of(props[FileTemplate.ATTRIBUTE_FILE_PATH] as String).parent) ?: return
        val sdk = getPythonSdk(project, virtualFile) ?: return

        props["packages"] = getInstalledPackages(sdk).map {
            it.name + "==" + it.version
        }.sortedBy(
            String::lowercase
        ).joinToString("\n")
    }
}
