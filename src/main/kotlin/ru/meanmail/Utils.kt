package ru.meanmail

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.util.FileContentUtil
import java.io.File

val CANONICALIZE_REGEX = "[-_.]+".toRegex()

fun canonicalizeName(name: String): String {
    return CANONICALIZE_REGEX.replace(name, "-").lowercase()
}

fun resolveFile(filepath: String, base: PsiDirectory? = null): VirtualFile? {
    val target = File(filepath)
    return if (target.isAbsolute) {
        LocalFileSystem.getInstance().findFileByIoFile(target)
    } else {
        base?.virtualFile?.findFileByRelativePath(filepath)
    }
}

fun reparseOpenedFiles(project: Project) {
    ApplicationManager.getApplication().invokeLater {
        var isDisposed = false
        ReadAction.run<Throwable> {
            isDisposed = project.isDisposed
        }
        if (isDisposed) {
            return@invokeLater
        }
        FileContentUtil.reparseOpenedFiles()
    }
}
