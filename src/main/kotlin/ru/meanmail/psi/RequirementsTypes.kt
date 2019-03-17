package ru.meanmail.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import ru.meanmail.psi.impl.*

interface RequirementsTypes {
    
    object Factory {
        fun createElement(node: ASTNode): PsiElement {
            val type = node.elementType
            return when (type) {
                EDITABLE_REQUIREMENT_STMT -> RequirementsEditableRequirementStmtImpl(node)
                EXTRA -> RequirementsExtraImpl(node)
                FILENAME_STMT -> RequirementsFilenameStmtImpl(node)
                PACKAGE_STMT -> RequirementsPackageStmtImpl(node)
                PATH_STMT -> RequirementsPathStmtImpl(node)
                REQUIREMENT_STMT -> RequirementsRequirementStmtImpl(node)
                SIMPLE_PACKAGE_STMT -> RequirementsSimplePackageStmtImpl(node)
                URL_STMT -> RequirementsUrlStmtImpl(node)
                else -> throw AssertionError("Unknown element type: $type")
            }
        }
    }
    
    companion object {
        
        val EDITABLE_REQUIREMENT_STMT: IElementType = RequirementsElementType("EDITABLE_REQUIREMENT_STMT")
        val EXTRA: IElementType = RequirementsElementType("EXTRA")
        val FILENAME_STMT: IElementType = RequirementsElementType("FILENAME_STMT")
        val PACKAGE_STMT: IElementType = RequirementsElementType("PACKAGE_STMT")
        val PATH_STMT: IElementType = RequirementsElementType("PATH_STMT")
        val REQUIREMENT_STMT: IElementType = RequirementsElementType("REQUIREMENT_STMT")
        val SIMPLE_PACKAGE_STMT: IElementType = RequirementsElementType("SIMPLE_PACKAGE_STMT")
        val URL_STMT: IElementType = RequirementsElementType("URL_STMT")
        
        val BRANCH: IElementType = RequirementsTokenType("BRANCH")
        val COMMENT: IElementType = RequirementsTokenType("COMMENT")
        val CRLF: IElementType = RequirementsTokenType("CRLF")
        val EGG: IElementType = RequirementsTokenType("EGG")
        val FILENAME: IElementType = RequirementsTokenType("FILENAME")
        val LSBRACE: IElementType = RequirementsTokenType("LSBRACE")
        val PACKAGE: IElementType = RequirementsTokenType("PACKAGE")
        val PATH: IElementType = RequirementsTokenType("PATH")
        val REQUIREMENT: IElementType = RequirementsTokenType("REQUIREMENT")
        val REQUIREMENT_EDITABLE: IElementType = RequirementsTokenType("REQUIREMENT_EDITABLE")
        val RSBRACE: IElementType = RequirementsTokenType("RSBRACE")
        val SCHEMA: IElementType = RequirementsTokenType("SCHEMA")
        val SEPARATOR: IElementType = RequirementsTokenType("SEPARATOR")
        val VERSION: IElementType = RequirementsTokenType("VERSION")
    }
}
