// This is a generated file. Not intended for manual editing.
package ru.meanmail.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class RequirementsVisitor extends PsiElementVisitor {

  public void visitEditableRequirementStmt(@NotNull RequirementsEditableRequirementStmt o) {
    visitNamedElement(o);
  }

  public void visitExtra(@NotNull RequirementsExtra o) {
    visitPsiElement(o);
  }

  public void visitPackageStmt(@NotNull RequirementsPackageStmt o) {
    visitNamedElement(o);
  }

  public void visitPathStmt(@NotNull RequirementsPathStmt o) {
    visitPsiElement(o);
  }

  public void visitRequirementStmt(@NotNull RequirementsRequirementStmt o) {
    visitNamedElement(o);
  }

  public void visitSimplePackageStmt(@NotNull RequirementsSimplePackageStmt o) {
    visitPsiElement(o);
  }

  public void visitUrlStmt(@NotNull RequirementsUrlStmt o) {
    visitNamedElement(o);
  }

  public void visitNamedElement(@NotNull RequirementsNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
