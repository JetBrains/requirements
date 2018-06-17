// This is a generated file. Not intended for manual editing.
package ru.meanmail.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import ru.meanmail.psi.impl.*;

public interface RequirementsTypes {

  IElementType EDITABLE_REQUIREMENT_STMT = new RequirementsElementType("EDITABLE_REQUIREMENT_STMT");
  IElementType EXTRA = new RequirementsElementType("EXTRA");
  IElementType PACKAGE_STMT = new RequirementsElementType("PACKAGE_STMT");
  IElementType PATH_STMT = new RequirementsElementType("PATH_STMT");
  IElementType REQUIREMENT_STMT = new RequirementsElementType("REQUIREMENT_STMT");
  IElementType SIMPLE_PACKAGE_STMT = new RequirementsElementType("SIMPLE_PACKAGE_STMT");
  IElementType URL_STMT = new RequirementsElementType("URL_STMT");

  IElementType BRANCH = new RequirementsTokenType("BRANCH");
  IElementType COMMENT = new RequirementsTokenType("COMMENT");
  IElementType CRLF = new RequirementsTokenType("CRLF");
  IElementType EGG = new RequirementsTokenType("EGG");
  IElementType EOF = new RequirementsTokenType("EOF");
  IElementType FILENAME = new RequirementsTokenType("FILENAME");
  IElementType LSBRACE = new RequirementsTokenType("LSBRACE");
  IElementType PACKAGE = new RequirementsTokenType("PACKAGE");
  IElementType PATH = new RequirementsTokenType("PATH");
  IElementType REQUIREMENT = new RequirementsTokenType("REQUIREMENT");
  IElementType REQUIREMENT_EDITABLE = new RequirementsTokenType("REQUIREMENT_EDITABLE");
  IElementType RSBRACE = new RequirementsTokenType("RSBRACE");
  IElementType SCHEMA = new RequirementsTokenType("SCHEMA");
  IElementType SEPARATOR = new RequirementsTokenType("SEPARATOR");
  IElementType VERSION = new RequirementsTokenType("VERSION");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == EDITABLE_REQUIREMENT_STMT) {
        return new RequirementsEditableRequirementStmtImpl(node);
      }
      else if (type == EXTRA) {
        return new RequirementsExtraImpl(node);
      }
      else if (type == PACKAGE_STMT) {
        return new RequirementsPackageStmtImpl(node);
      }
      else if (type == PATH_STMT) {
        return new RequirementsPathStmtImpl(node);
      }
      else if (type == REQUIREMENT_STMT) {
        return new RequirementsRequirementStmtImpl(node);
      }
      else if (type == SIMPLE_PACKAGE_STMT) {
        return new RequirementsSimplePackageStmtImpl(node);
      }
      else if (type == URL_STMT) {
        return new RequirementsUrlStmtImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
