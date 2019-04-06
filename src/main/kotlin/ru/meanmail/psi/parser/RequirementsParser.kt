package ru.meanmail.psi.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.lang.parser.GeneratedParserUtilBase.*
import com.intellij.psi.tree.IElementType
import ru.meanmail.psi.RequirementsTypes.Companion.BRANCH
import ru.meanmail.psi.RequirementsTypes.Companion.COMMENT
import ru.meanmail.psi.RequirementsTypes.Companion.CRLF
import ru.meanmail.psi.RequirementsTypes.Companion.EDITABLE_REQUIREMENT_STMT
import ru.meanmail.psi.RequirementsTypes.Companion.EGG
import ru.meanmail.psi.RequirementsTypes.Companion.EXTRA
import ru.meanmail.psi.RequirementsTypes.Companion.FILENAME
import ru.meanmail.psi.RequirementsTypes.Companion.FILENAME_STMT
import ru.meanmail.psi.RequirementsTypes.Companion.LSBRACE
import ru.meanmail.psi.RequirementsTypes.Companion.PACKAGE
import ru.meanmail.psi.RequirementsTypes.Companion.PACKAGE_STMT
import ru.meanmail.psi.RequirementsTypes.Companion.PATH
import ru.meanmail.psi.RequirementsTypes.Companion.PATH_STMT
import ru.meanmail.psi.RequirementsTypes.Companion.REQUIREMENT
import ru.meanmail.psi.RequirementsTypes.Companion.REQUIREMENT_EDITABLE
import ru.meanmail.psi.RequirementsTypes.Companion.REQUIREMENT_STMT
import ru.meanmail.psi.RequirementsTypes.Companion.RSBRACE
import ru.meanmail.psi.RequirementsTypes.Companion.SCHEMA
import ru.meanmail.psi.RequirementsTypes.Companion.RELATION
import ru.meanmail.psi.RequirementsTypes.Companion.SIMPLE_PACKAGE_STMT
import ru.meanmail.psi.RequirementsTypes.Companion.URL_STMT
import ru.meanmail.psi.RequirementsTypes.Companion.VERSION

class RequirementsParser : PsiParser, LightPsiParser {
    
    override fun parse(t: IElementType, b: PsiBuilder): ASTNode {
        parseLight(t, b)
        return b.treeBuilt
    }
    
    override fun parseLight(type: IElementType, b: PsiBuilder) {
        val builder = adapt_builder_(type, b, this, null)
        val m = enter_section_(builder, 0, _COLLAPSE_, null)
        val r = when (type) {
            EDITABLE_REQUIREMENT_STMT -> editable_requirement_stmt(builder, 0)
            EXTRA -> extra(builder, 0)
            FILENAME_STMT -> filename_stmt(builder, 0)
            PACKAGE_STMT -> package_stmt(builder, 0)
            PATH_STMT -> path_stmt(builder, 0)
            REQUIREMENT_STMT -> requirement_stmt(builder, 0)
            SIMPLE_PACKAGE_STMT -> simple_package_stmt(builder, 0)
            URL_STMT -> url_stmt(builder, 0)
            else -> parse_root_(builder, 0)
        }
        exit_section_(builder, 0, m, type, r, true, TRUE_CONDITION)
    }
    
    companion object {
        
        private fun parse_root_(builder: PsiBuilder, l: Int): Boolean {
            return requirementsFile(builder, l + 1)
        }
        
        /* ********************************************************** */
        // REQUIREMENT_EDITABLE url_stmt
        private fun editable_requirement_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "editable_requirement_stmt")) return false
            if (!nextTokenIs(b, REQUIREMENT_EDITABLE)) return false
            val m = enter_section_(b)
            var r = consumeToken(b, REQUIREMENT_EDITABLE)
            r = r && url_stmt(b, l + 1)
            exit_section_(b, m, EDITABLE_REQUIREMENT_STMT, r)
            return r
        }
        
        /* ********************************************************** */
        // LSBRACE PACKAGE RSBRACE
        private fun extra(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extra")) return false
            if (!nextTokenIs(b, LSBRACE)) return false
            val m = enter_section_(b)
            val r = consumeTokens(b, 0, LSBRACE, PACKAGE, RSBRACE)
            exit_section_(b, m, EXTRA, r)
            return r
        }
        
        /* ********************************************************** */
        // FILENAME
        private fun filename_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "filename_stmt")) return false
            if (!nextTokenIs(b, FILENAME)) return false
            val m = enter_section_(b)
            val r = consumeToken(b, FILENAME)
            exit_section_(b, m, FILENAME_STMT, r)
            return r
        }
        
        /* ********************************************************** */
        // stmt|COMMENT|CRLF
        private fun item_(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "item_")) return false
            var r = stmt(b, l + 1)
            if (!r) r = consumeToken(b, COMMENT)
            if (!r) r = consumeToken(b, CRLF)
            return r
        }
        
        /* ********************************************************** */
        // simple_package_stmt extra?
        private fun package_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "package_stmt")) return false
            if (!nextTokenIs(b, PACKAGE)) return false
            val m = enter_section_(b)
            var r = simple_package_stmt(b, l + 1)
            r = r && package_stmt_1(b, l + 1)
            exit_section_(b, m, PACKAGE_STMT, r)
            return r
        }
        
        // extra?
        private fun package_stmt_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "package_stmt_1")) return false
            extra(b, l + 1)
            return true
        }
        
        /* ********************************************************** */
        // SCHEMA? PATH
        private fun path_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_stmt")) return false
            if (!nextTokenIs(b, "<path stmt>", PATH, SCHEMA)) return false
            val m = enter_section_(b, l, _NONE_, PATH_STMT, "<path stmt>")
            var r = path_stmt_0(b, l + 1)
            r = r && consumeToken(b, PATH)
            exit_section_(b, l, m, r, false, null)
            return r
        }
        
        // SCHEMA?
        private fun path_stmt_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_stmt_0")) return false
            consumeToken(b, SCHEMA)
            return true
        }
        
        /* ********************************************************** */
        // REQUIREMENT filename_stmt
        private fun requirement_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "requirement_stmt")) return false
            if (!nextTokenIs(b, REQUIREMENT)) return false
            val m = enter_section_(b)
            var r = consumeToken(b, REQUIREMENT)
            r = r && filename_stmt(b, l + 1)
            exit_section_(b, m, REQUIREMENT_STMT, r)
            return r
        }
        
        /* ********************************************************** */
        // item_*
        private fun requirementsFile(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "requirementsFile")) return false
            while (true) {
                val c = current_position_(b)
                if (!item_(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "requirementsFile", c)) break
            }
            return true
        }
        
        /* ********************************************************** */
        // PACKAGE (RELATION VERSION)?
        private fun simple_package_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "simple_package_stmt")) return false
            if (!nextTokenIs(b, PACKAGE)) return false
            val m = enter_section_(b)
            var r = consumeToken(b, PACKAGE)
            r = r && simple_package_stmt_1(b, l + 1)
            exit_section_(b, m, SIMPLE_PACKAGE_STMT, r)
            return r
        }
        
        // (RELATION VERSION)?
        private fun simple_package_stmt_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "simple_package_stmt_1")) return false
            simple_package_stmt_1_0(b, l + 1)
            return true
        }
        
        // RELATION VERSION
        private fun simple_package_stmt_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "simple_package_stmt_1_0")) return false
            val m = enter_section_(b)
            val r = consumeTokens(b, 0, RELATION, VERSION)
            exit_section_(b, m, null, r)
            return r
        }
        
        /* ********************************************************** */
        // (package_stmt|requirement_stmt|editable_requirement_stmt|url_stmt) (CRLF|<<eof>>)
        private fun stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "stmt")) return false
            val m = enter_section_(b)
            var r = stmt_0(b, l + 1)
            r = r && stmt_1(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }
        
        // package_stmt|requirement_stmt|editable_requirement_stmt|url_stmt
        private fun stmt_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "stmt_0")) return false
            var r = package_stmt(b, l + 1)
            if (!r) r = requirement_stmt(b, l + 1)
            if (!r) r = editable_requirement_stmt(b, l + 1)
            if (!r) r = url_stmt(b, l + 1)
            return r
        }
        
        // CRLF|<<eof>>
        private fun stmt_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "stmt_1")) return false
            val m = enter_section_(b)
            var r = consumeToken(b, CRLF)
            if (!r) r = eof(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }
        
        /* ********************************************************** */
        // path_stmt BRANCH? (EGG simple_package_stmt)?
        private fun url_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "url_stmt")) return false
            if (!nextTokenIs(b, "<url stmt>", PATH, SCHEMA)) return false
            val m = enter_section_(b, l, _NONE_, URL_STMT, "<url stmt>")
            var r = path_stmt(b, l + 1)
            r = r && url_stmt_1(b, l + 1)
            r = r && url_stmt_2(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }
        
        // BRANCH?
        private fun url_stmt_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "url_stmt_1")) return false
            consumeToken(b, BRANCH)
            return true
        }
        
        // (EGG simple_package_stmt)?
        private fun url_stmt_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "url_stmt_2")) return false
            url_stmt_2_0(b, l + 1)
            return true
        }
        
        // EGG simple_package_stmt
        private fun url_stmt_2_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "url_stmt_2_0")) return false
            val m = enter_section_(b)
            var r = consumeToken(b, EGG)
            r = r && simple_package_stmt(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }
    }
    
}
