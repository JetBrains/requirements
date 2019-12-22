package ru.meanmail

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import ru.meanmail.psi.Types

class RequirementsSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return RequirementsLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            Types.COMMENT -> COMMENT_KEYS
            Types.ENV_VAR -> ENV_VAR_KEYS
            Types.IDENTIFIER -> IDENTIFIER_KEYS
            Types.PYTHON_STR_C -> PYTHON_STR_C_KEYS
            Types.VERSION -> VERSION_KEYS
            Types.OR -> OPERATOR_KEYS
            Types.AND -> OPERATOR_KEYS
            Types.IN -> OPERATOR_KEYS
            Types.NOT -> OPERATOR_KEYS
            Types.SHARP -> SHARP_KEYS
            Types.SEMICOLON -> SEMICOLON_KEYS
            Types.REFER -> IDENTIFIER_KEYS
            Types.CONSTRAINT -> IDENTIFIER_KEYS
            Types.EDITABLE -> IDENTIFIER_KEYS
            Types.INDEX_URL -> IDENTIFIER_KEYS
            Types.EXTRA_INDEX_URL -> IDENTIFIER_KEYS
            Types.NO_INDEX -> IDENTIFIER_KEYS
            Types.FIND_LINKS -> IDENTIFIER_KEYS
            Types.NO_BINARY -> IDENTIFIER_KEYS
            Types.ONLY_BINARY -> IDENTIFIER_KEYS
            Types.REQUIRE_HASHES -> IDENTIFIER_KEYS
            Types.TRUSTED_HOST -> IDENTIFIER_KEYS
            else -> EMPTY_KEYS
        }
    }

    companion object {
        private val BAD_CHARACTER = createTextAttributesKey("REQUIREMENTS_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        private val COMMENT = createTextAttributesKey("REQUIREMENTS_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        private val ENV_VAR = createTextAttributesKey("REQUIREMENTS_ENV_VAR", DefaultLanguageHighlighterColors.STRING)
        private val IDENTIFIER = createTextAttributesKey("IDENTIFIER", DefaultLanguageHighlighterColors.KEYWORD)
        private val PYTHON_STR_C = createTextAttributesKey("REQUIREMENTS_PYTHON_STR_C", DefaultLanguageHighlighterColors.STRING)
        private val VERSION = createTextAttributesKey("REQUIREMENTS_VERSION", DefaultLanguageHighlighterColors.STRING)
        private val OPERATOR = createTextAttributesKey("REQUIREMENTS_OPERATOR", DefaultLanguageHighlighterColors.KEYWORD)
        private val SHARP = createTextAttributesKey("REQUIREMENTS_SHARP", DefaultLanguageHighlighterColors.SEMICOLON)
        private val SEMICOLON = createTextAttributesKey("REQUIREMENTS_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON)

        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
        private val ENV_VAR_KEYS = arrayOf(ENV_VAR)
        private val IDENTIFIER_KEYS = arrayOf(IDENTIFIER)
        private val PYTHON_STR_C_KEYS = arrayOf(PYTHON_STR_C)
        private val VERSION_KEYS = arrayOf(VERSION)
        private val OPERATOR_KEYS = arrayOf(OPERATOR)
        private val SHARP_KEYS = arrayOf(SHARP)
        private val SEMICOLON_KEYS = arrayOf(SEMICOLON)
    }
}
