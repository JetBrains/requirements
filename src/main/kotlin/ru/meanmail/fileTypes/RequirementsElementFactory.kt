package ru.meanmail.fileTypes

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import ru.meanmail.psi.*


fun createFileFromText(project: Project, text: String): RequirementsFile {
    val name = "dummy.txt"
    return PsiFileFactory.getInstance(project).createFileFromText(
        name, RequirementsFileType, text
    ) as RequirementsFile
}

fun createUriReference(project: Project, name: String): UriReference {
    val file = createFileFromText(project, "-r $name")
    return file.firstChild.children[0].children[0] as UriReference
}

fun createNameReq(project: Project, text: String): NameReq? {
    val file = createFileFromText(project, text)
    return file.firstChild as? NameReq
}

fun createVersionspec(project: Project, version: String): Versionspec? {
    val preparedVersion = version.split(',')
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .joinToString(",")
    val nameReq = createNameReq(project, "packageName${preparedVersion}")
    return nameReq?.children?.find { it is Versionspec } as? Versionspec
}

fun createExtras(project: Project, extras: List<String>): Extras? {
    val preparedExtras = extras.map { it.trim() }
        .filter { it.isNotEmpty() }
        .joinToString(",")
    if (preparedExtras.isEmpty()) {
        return null
    }
    val nameReq = createNameReq(project, "packageName[${preparedExtras}]")
    return nameReq?.extras
}
