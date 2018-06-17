// This is a generated file. Not intended for manual editing.
package ru.meanmail.psi.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static ru.meanmail.psi.RequirementsTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class RequirementsParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == EDITABLE_REQUIREMENT_STMT) {
      r = editable_requirement_stmt(b, 0);
    }
    else if (t == EXTRA) {
      r = extra(b, 0);
    }
    else if (t == PACKAGE_STMT) {
      r = package_stmt(b, 0);
    }
    else if (t == PATH_STMT) {
      r = path_stmt(b, 0);
    }
    else if (t == REQUIREMENT_STMT) {
      r = requirement_stmt(b, 0);
    }
    else if (t == SIMPLE_PACKAGE_STMT) {
      r = simple_package_stmt(b, 0);
    }
    else if (t == URL_STMT) {
      r = url_stmt(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return requirementsFile(b, l + 1);
  }

  /* ********************************************************** */
  // REQUIREMENT_EDITABLE url_stmt
  public static boolean editable_requirement_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "editable_requirement_stmt")) return false;
    if (!nextTokenIs(b, REQUIREMENT_EDITABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REQUIREMENT_EDITABLE);
    r = r && url_stmt(b, l + 1);
    exit_section_(b, m, EDITABLE_REQUIREMENT_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // LSBRACE PACKAGE RSBRACE
  public static boolean extra(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extra")) return false;
    if (!nextTokenIs(b, LSBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LSBRACE, PACKAGE, RSBRACE);
    exit_section_(b, m, EXTRA, r);
    return r;
  }

  /* ********************************************************** */
  // stmt|COMMENT|CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = stmt(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    return r;
  }

  /* ********************************************************** */
  // simple_package_stmt extra?
  public static boolean package_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_stmt")) return false;
    if (!nextTokenIs(b, PACKAGE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = simple_package_stmt(b, l + 1);
    r = r && package_stmt_1(b, l + 1);
    exit_section_(b, m, PACKAGE_STMT, r);
    return r;
  }

  // extra?
  private static boolean package_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_stmt_1")) return false;
    extra(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // SCHEMA? PATH
  public static boolean path_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "path_stmt")) return false;
    if (!nextTokenIs(b, "<path stmt>", PATH, SCHEMA)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PATH_STMT, "<path stmt>");
    r = path_stmt_0(b, l + 1);
    r = r && consumeToken(b, PATH);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // SCHEMA?
  private static boolean path_stmt_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "path_stmt_0")) return false;
    consumeToken(b, SCHEMA);
    return true;
  }

  /* ********************************************************** */
  // REQUIREMENT FILENAME
  public static boolean requirement_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "requirement_stmt")) return false;
    if (!nextTokenIs(b, REQUIREMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, REQUIREMENT, FILENAME);
    exit_section_(b, m, REQUIREMENT_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean requirementsFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "requirementsFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "requirementsFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (PACKAGE SEPARATOR VERSION) | PACKAGE
  public static boolean simple_package_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_package_stmt")) return false;
    if (!nextTokenIs(b, PACKAGE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = simple_package_stmt_0(b, l + 1);
    if (!r) r = consumeToken(b, PACKAGE);
    exit_section_(b, m, SIMPLE_PACKAGE_STMT, r);
    return r;
  }

  // PACKAGE SEPARATOR VERSION
  private static boolean simple_package_stmt_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_package_stmt_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PACKAGE, SEPARATOR, VERSION);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (package_stmt|requirement_stmt|editable_requirement_stmt|url_stmt) (CRLF|EOF)
  static boolean stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmt")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = stmt_0(b, l + 1);
    r = r && stmt_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // package_stmt|requirement_stmt|editable_requirement_stmt|url_stmt
  private static boolean stmt_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmt_0")) return false;
    boolean r;
    r = package_stmt(b, l + 1);
    if (!r) r = requirement_stmt(b, l + 1);
    if (!r) r = editable_requirement_stmt(b, l + 1);
    if (!r) r = url_stmt(b, l + 1);
    return r;
  }

  // CRLF|EOF
  private static boolean stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmt_1")) return false;
    boolean r;
    r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, EOF);
    return r;
  }

  /* ********************************************************** */
  // path_stmt BRANCH? (EGG simple_package_stmt)?
  public static boolean url_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "url_stmt")) return false;
    if (!nextTokenIs(b, "<url stmt>", PATH, SCHEMA)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, URL_STMT, "<url stmt>");
    r = path_stmt(b, l + 1);
    r = r && url_stmt_1(b, l + 1);
    r = r && url_stmt_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // BRANCH?
  private static boolean url_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "url_stmt_1")) return false;
    consumeToken(b, BRANCH);
    return true;
  }

  // (EGG simple_package_stmt)?
  private static boolean url_stmt_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "url_stmt_2")) return false;
    url_stmt_2_0(b, l + 1);
    return true;
  }

  // EGG simple_package_stmt
  private static boolean url_stmt_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "url_stmt_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EGG);
    r = r && simple_package_stmt(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
