package ru.meanmail.psi

import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil
import ru.meanmail.RequirementsFileType


object ElementFactory {
    fun createFile(project: Project, text: String): RequirementsFile {
        val name = "requirements.txt"
        return PsiFileFactory.getInstance(project).createFileFromText(name,
                RequirementsFileType, text) as RequirementsFile
    }

    fun createVersion(version: String): ASTNode {
        val node = ASTFactory.leaf(Types.VERSION, version)
        CodeEditUtil.setNodeGenerated(node, true)
        return node
    }

}
