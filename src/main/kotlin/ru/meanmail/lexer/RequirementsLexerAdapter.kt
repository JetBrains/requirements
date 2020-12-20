package ru.meanmail.lexer

import com.intellij.lexer.FlexAdapter
import java.io.Reader

class RequirementsLexerAdapter : FlexAdapter(RequirementsLexer(null as Reader?))
