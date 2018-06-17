package ru.meanmail

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import ru.meanmail.psi.RequirementsPackageStmt
import ru.meanmail.psi.RequirementsTypes

class RequirementsFindUsageProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner? {
        return DefaultWordsScanner(RequirementsLexerAdapter(),
                TokenSet.create(RequirementsTypes.PACKAGE),
                TokenSet.create(RequirementsTypes.COMMENT),
                TokenSet.EMPTY)
    }
    
    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }
    
    override fun getHelpId(psiElement: PsiElement): String? {
        return null
    }
    
    override fun getType(element: PsiElement): String {
        return if (element is RequirementsPackageStmt) {
            "package"
        } else {
            ""
        }
    }
    
    override fun getDescriptiveName(element: PsiElement): String {
        return if (element is RequirementsPackageStmt) {
            element.packageName ?: ""
        } else {
            ""
        }
    }
    
    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return if (element is RequirementsPackageStmt) {
            element.packageName + ":" + element.version
        } else {
            ""
        }
    }
}
