// This is a generated file. Not intended for manual editing.
package ru.meanmail.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static ru.meanmail.psi.RequirementsTypes.*;
import ru.meanmail.psi.*;

public class RequirementsUrlStmtImpl extends RequirementsNamedElementImpl implements RequirementsUrlStmt {

  public RequirementsUrlStmtImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RequirementsVisitor visitor) {
    visitor.visitUrlStmt(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RequirementsVisitor) accept((RequirementsVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public RequirementsPathStmt getPathStmt() {
    return findNotNullChildByClass(RequirementsPathStmt.class);
  }

  @Override
  @Nullable
  public RequirementsSimplePackageStmt getSimplePackageStmt() {
    return findChildByClass(RequirementsSimplePackageStmt.class);
  }

  @Nullable
  public String getURL() {
    return RequirementsPsiImplUtil.getURL(this);
  }

  @Nullable
  public String getBranch() {
    return RequirementsPsiImplUtil.getBranch(this);
  }

  @Nullable
  public String getEgg() {
    return RequirementsPsiImplUtil.getEgg(this);
  }

  @Nullable
  public String getName() {
    return RequirementsPsiImplUtil.getName(this);
  }

  @NotNull
  public PsiElement setName(String newName) {
    return RequirementsPsiImplUtil.setName(this, newName);
  }

  @Nullable
  public PsiElement getNameIdentifier() {
    return RequirementsPsiImplUtil.getNameIdentifier(this);
  }

}
