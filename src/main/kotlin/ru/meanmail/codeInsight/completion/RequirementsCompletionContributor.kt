package ru.meanmail.codeInsight.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.elementType
import com.intellij.psi.util.prevLeaf
import com.intellij.util.ProcessingContext
import ru.meanmail.getPythonSdk
import ru.meanmail.psi.Name
import ru.meanmail.psi.Types
import ru.meanmail.pypi.getVersionsAsync



class RequirementsCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(Types.VERSION),
            VersionCompletionProvider()
        )
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            ShortOptionCompletionProvider()
        )
    }
}

class VersionCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val originalFile = parameters.originalFile
        val project = originalFile.project
        val sdk = getPythonSdk(project, originalFile.virtualFile) ?: return
        var packageName: String? = null
        var prevLeaf = parameters.originalPosition?.prevLeaf(true)
        while (prevLeaf != null) {
            if (prevLeaf.parent is Name) {
                packageName = prevLeaf.text
                break
            }
            prevLeaf = prevLeaf.prevLeaf(true)
        }
        packageName ?: return

        val task = getVersionsAsync(project, sdk, packageName)
        val application = ApplicationManager.getApplication()
        application.executeOnPooledThread {
            val versions = task.get()
            application.runReadAction {
                val latest = versions.firstOrNull {
                    it.pre == null
                }
                result.addAllElements(
                    versions.map {
                        val isLatest = it == latest
                        val lookupElement = LookupElementBuilder.create(it.presentableText)
                        if (it.pre != null) {
                            return@map lookupElement.withTypeText("pre")
                        }
                        if (isLatest) {
                            return@map lookupElement.withTypeText("latest")
                        }
                        return@map lookupElement
                    }
                )
            }
        }
    }

}

class ShortOptionCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val prevLeaf = parameters.originalPosition?.prevLeaf(true)
        when {
            prevLeaf?.elementType == Types.SHORT_OPTION -> {
                result.addElement(LookupElementBuilder.create("r"))
                result.addElement(LookupElementBuilder.create("e"))
                result.addElement(LookupElementBuilder.create("c"))
                result.addElement(LookupElementBuilder.create("-"))
            }
            prevLeaf?.elementType == Types.LONG_OPTION -> {
                result.addElement(LookupElementBuilder.create("requirement"))
                result.addElement(LookupElementBuilder.create("constraint"))
                result.addElement(LookupElementBuilder.create("editable"))
                result.addElement(LookupElementBuilder.create("index-url"))
                result.addElement(LookupElementBuilder.create("extra-index-url"))
                result.addElement(LookupElementBuilder.create("no-index"))
                result.addElement(LookupElementBuilder.create("find-links"))
                result.addElement(LookupElementBuilder.create("no-binary"))
                result.addElement(LookupElementBuilder.create("only-binary"))
                result.addElement(LookupElementBuilder.create("prefer-binary"))
                result.addElement(LookupElementBuilder.create("require-hashes"))
                result.addElement(LookupElementBuilder.create("pre"))
                result.addElement(LookupElementBuilder.create("trusted-host"))
                result.addElement(LookupElementBuilder.create("use-feature"))
            }
            prevLeaf?.prevLeaf(true)
                .elementType in listOf(Types.ONLY_BINARY, Types.NO_BINARY) -> {
                result.addElement(LookupElementBuilder.create(":all:"))
                result.addElement(LookupElementBuilder.create(":none:"))
            }
        }
    }
}
