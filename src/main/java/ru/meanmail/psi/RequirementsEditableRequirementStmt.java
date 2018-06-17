// This is a generated file. Not intended for manual editing.
package ru.meanmail.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface RequirementsEditableRequirementStmt extends RequirementsNamedElement {

  @NotNull
  RequirementsUrlStmt getUrlStmt();

  @Nullable
  String getURL();

  @Nullable
  String getBranch();

  @Nullable
  String getEgg();

  @Nullable
  String getName();

  @NotNull
  PsiElement setName(String newName);

  @Nullable
  PsiElement getNameIdentifier();

}
