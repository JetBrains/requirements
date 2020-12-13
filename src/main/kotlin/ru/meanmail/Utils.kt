package ru.meanmail

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.FileContentUtil
import java.io.File

fun formatPackageName(packageName: String): String {
    return packageName.replace('_', '-').toLowerCase()
}

fun resolveFile(filepath: String, base: VirtualFile): VirtualFile? {
    val target = File(filepath)
    return if (target.isAbsolute) {
        LocalFileSystem.getInstance().findFileByIoFile(target)
    } else {
        base.findFileByRelativePath(filepath)
    }
}

fun reparseFile(project: Project, virtualFile: VirtualFile) {
    val application = ApplicationManager.getApplication()
    application.invokeLater {
        application.runWriteAction {
            FileContentUtil.reparseFiles(project, listOf(virtualFile), true)
        }
    }
}
