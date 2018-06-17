package ru.meanmail

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import ru.meanmail.psi.RequirementsTypes


class RequirementsCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RequirementsTypes.VERSION).withLanguage(RequirementsLanguage),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters,
                                                       context: ProcessingContext?,
                                                       resultSet: CompletionResultSet) {
                        resultSet.addElement(LookupElementBuilder.create("Hello"))
                    }
                }
        )
    }
}
