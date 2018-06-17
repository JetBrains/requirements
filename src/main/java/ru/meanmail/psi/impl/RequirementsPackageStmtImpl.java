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

public class RequirementsPackageStmtImpl extends RequirementsNamedElementImpl implements RequirementsPackageStmt {

  public RequirementsPackageStmtImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RequirementsVisitor visitor) {
    visitor.visitPackageStmt(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RequirementsVisitor) accept((RequirementsVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public RequirementsExtra getExtra() {
    return findChildByClass(RequirementsExtra.class);
  }

  @Override
  @NotNull
  public RequirementsSimplePackageStmt getSimplePackageStmt() {
    return findNotNullChildByClass(RequirementsSimplePackageStmt.class);
  }

  @Nullable
  public String getPackageName() {
    return RequirementsPsiImplUtil.getPackageName(this);
  }

  @Nullable
  public String getVersion() {
    return RequirementsPsiImplUtil.getVersion(this);
  }

  @Nullable
  public String getExtraPackage() {
    return RequirementsPsiImplUtil.getExtraPackage(this);
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
