package ru.meanmail

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import ru.meanmail.fileTypes.RequirementsFileType
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.psi.UriReference
import ru.meanmail.psi.Versionspec


fun createFile(project: Project, text: String): RequirementsFile {
    val name = "dummy.txt"
    return PsiFileFactory.getInstance(project).createFileFromText(
        name, RequirementsFileType, text
    ) as RequirementsFile
}

fun createUriReference(project: Project, name: String): UriReference {
    val file = createFile(project, "-r $name")
    return file.firstChild.children[0].children[0] as UriReference
}

fun createVersionspec(project: Project, version: String): Versionspec {
    val preparedVersion = version.split(',')
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .joinToString(",")
    val file = createFile(project, "packageName${preparedVersion}")
    return file.firstChild.children.find { it is Versionspec } as Versionspec
}
