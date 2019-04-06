package ru.meanmail

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.psi.RequirementsTypes
import ru.meanmail.psi.parser.RequirementsParser

class RequirementsParserDefinition : ParserDefinition {
    
    override fun createLexer(project: Project): Lexer {
        return RequirementsLexerAdapter()
    }
    
    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }
    
    override fun getCommentTokens(): TokenSet {
        return COMMENTS
    }
    
    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }
    
    override fun createParser(project: Project): PsiParser {
        return RequirementsParser()
    }
    
    override fun getFileNodeType(): IFileElementType {
        return FILE
    }
    
    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return RequirementsFile(viewProvider)
    }
    
    override fun spaceExistenceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }
    
    override fun createElement(node: ASTNode): PsiElement {
        return RequirementsTypes.Factory.createElement(node)
    }
    
    companion object {
        val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
        val COMMENTS = TokenSet.create(RequirementsTypes.COMMENT)
        val FILE = IFileElementType(RequirementsLanguage)
    }
}
