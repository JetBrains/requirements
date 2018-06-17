package ru.meanmail

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import ru.meanmail.psi.RequirementsTypes

class RequirementsSyntaxHighlighter : SyntaxHighlighterBase() {
    
    override fun getHighlightingLexer(): Lexer {
        return RequirementsLexerAdapter()
    }
    
    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            RequirementsTypes.SEPARATOR -> SEPARATOR_KEYS
            RequirementsTypes.PACKAGE -> PACKAGE_KEYS
            RequirementsTypes.VERSION -> VERSION_KEYS
            RequirementsTypes.COMMENT -> COMMENT_KEYS
            RequirementsTypes.REQUIREMENT -> REQUIREMENT_KEYS
            RequirementsTypes.FILENAME -> FILENAME_KEYS
            RequirementsTypes.LSBRACE -> LSBRACE_KEYS
            RequirementsTypes.RSBRACE -> RSBRACE_KEYS
            RequirementsTypes.REQUIREMENT_EDITABLE -> REQUIREMENT_EDITABLE_KEYS
            RequirementsTypes.SCHEMA -> SCHEMA_KEYS
            RequirementsTypes.PATH -> PATH_KEYS
            RequirementsTypes.BRANCH -> BRANCH_KEYS
            RequirementsTypes.EGG -> EGG_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }
    
    companion object {
        val SEPARATOR = createTextAttributesKey("REQUIREMENTS_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val PACKAGE = createTextAttributesKey("REQUIREMENTS_PACKAGE", DefaultLanguageHighlighterColors.KEYWORD)
        val VERSION = createTextAttributesKey("REQUIREMENTS_VERSION", DefaultLanguageHighlighterColors.STRING)
        val COMMENT = createTextAttributesKey("REQUIREMENTS_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = createTextAttributesKey("REQUIREMENTS_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        val REQUIREMENT = createTextAttributesKey("REQUIREMENTS_REQUIREMENT", DefaultLanguageHighlighterColors.KEYWORD)
        val FILENAME = createTextAttributesKey("REQUIREMENTS_FILENAME", DefaultLanguageHighlighterColors.STRING)
        val LSBRACE = createTextAttributesKey("REQUIREMENTS_LSBRACE", DefaultLanguageHighlighterColors.BRACES)
        val RSBRACE = createTextAttributesKey("REQUIREMENTS_RSBRACE", DefaultLanguageHighlighterColors.BRACES)
        val REQUIREMENT_EDITABLE = createTextAttributesKey("REQUIREMENTS_REQUIREMENT_EDITABLE", DefaultLanguageHighlighterColors.KEYWORD)
        val SCHEMA = createTextAttributesKey("REQUIREMENTS_SCHEMA", DefaultLanguageHighlighterColors.STRING)
        val PATH = createTextAttributesKey("REQUIREMENTS_PATH", DefaultLanguageHighlighterColors.STRING)
        val BRANCH = createTextAttributesKey("REQUIREMENTS_BRANCH", DefaultLanguageHighlighterColors.IDENTIFIER)
        val EGG = createTextAttributesKey("REQUIREMENTS_EGG", DefaultLanguageHighlighterColors.IDENTIFIER)
        
        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val SEPARATOR_KEYS = arrayOf(SEPARATOR)
        private val PACKAGE_KEYS = arrayOf(PACKAGE)
        private val VERSION_KEYS = arrayOf(VERSION)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val REQUIREMENT_KEYS = arrayOf(REQUIREMENT)
        private val FILENAME_KEYS = arrayOf(FILENAME)
        private val LSBRACE_KEYS = arrayOf(LSBRACE)
        private val RSBRACE_KEYS = arrayOf(RSBRACE)
        private val REQUIREMENT_EDITABLE_KEYS = arrayOf(REQUIREMENT_EDITABLE)
        private val SCHEMA_KEYS = arrayOf(SCHEMA)
        private val PATH_KEYS = arrayOf(PATH)
        private val BRANCH_KEYS = arrayOf(BRANCH)
        private val EGG_KEYS = arrayOf(EGG)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}
