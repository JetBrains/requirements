package ru.meanmail.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor

class RequirementsVisitor : PsiElementVisitor() {
    
    fun visitEditableRequirementStmt(o: RequirementsEditableRequirementStmt) {
        visitNamedElement(o)
    }
    
    fun visitExtra(o: RequirementsExtra) {
        visitPsiElement(o)
    }
    
    fun visitFilenameStmt(o: RequirementsFilenameStmt) {
        visitNamedElement(o)
    }
    
    fun visitPackageNameStmt(o: RequirementsPackageNameStmt) {
        visitNamedElement(o)
    }
    
    fun visitPackageStmt(o: RequirementsPackageStmt) {
        visitNamedElement(o)
    }
    
    fun visitPathStmt(o: RequirementsPathStmt) {
        visitPsiElement(o)
    }
    
    fun visitRequirementStmt(o: RequirementsRequirementStmt) {
        visitNamedElement(o)
    }
    
    fun visitSimplePackageStmt(o: RequirementsSimplePackageStmt) {
        visitPsiElement(o)
    }
    
    fun visitUrlStmt(o: RequirementsUrlStmt) {
        visitNamedElement(o)
    }
    
    fun visitVersionStmt(o: RequirementsVersionStmt) {
        visitNamedElement(o)
    }
    
    fun visitNamedElement(o: RequirementsNamedElement) {
        visitPsiElement(o)
    }
    
    fun visitPsiElement(o: PsiElement) {
        visitElement(o)
    }
    
}
