package ru.meanmail

import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil
import com.intellij.psi.tree.IElementType
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


fun createNodeFromText(type: IElementType, text: String): ASTNode {
    val node = ASTFactory.leaf(type, text)
    CodeEditUtil.setNodeGenerated(node, true)
    return node
}

fun reparseFile(project: Project, virtualFile: VirtualFile) {
    val application = ApplicationManager.getApplication()
    application.invokeLater {
        application.runWriteAction {
            FileContentUtil.reparseFiles(project, listOf(virtualFile), true)
        }
    }
}
