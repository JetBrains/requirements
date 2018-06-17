// This is a generated file. Not intended for manual editing.
package ru.meanmail.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static ru.meanmail.psi.RequirementsTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import ru.meanmail.psi.*;

public class RequirementsPathStmtImpl extends ASTWrapperPsiElement implements RequirementsPathStmt {

  public RequirementsPathStmtImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RequirementsVisitor visitor) {
    visitor.visitPathStmt(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RequirementsVisitor) accept((RequirementsVisitor)visitor);
    else super.accept(visitor);
  }

}
