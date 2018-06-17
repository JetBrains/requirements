package ru.meanmail

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import java.util.*

class SimpleReference(element: PsiElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {
    private var `package`: String = element.text.substring(textRange.startOffset, textRange.endOffset)
    
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val project = myElement.project
        val packagesStmts = RequirementsUtil.findPackages(project, `package`)
        val results = ArrayList<ResolveResult>()
        for (packageStmt in packagesStmts) {
            results.add(PsiElementResolveResult(packageStmt))
        }
        return results.toTypedArray()
    }
    
    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }
    
    override fun getVariants(): Array<Any> {
        val project = myElement.project
        val packagesStmts = RequirementsUtil.findPackages(project)
        val variants = ArrayList<LookupElement>()
        for (packageStmt in packagesStmts) {
            if (packageStmt.packageName?.length ?: 0 > 0) {
                variants.add(LookupElementBuilder
                        .create(packageStmt)
                        .withIcon(AllIcons.FILE)
                        .withTypeText(packageStmt.containingFile.name)
                )
            }
        }
        return variants.toTypedArray()
    }
}
