package ru.meanmail.psi.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.lang.parser.GeneratedParserUtilBase.*
import com.intellij.psi.tree.IElementType
import ru.meanmail.psi.Types.Companion.AND
import ru.meanmail.psi.Types.Companion.AT
import ru.meanmail.psi.Types.Companion.AUTHORITY
import ru.meanmail.psi.Types.Companion.BINARY_ALL
import ru.meanmail.psi.Types.Companion.BINARY_LIST
import ru.meanmail.psi.Types.Companion.BINARY_NONE
import ru.meanmail.psi.Types.Companion.COLON
import ru.meanmail.psi.Types.Companion.COMMA
import ru.meanmail.psi.Types.Companion.COMMENT
import ru.meanmail.psi.Types.Companion.CONSTRAINT
import ru.meanmail.psi.Types.Companion.CONSTRAINT_REQ
import ru.meanmail.psi.Types.Companion.DEC_OCTET
import ru.meanmail.psi.Types.Companion.DIGIT
import ru.meanmail.psi.Types.Companion.DOLLAR_SIGN
import ru.meanmail.psi.Types.Companion.DOT
import ru.meanmail.psi.Types.Companion.DQUOTE
import ru.meanmail.psi.Types.Companion.EDITABLE
import ru.meanmail.psi.Types.Companion.EDITABLE_REQ
import ru.meanmail.psi.Types.Companion.ENV_VAR
import ru.meanmail.psi.Types.Companion.ENV_VARIABLE
import ru.meanmail.psi.Types.Companion.EOL
import ru.meanmail.psi.Types.Companion.EQUAL
import ru.meanmail.psi.Types.Companion.EXTRAS
import ru.meanmail.psi.Types.Companion.EXTRAS_LIST
import ru.meanmail.psi.Types.Companion.EXTRA_INDEX_URL
import ru.meanmail.psi.Types.Companion.EXTRA_INDEX_URL_REQ
import ru.meanmail.psi.Types.Companion.FIND_LINKS
import ru.meanmail.psi.Types.Companion.FIND_LINKS_REQ
import ru.meanmail.psi.Types.Companion.FRAGMENT
import ru.meanmail.psi.Types.Companion.HASH
import ru.meanmail.psi.Types.Companion.HASH_OPTION
import ru.meanmail.psi.Types.Companion.HEX
import ru.meanmail.psi.Types.Companion.HEXDIG
import ru.meanmail.psi.Types.Companion.HIER_PART
import ru.meanmail.psi.Types.Companion.HOST
import ru.meanmail.psi.Types.Companion.H_16
import ru.meanmail.psi.Types.Companion.H_16_COLON
import ru.meanmail.psi.Types.Companion.IDENTIFIER
import ru.meanmail.psi.Types.Companion.IN
import ru.meanmail.psi.Types.Companion.INDEX_URL
import ru.meanmail.psi.Types.Companion.INDEX_URL_REQ
import ru.meanmail.psi.Types.Companion.IP_LITERAL
import ru.meanmail.psi.Types.Companion.I_PV_4_ADDRESS
import ru.meanmail.psi.Types.Companion.I_PV_6_ADDRESS
import ru.meanmail.psi.Types.Companion.I_PV_FUTURE
import ru.meanmail.psi.Types.Companion.LBRACE
import ru.meanmail.psi.Types.Companion.LETTER
import ru.meanmail.psi.Types.Companion.LONG_OPTION
import ru.meanmail.psi.Types.Companion.LPARENTHESIS
import ru.meanmail.psi.Types.Companion.LSBRACE
import ru.meanmail.psi.Types.Companion.LS_32
import ru.meanmail.psi.Types.Companion.MARKER
import ru.meanmail.psi.Types.Companion.MARKER_AND
import ru.meanmail.psi.Types.Companion.MARKER_EXPR
import ru.meanmail.psi.Types.Companion.MARKER_OP
import ru.meanmail.psi.Types.Companion.MARKER_OR
import ru.meanmail.psi.Types.Companion.MARKER_VAR
import ru.meanmail.psi.Types.Companion.MINUS
import ru.meanmail.psi.Types.Companion.NAME
import ru.meanmail.psi.Types.Companion.NAME_REQ
import ru.meanmail.psi.Types.Companion.NOT
import ru.meanmail.psi.Types.Companion.NO_BINARY
import ru.meanmail.psi.Types.Companion.NO_BINARY_REQ
import ru.meanmail.psi.Types.Companion.NO_INDEX
import ru.meanmail.psi.Types.Companion.NO_INDEX_REQ
import ru.meanmail.psi.Types.Companion.NZ
import ru.meanmail.psi.Types.Companion.ONLY_BINARY
import ru.meanmail.psi.Types.Companion.ONLY_BINARY_REQ
import ru.meanmail.psi.Types.Companion.OPTION
import ru.meanmail.psi.Types.Companion.OR
import ru.meanmail.psi.Types.Companion.PATH_ABEMPTY
import ru.meanmail.psi.Types.Companion.PATH_ABSOLUTE
import ru.meanmail.psi.Types.Companion.PATH_EMPTY
import ru.meanmail.psi.Types.Companion.PATH_NOSCHEME
import ru.meanmail.psi.Types.Companion.PATH_REQ
import ru.meanmail.psi.Types.Companion.PATH_ROOTLESS
import ru.meanmail.psi.Types.Companion.PCHAR
import ru.meanmail.psi.Types.Companion.PCT_ENCODED
import ru.meanmail.psi.Types.Companion.PERCENT_SIGN
import ru.meanmail.psi.Types.Companion.PLUS
import ru.meanmail.psi.Types.Companion.PORT
import ru.meanmail.psi.Types.Companion.PYTHON_STR
import ru.meanmail.psi.Types.Companion.PYTHON_STR_C
import ru.meanmail.psi.Types.Companion.QUERY
import ru.meanmail.psi.Types.Companion.QUESTION_MARK
import ru.meanmail.psi.Types.Companion.QUOTED_MARKER
import ru.meanmail.psi.Types.Companion.RBRACE
import ru.meanmail.psi.Types.Companion.REFER
import ru.meanmail.psi.Types.Companion.REFER_REQ
import ru.meanmail.psi.Types.Companion.REG_NAME
import ru.meanmail.psi.Types.Companion.RELATIVE_PART
import ru.meanmail.psi.Types.Companion.RELATIVE_REF
import ru.meanmail.psi.Types.Companion.REQUIRE_HASHES
import ru.meanmail.psi.Types.Companion.REQUIRE_HASHES_REQ
import ru.meanmail.psi.Types.Companion.RPARENTHESIS
import ru.meanmail.psi.Types.Companion.RSBRACE
import ru.meanmail.psi.Types.Companion.SCHEME
import ru.meanmail.psi.Types.Companion.SEGMENT
import ru.meanmail.psi.Types.Companion.SEGMENT_NZ
import ru.meanmail.psi.Types.Companion.SEGMENT_NZ_NC
import ru.meanmail.psi.Types.Companion.SEMICOLON
import ru.meanmail.psi.Types.Companion.SHARP
import ru.meanmail.psi.Types.Companion.SHORT_OPTION
import ru.meanmail.psi.Types.Companion.SLASH
import ru.meanmail.psi.Types.Companion.SQUOTE
import ru.meanmail.psi.Types.Companion.SUB_DELIMS
import ru.meanmail.psi.Types.Companion.TILDA
import ru.meanmail.psi.Types.Companion.TRUSTED_HOST
import ru.meanmail.psi.Types.Companion.TRUSTED_HOST_REQ
import ru.meanmail.psi.Types.Companion.UNDERSCORE
import ru.meanmail.psi.Types.Companion.UNRESERVED
import ru.meanmail.psi.Types.Companion.URI
import ru.meanmail.psi.Types.Companion.URI_REFERENCE
import ru.meanmail.psi.Types.Companion.URLSPEC
import ru.meanmail.psi.Types.Companion.URL_REQ
import ru.meanmail.psi.Types.Companion.USERINFO
import ru.meanmail.psi.Types.Companion.VARIABLE_NAME
import ru.meanmail.psi.Types.Companion.VERSION
import ru.meanmail.psi.Types.Companion.VERSIONSPEC
import ru.meanmail.psi.Types.Companion.VERSION_CMP
import ru.meanmail.psi.Types.Companion.VERSION_CMP_STMT
import ru.meanmail.psi.Types.Companion.VERSION_MANY
import ru.meanmail.psi.Types.Companion.VERSION_ONE
import ru.meanmail.psi.Types.Companion.VERSION_STMT
import ru.meanmail.psi.Types.Companion.WHITE_SPACE

class RequirementsParser : PsiParser, LightPsiParser {

    override fun parse(t: IElementType, b: PsiBuilder): ASTNode {
        parseLight(t, b)
        return b.treeBuilt
    }

    override fun parseLight(type: IElementType, b: PsiBuilder) {
        val builder = adapt_builder_(type, b, this, null)
        val marker = enter_section_(builder, 0, _COLLAPSE_, null)
        val result = parse_root_(type, builder)
        exit_section_(builder, 0, marker, type, result, true, TRUE_CONDITION)
    }

    protected fun parse_root_(type: IElementType, builder: PsiBuilder): Boolean {
        return parse_root_(type, builder, 0)
    }

    companion object {
        private fun parse_root_(type: IElementType,
                                builder: PsiBuilder, level: Int): Boolean {
            return requirementsFile(builder, level + 1)
        }

        /* ********************************************************** */
        // LSBRACE (IPv6address | IPvFuture) RSBRACE
        private fun IP_literal(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IP_literal")) return false
            if (!nextTokenIs(b, LSBRACE)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, LSBRACE)
            r = r && IP_literal_1(b, l + 1)
            r = r && consumeToken(b, RSBRACE)
            exit_section_(b, m, IP_LITERAL, r)
            return r
        }

        // IPv6address | IPvFuture
        private fun IP_literal_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IP_literal_1")) return false
            var r: Boolean
            r = IPv6address(b, l + 1)
            if (!r) r = IPvFuture(b, l + 1)
            return r
        }

        /* ********************************************************** */
        // dec_octet DOT dec_octet DOT dec_octet DOT dec_octet
        private fun IPv4address(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv4address")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, I_PV_4_ADDRESS, "<i pv 4 address>")
            r = dec_octet(b, l + 1)
            r = r && consumeToken(b, DOT)
            r = r && dec_octet(b, l + 1)
            r = r && consumeToken(b, DOT)
            r = r && dec_octet(b, l + 1)
            r = r && consumeToken(b, DOT)
            r = r && dec_octet(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // h16_colon h16_colon h16_colon h16_colon h16_colon h16_colon ls32
        //                   | COLON COLON h16_colon h16_colon h16_colon h16_colon h16_colon ls32
        //                   | h16?  COLON COLON h16_colon h16_colon h16_colon h16_colon ls32
        //                   | (h16_colon? h16)? COLON COLON h16_colon h16_colon h16_colon ls32
        //                   | (h16_colon? h16_colon? h16 )? COLON COLON h16_colon h16_colon ls32
        //                   | (h16_colon? h16_colon? h16_colon? h16 )? COLON COLON h16_colon ls32
        //                   | (h16_colon? h16_colon? h16_colon? h16_colon? h16 )? COLON COLON ls32
        //                   | (h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16 )? COLON COLON h16
        //                   | (h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16 )? COLON COLON
        private fun IPv6address(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, I_PV_6_ADDRESS, "<i pv 6 address>")
            r = IPv6address_0(b, l + 1)
            if (!r) r = IPv6address_1(b, l + 1)
            if (!r) r = IPv6address_2(b, l + 1)
            if (!r) r = IPv6address_3(b, l + 1)
            if (!r) r = IPv6address_4(b, l + 1)
            if (!r) r = IPv6address_5(b, l + 1)
            if (!r) r = IPv6address_6(b, l + 1)
            if (!r) r = IPv6address_7(b, l + 1)
            if (!r) r = IPv6address_8(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // h16_colon h16_colon h16_colon h16_colon h16_colon h16_colon ls32
        private fun IPv6address_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && ls32(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // COLON COLON h16_colon h16_colon h16_colon h16_colon h16_colon ls32
        private fun IPv6address_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_1")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeTokens(b, 0, COLON, COLON)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && ls32(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16?  COLON COLON h16_colon h16_colon h16_colon h16_colon ls32
        private fun IPv6address_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_2")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_2_0(b, l + 1)
            r = r && consumeTokens(b, 0, COLON, COLON)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && ls32(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16?
        private fun IPv6address_2_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_2_0")) return false
            h16(b, l + 1)
            return true
        }

        // (h16_colon? h16)? COLON COLON h16_colon h16_colon h16_colon ls32
        private fun IPv6address_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_3")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_3_0(b, l + 1)
            r = r && consumeTokens(b, 0, COLON, COLON)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && ls32(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (h16_colon? h16)?
        private fun IPv6address_3_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_3_0")) return false
            IPv6address_3_0_0(b, l + 1)
            return true
        }

        // h16_colon? h16
        private fun IPv6address_3_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_3_0_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_3_0_0_0(b, l + 1)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16_colon?
        private fun IPv6address_3_0_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_3_0_0_0")) return false
            h16_colon(b, l + 1)
            return true
        }

        // (h16_colon? h16_colon? h16 )? COLON COLON h16_colon h16_colon ls32
        private fun IPv6address_4(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_4")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_4_0(b, l + 1)
            r = r && consumeTokens(b, 0, COLON, COLON)
            r = r && h16_colon(b, l + 1)
            r = r && h16_colon(b, l + 1)
            r = r && ls32(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (h16_colon? h16_colon? h16 )?
        private fun IPv6address_4_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_4_0")) return false
            IPv6address_4_0_0(b, l + 1)
            return true
        }

        // h16_colon? h16_colon? h16
        private fun IPv6address_4_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_4_0_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_4_0_0_0(b, l + 1)
            r = r && IPv6address_4_0_0_1(b, l + 1)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16_colon?
        private fun IPv6address_4_0_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_4_0_0_0")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_4_0_0_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_4_0_0_1")) return false
            h16_colon(b, l + 1)
            return true
        }

        // (h16_colon? h16_colon? h16_colon? h16 )? COLON COLON h16_colon ls32
        private fun IPv6address_5(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_5")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_5_0(b, l + 1)
            r = r && consumeTokens(b, 0, COLON, COLON)
            r = r && h16_colon(b, l + 1)
            r = r && ls32(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (h16_colon? h16_colon? h16_colon? h16 )?
        private fun IPv6address_5_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_5_0")) return false
            IPv6address_5_0_0(b, l + 1)
            return true
        }

        // h16_colon? h16_colon? h16_colon? h16
        private fun IPv6address_5_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_5_0_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_5_0_0_0(b, l + 1)
            r = r && IPv6address_5_0_0_1(b, l + 1)
            r = r && IPv6address_5_0_0_2(b, l + 1)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16_colon?
        private fun IPv6address_5_0_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_5_0_0_0")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_5_0_0_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_5_0_0_1")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_5_0_0_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_5_0_0_2")) return false
            h16_colon(b, l + 1)
            return true
        }

        // (h16_colon? h16_colon? h16_colon? h16_colon? h16 )? COLON COLON ls32
        private fun IPv6address_6(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_6")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_6_0(b, l + 1)
            r = r && consumeTokens(b, 0, COLON, COLON)
            r = r && ls32(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (h16_colon? h16_colon? h16_colon? h16_colon? h16 )?
        private fun IPv6address_6_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_6_0")) return false
            IPv6address_6_0_0(b, l + 1)
            return true
        }

        // h16_colon? h16_colon? h16_colon? h16_colon? h16
        private fun IPv6address_6_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_6_0_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_6_0_0_0(b, l + 1)
            r = r && IPv6address_6_0_0_1(b, l + 1)
            r = r && IPv6address_6_0_0_2(b, l + 1)
            r = r && IPv6address_6_0_0_3(b, l + 1)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16_colon?
        private fun IPv6address_6_0_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_6_0_0_0")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_6_0_0_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_6_0_0_1")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_6_0_0_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_6_0_0_2")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_6_0_0_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_6_0_0_3")) return false
            h16_colon(b, l + 1)
            return true
        }

        // (h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16 )? COLON COLON h16
        private fun IPv6address_7(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_7_0(b, l + 1)
            r = r && consumeTokens(b, 0, COLON, COLON)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16 )?
        private fun IPv6address_7_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7_0")) return false
            IPv6address_7_0_0(b, l + 1)
            return true
        }

        // h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16
        private fun IPv6address_7_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7_0_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_7_0_0_0(b, l + 1)
            r = r && IPv6address_7_0_0_1(b, l + 1)
            r = r && IPv6address_7_0_0_2(b, l + 1)
            r = r && IPv6address_7_0_0_3(b, l + 1)
            r = r && IPv6address_7_0_0_4(b, l + 1)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16_colon?
        private fun IPv6address_7_0_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7_0_0_0")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_7_0_0_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7_0_0_1")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_7_0_0_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7_0_0_2")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_7_0_0_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7_0_0_3")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_7_0_0_4(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_7_0_0_4")) return false
            h16_colon(b, l + 1)
            return true
        }

        // (h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16 )? COLON COLON
        private fun IPv6address_8(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_8_0(b, l + 1)
            r = r && consumeTokens(b, 0, COLON, COLON)
            exit_section_(b, m, null, r)
            return r
        }

        // (h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16 )?
        private fun IPv6address_8_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0")) return false
            IPv6address_8_0_0(b, l + 1)
            return true
        }

        // h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16_colon? h16
        private fun IPv6address_8_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = IPv6address_8_0_0_0(b, l + 1)
            r = r && IPv6address_8_0_0_1(b, l + 1)
            r = r && IPv6address_8_0_0_2(b, l + 1)
            r = r && IPv6address_8_0_0_3(b, l + 1)
            r = r && IPv6address_8_0_0_4(b, l + 1)
            r = r && IPv6address_8_0_0_5(b, l + 1)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // h16_colon?
        private fun IPv6address_8_0_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0_0_0")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_8_0_0_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0_0_1")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_8_0_0_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0_0_2")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_8_0_0_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0_0_3")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_8_0_0_4(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0_0_4")) return false
            h16_colon(b, l + 1)
            return true
        }

        // h16_colon?
        private fun IPv6address_8_0_0_5(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPv6address_8_0_0_5")) return false
            h16_colon(b, l + 1)
            return true
        }

        /* ********************************************************** */
        // 'v' hexdig+ DOT (unreserved | SUB_DELIMS | DOLLAR_SIGN | COLON)+
        fun IPvFuture(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPvFuture")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, I_PV_FUTURE, "<i pv future>")
            r = consumeToken(b, "v")
            r = r && IPvFuture_1(b, l + 1)
            r = r && consumeToken(b, DOT)
            r = r && IPvFuture_3(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // hexdig+
        private fun IPvFuture_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPvFuture_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = hexdig(b, l + 1)
            while (r) {
                val c = current_position_(b)
                if (!hexdig(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "IPvFuture_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        // (unreserved | SUB_DELIMS | DOLLAR_SIGN | COLON)+
        private fun IPvFuture_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPvFuture_3")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = IPvFuture_3_0(b, l + 1)
            while (r) {
                val c = current_position_(b)
                if (!IPvFuture_3_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "IPvFuture_3", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        // unreserved | SUB_DELIMS | DOLLAR_SIGN | COLON
        private fun IPvFuture_3_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "IPvFuture_3_0")) return false
            var r: Boolean
            r = unreserved(b, l + 1)
            if (!r) r = consumeToken(b, SUB_DELIMS)
            if (!r) r = consumeToken(b, DOLLAR_SIGN)
            if (!r) r = consumeToken(b, COLON)
            return r
        }

        /* ********************************************************** */
        // (userinfo AT)? host (COLON port)?
        fun authority(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "authority")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, AUTHORITY, "<authority>")
            r = authority_0(b, l + 1)
            r = r && host(b, l + 1)
            r = r && authority_2(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // (userinfo AT)?
        private fun authority_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "authority_0")) return false
            authority_0_0(b, l + 1)
            return true
        }

        // userinfo AT
        private fun authority_0_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "authority_0_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = userinfo(b, l + 1)
            r = r && consumeToken(b, AT)
            exit_section_(b, m, null, r)
            return r
        }

        // (COLON port)?
        private fun authority_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "authority_2")) return false
            authority_2_0(b, l + 1)
            return true
        }

        // COLON port
        private fun authority_2_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "authority_2_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, COLON)
            r = r && port(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // extras_list | BINARY_ALL | BINARY_NONE
        fun binary_list(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "binary_list")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, BINARY_LIST, "<binary list>")
            r = extras_list(b, l + 1)
            if (!r) r = consumeToken(b, BINARY_ALL)
            if (!r) r = consumeToken(b, BINARY_NONE)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // CONSTRAINT WHITE_SPACE+ uri_reference
        private fun constraint_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "constraint_req")) return false
            if (!nextTokenIs(b, CONSTRAINT)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, CONSTRAINT_REQ, null)
            r = consumeToken(b, CONSTRAINT)
            p = r // pin = 1
            r = r && report_error_(b, constraint_req_1(b, l + 1))
            r = p && uri_reference(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun constraint_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "constraint_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "constraint_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // DIGIT // 0-9
        //                   | nz DIGIT // 10-99
        //                   | "1" DIGIT DIGIT // 100-199
        //                   | "2" ("0" | "1" | "2" | "3" | "4") DIGIT // 200-249
        //                   | "2" "5" ("0" | "1" | "2" | "3" | "4" | "5")
        private fun dec_octet(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "dec_octet")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, DEC_OCTET, "<dec octet>")
            r = consumeToken(b, DIGIT)
            if (!r) r = dec_octet_1(b, l + 1)
            if (!r) r = dec_octet_2(b, l + 1)
            if (!r) r = dec_octet_3(b, l + 1)
            if (!r) r = dec_octet_4(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // nz DIGIT
        private fun dec_octet_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "dec_octet_1")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = nz(b, l + 1)
            r = r && consumeToken(b, DIGIT)
            exit_section_(b, m, null, r)
            return r
        }

        // "1" DIGIT DIGIT
        private fun dec_octet_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "dec_octet_2")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, "1")
            r = r && consumeTokens(b, 0, DIGIT, DIGIT)
            exit_section_(b, m, null, r)
            return r
        }

        // "2" ("0" | "1" | "2" | "3" | "4") DIGIT
        private fun dec_octet_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "dec_octet_3")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, "2")
            r = r && dec_octet_3_1(b, l + 1)
            r = r && consumeToken(b, DIGIT)
            exit_section_(b, m, null, r)
            return r
        }

        // "0" | "1" | "2" | "3" | "4"
        private fun dec_octet_3_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "dec_octet_3_1")) return false
            var r: Boolean
            r = consumeToken(b, "0")
            if (!r) r = consumeToken(b, "1")
            if (!r) r = consumeToken(b, "2")
            if (!r) r = consumeToken(b, "3")
            if (!r) r = consumeToken(b, "4")
            return r
        }

        // "2" "5" ("0" | "1" | "2" | "3" | "4" | "5")
        private fun dec_octet_4(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "dec_octet_4")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, "2")
            r = r && consumeToken(b, "5")
            r = r && dec_octet_4_2(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // "0" | "1" | "2" | "3" | "4" | "5"
        private fun dec_octet_4_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "dec_octet_4_2")) return false
            var r: Boolean
            r = consumeToken(b, "0")
            if (!r) r = consumeToken(b, "1")
            if (!r) r = consumeToken(b, "2")
            if (!r) r = consumeToken(b, "3")
            if (!r) r = consumeToken(b, "4")
            if (!r) r = consumeToken(b, "5")
            return r
        }

        /* ********************************************************** */
        // EDITABLE WHITE_SPACE+ uri_reference
        private fun editable_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "editable_req")) return false
            if (!nextTokenIs(b, EDITABLE)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, EDITABLE_REQ, null)
            r = consumeToken(b, EDITABLE)
            p = r // pin = 1
            r = r && report_error_(b, editable_req_1(b, l + 1))
            r = p && uri_reference(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun editable_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "editable_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "editable_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // DOLLAR_SIGN LBRACE variable_name RBRACE
        private fun env_variable(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "env_variable")) return false
            if (!nextTokenIs(b, DOLLAR_SIGN)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeTokens(b, 0, DOLLAR_SIGN, LBRACE)
            r = r && variable_name(b, l + 1)
            r = r && consumeToken(b, RBRACE)
            exit_section_(b, m, ENV_VARIABLE, r)
            return r
        }

        /* ********************************************************** */
        // EXTRA_INDEX_URL WHITE_SPACE+ uri_reference
        private fun extra_index_url_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extra_index_url_req")) return false
            if (!nextTokenIs(b, EXTRA_INDEX_URL)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, EXTRA_INDEX_URL_REQ, null)
            r = consumeToken(b, EXTRA_INDEX_URL)
            p = r // pin = 1
            r = r && report_error_(b, extra_index_url_req_1(b, l + 1))
            r = p && uri_reference(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun extra_index_url_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extra_index_url_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "extra_index_url_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // LSBRACE wsps extras_list? wsps RSBRACE
        fun extras(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extras")) return false
            if (!nextTokenIs(b, LSBRACE)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, LSBRACE)
            r = r && wsps(b, l + 1)
            r = r && extras_2(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && consumeToken(b, RSBRACE)
            exit_section_(b, m, EXTRAS, r)
            return r
        }

        // extras_list?
        private fun extras_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extras_2")) return false
            extras_list(b, l + 1)
            return true
        }

        /* ********************************************************** */
        // IDENTIFIER (wsps COMMA wsps IDENTIFIER)*
        private fun extras_list(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extras_list")) return false
            if (!nextTokenIs(b, IDENTIFIER)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, IDENTIFIER)
            r = r && extras_list_1(b, l + 1)
            exit_section_(b, m, EXTRAS_LIST, r)
            return r
        }

        // (wsps COMMA wsps IDENTIFIER)*
        private fun extras_list_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extras_list_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!extras_list_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "extras_list_1", c)) break
            }
            return true
        }

        // wsps COMMA wsps IDENTIFIER
        private fun extras_list_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "extras_list_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = wsps(b, l + 1)
            r = r && consumeToken(b, COMMA)
            r = r && wsps(b, l + 1)
            r = r && consumeToken(b, IDENTIFIER)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // FIND_LINKS WHITE_SPACE+ uri_reference
        private fun find_links_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "find_links_req")) return false
            if (!nextTokenIs(b, FIND_LINKS)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, FIND_LINKS_REQ, null)
            r = consumeToken(b, FIND_LINKS)
            p = r // pin = 1
            r = r && report_error_(b, find_links_req_1(b, l + 1))
            r = p && uri_reference(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun find_links_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "find_links_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "find_links_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // (pchar | SLASH | QUESTION_MARK)*
        fun fragment(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "fragment")) return false
            val m = enter_section_(b, l, _NONE_, FRAGMENT, "<fragment>")
            while (true) {
                val c = current_position_(b)
                if (!fragment_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "fragment", c)) break
            }
            exit_section_(b, l, m, true, false, null)
            return true
        }

        // pchar | SLASH | QUESTION_MARK
        private fun fragment_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "fragment_0")) return false
            var r: Boolean
            r = pchar(b, l + 1)
            if (!r) r = consumeToken(b, SLASH)
            if (!r) r = consumeToken(b, QUESTION_MARK)
            return r
        }

        /* ********************************************************** */
        // hexdig hexdig? hexdig? hexdig?
        fun h16(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "h16")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, H_16, "<h 16>")
            r = hexdig(b, l + 1)
            r = r && h16_1(b, l + 1)
            r = r && h16_2(b, l + 1)
            r = r && h16_3(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // hexdig?
        private fun h16_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "h16_1")) return false
            hexdig(b, l + 1)
            return true
        }

        // hexdig?
        private fun h16_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "h16_2")) return false
            hexdig(b, l + 1)
            return true
        }

        // hexdig?
        private fun h16_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "h16_3")) return false
            hexdig(b, l + 1)
            return true
        }

        /* ********************************************************** */
        // h16 COLON
        private fun h16_colon(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "h16_colon")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, H_16_COLON, "<h 16 colon>")
            r = h16(b, l + 1)
            r = r && consumeToken(b, COLON)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // HASH EQUAL IDENTIFIER COLON HEX
        private fun hash_option(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "hash_option")) return false
            if (!nextTokenIs(b, HASH)) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeTokens(b, 0, HASH, EQUAL, IDENTIFIER, COLON, HEX)
            exit_section_(b, m, HASH_OPTION, r)
            return r
        }

        /* ********************************************************** */
        // DIGIT | 'a' | 'A' | 'b' | 'B' | 'c' | 'C' | 'd' | 'D' | 'e' | 'E' | 'f' | 'F'
        fun hexdig(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "hexdig")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, HEXDIG, "<hexdig>")
            r = consumeToken(b, DIGIT)
            if (!r) r = consumeToken(b, "a")
            if (!r) r = consumeToken(b, "A")
            if (!r) r = consumeToken(b, "b")
            if (!r) r = consumeToken(b, "B")
            if (!r) r = consumeToken(b, "c")
            if (!r) r = consumeToken(b, "C")
            if (!r) r = consumeToken(b, "d")
            if (!r) r = consumeToken(b, "D")
            if (!r) r = consumeToken(b, "e")
            if (!r) r = consumeToken(b, "E")
            if (!r) r = consumeToken(b, "f")
            if (!r) r = consumeToken(b, "F")
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // SLASH SLASH authority path_abempty | path_absolute | path_rootless | path_empty
        fun hier_part(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "hier_part")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, HIER_PART, "<hier part>")
            r = hier_part_0(b, l + 1)
            if (!r) r = path_absolute(b, l + 1)
            if (!r) r = path_rootless(b, l + 1)
            if (!r) r = path_empty(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // SLASH SLASH authority path_abempty
        private fun hier_part_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "hier_part_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeTokens(b, 0, SLASH, SLASH)
            r = r && authority(b, l + 1)
            r = r && path_abempty(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // IP_literal | IPv4address | reg_name
        fun host(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "host")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, HOST, "<host>")
            r = IP_literal(b, l + 1)
            if (!r) r = IPv4address(b, l + 1)
            if (!r) r = reg_name(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // INDEX_URL WHITE_SPACE+ uri_reference
        private fun index_url_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "index_url_req")) return false
            if (!nextTokenIs(b, INDEX_URL)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, INDEX_URL_REQ, null)
            r = consumeToken(b, INDEX_URL)
            p = r // pin = 1
            r = r && report_error_(b, index_url_req_1(b, l + 1))
            r = p && uri_reference(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun index_url_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "index_url_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "index_url_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // (wsps specification wsps COMMENT? (EOL | <<eof>>)) | EOL
        private fun line_(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "line_")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = line__0(b, l + 1)
            if (!r) r = consumeToken(b, EOL)
            exit_section_(b, m, null, r)
            return r
        }

        // wsps specification wsps COMMENT? (EOL | <<eof>>)
        private fun line__0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "line__0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = wsps(b, l + 1)
            r = r && specification(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && line__0_3(b, l + 1)
            r = r && line__0_4(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // COMMENT?
        private fun line__0_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "line__0_3")) return false
            consumeToken(b, COMMENT)
            return true
        }

        // EOL | <<eof>>
        private fun line__0_4(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "line__0_4")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, EOL)
            if (!r) r = eof(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // h16_colon h16 | IPv4address
        fun ls32(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "ls32")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, LS_32, "<ls 32>")
            r = ls32_0(b, l + 1)
            if (!r) r = IPv4address(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // h16_colon h16
        private fun ls32_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "ls32_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = h16_colon(b, l + 1)
            r = r && h16(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // marker_or
        fun marker(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker")) return false
            val r: Boolean
            val m = enter_section_(b, l, _NONE_, MARKER, "<marker>")
            r = marker_or(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // marker_expr (wsps AND wsps marker_expr)?
        private fun marker_and(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_and")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, MARKER_AND, "<marker and>")
            r = marker_expr(b, l + 1)
            r = r && marker_and_1(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // (wsps AND wsps marker_expr)?
        private fun marker_and_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_and_1")) return false
            marker_and_1_0(b, l + 1)
            return true
        }

        // wsps AND wsps marker_expr
        private fun marker_and_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_and_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = wsps(b, l + 1)
            r = r && consumeToken(b, AND)
            r = r && wsps(b, l + 1)
            r = r && marker_expr(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // marker_var wsps marker_op wsps marker_var
        //                   | LPARENTHESIS wsps marker wsps RPARENTHESIS
        private fun marker_expr(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_expr")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, MARKER_EXPR, "<marker expr>")
            r = marker_expr_0(b, l + 1)
            if (!r) r = marker_expr_1(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // marker_var wsps marker_op wsps marker_var
        private fun marker_expr_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_expr_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = marker_var(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && marker_op(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && marker_var(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // LPARENTHESIS wsps marker wsps RPARENTHESIS
        private fun marker_expr_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_expr_1")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, LPARENTHESIS)
            r = r && wsps(b, l + 1)
            r = r && marker(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && consumeToken(b, RPARENTHESIS)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // VERSION_CMP | IN | NOT WHITE_SPACE+ IN
        private fun marker_op(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_op")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, MARKER_OP, "<marker op>")
            r = consumeToken(b, VERSION_CMP)
            if (!r) r = consumeToken(b, IN)
            if (!r) r = marker_op_2(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // NOT WHITE_SPACE+ IN
        private fun marker_op_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_op_2")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, NOT)
            r = r && marker_op_2_1(b, l + 1)
            r = r && consumeToken(b, IN)
            exit_section_(b, m, null, r)
            return r
        }

        // WHITE_SPACE+
        private fun marker_op_2_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_op_2_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "marker_op_2_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // marker_and (wsps OR wsps marker_and)?
        private fun marker_or(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_or")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, MARKER_OR, "<marker or>")
            r = marker_and(b, l + 1)
            r = r && marker_or_1(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // (wsps OR wsps marker_and)?
        private fun marker_or_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_or_1")) return false
            marker_or_1_0(b, l + 1)
            return true
        }

        // wsps OR wsps marker_and
        private fun marker_or_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_or_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = wsps(b, l + 1)
            r = r && consumeToken(b, OR)
            r = r && wsps(b, l + 1)
            r = r && marker_and(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // ENV_VAR | python_str
        private fun marker_var(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "marker_var")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, MARKER_VAR, "<marker var>")
            r = consumeToken(b, ENV_VAR)
            if (!r) r = python_str(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // IDENTIFIER
        fun name(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name")) return false
            if (!nextTokenIs(b, IDENTIFIER)) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, IDENTIFIER)
            exit_section_(b, m, NAME, r)
            return r
        }

        /* ********************************************************** */
        // name wsps extras? wsps !AT wsps versionspec? wsps
        //                    (LONG_OPTION hash_option wsps)* quoted_marker?
        fun name_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name_req")) return false
            if (!nextTokenIs(b, IDENTIFIER)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, NAME_REQ, null)
            r = name(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && name_req_2(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && name_req_4(b, l + 1)
            p = r // pin = 5
            r = r && report_error_(b, wsps(b, l + 1))
            r = p && report_error_(b, name_req_6(b, l + 1)) && r
            r = p && report_error_(b, wsps(b, l + 1)) && r
            r = p && report_error_(b, name_req_8(b, l + 1)) && r
            r = p && name_req_9(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // extras?
        private fun name_req_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name_req_2")) return false
            extras(b, l + 1)
            return true
        }

        // !AT
        private fun name_req_4(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name_req_4")) return false
            val r: Boolean
            val m = enter_section_(b, l, _NOT_)
            r = !consumeToken(b, AT)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // versionspec?
        private fun name_req_6(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name_req_6")) return false
            versionspec(b, l + 1)
            return true
        }

        // (LONG_OPTION hash_option wsps)*
        private fun name_req_8(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name_req_8")) return false
            while (true) {
                val c = current_position_(b)
                if (!name_req_8_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "name_req_8", c)) break
            }
            return true
        }

        // LONG_OPTION hash_option wsps
        private fun name_req_8_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name_req_8_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, LONG_OPTION)
            r = r && hash_option(b, l + 1)
            r = r && wsps(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // quoted_marker?
        private fun name_req_9(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "name_req_9")) return false
            quoted_marker(b, l + 1)
            return true
        }

        /* ********************************************************** */
        // NO_BINARY WHITE_SPACE+ binary_list
        private fun no_binary_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "no_binary_req")) return false
            if (!nextTokenIs(b, NO_BINARY)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, NO_BINARY_REQ, null)
            r = consumeToken(b, NO_BINARY)
            p = r // pin = 1
            r = r && report_error_(b, no_binary_req_1(b, l + 1))
            r = p && binary_list(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun no_binary_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "no_binary_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "no_binary_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // NO_INDEX
        private fun no_index_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "no_index_req")) return false
            if (!nextTokenIs(b, NO_INDEX)) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, NO_INDEX)
            exit_section_(b, m, NO_INDEX_REQ, r)
            return r
        }

        /* ********************************************************** */
        // !"0" DIGIT
        fun nz(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "nz")) return false
            if (!nextTokenIs(b, DIGIT)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = nz_0(b, l + 1)
            r = r && consumeToken(b, DIGIT)
            exit_section_(b, m, NZ, r)
            return r
        }

        // !"0"
        private fun nz_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "nz_0")) return false
            val r: Boolean
            val m = enter_section_(b, l, _NOT_)
            r = !consumeToken(b, "0")
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // ONLY_BINARY WHITE_SPACE+ binary_list
        private fun only_binary_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "only_binary_req")) return false
            if (!nextTokenIs(b, ONLY_BINARY)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, ONLY_BINARY_REQ, null)
            r = consumeToken(b, ONLY_BINARY)
            p = r // pin = 1
            r = r && report_error_(b, only_binary_req_1(b, l + 1))
            r = p && binary_list(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun only_binary_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "only_binary_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "only_binary_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // (SHORT_OPTION | LONG_OPTION) (
        //                    refer_req
        //                    | constraint_req
        //                    | editable_req
        //                    | index_url_req
        //                    | extra_index_url_req
        //                    | find_links_req
        //                    | no_index_req
        //                    | require_hashes_req
        //                    | no_binary_req
        //                    | only_binary_req
        //                    | trusted_host_req)
        fun option(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "option")) return false
            if (!nextTokenIs(b, "<option>", LONG_OPTION, SHORT_OPTION)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, OPTION, "<option>")
            r = option_0(b, l + 1)
            p = r // pin = 1
            r = r && option_1(b, l + 1)
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // SHORT_OPTION | LONG_OPTION
        private fun option_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "option_0")) return false
            var r: Boolean
            r = consumeToken(b, SHORT_OPTION)
            if (!r) r = consumeToken(b, LONG_OPTION)
            return r
        }

        // refer_req
        //                    | constraint_req
        //                    | editable_req
        //                    | index_url_req
        //                    | extra_index_url_req
        //                    | find_links_req
        //                    | no_index_req
        //                    | require_hashes_req
        //                    | no_binary_req
        //                    | only_binary_req
        //                    | trusted_host_req
        private fun option_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "option_1")) return false
            var r: Boolean
            r = refer_req(b, l + 1)
            if (!r) r = constraint_req(b, l + 1)
            if (!r) r = editable_req(b, l + 1)
            if (!r) r = index_url_req(b, l + 1)
            if (!r) r = extra_index_url_req(b, l + 1)
            if (!r) r = find_links_req(b, l + 1)
            if (!r) r = no_index_req(b, l + 1)
            if (!r) r = require_hashes_req(b, l + 1)
            if (!r) r = no_binary_req(b, l + 1)
            if (!r) r = only_binary_req(b, l + 1)
            if (!r) r = trusted_host_req(b, l + 1)
            return r
        }

        /* ********************************************************** */
        // (SLASH segment)*
        private fun path_abempty(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_abempty")) return false
            val m = enter_section_(b, l, _NONE_, PATH_ABEMPTY, "<path abempty>")
            while (true) {
                val c = current_position_(b)
                if (!path_abempty_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "path_abempty", c)) break
            }
            exit_section_(b, l, m, true, false, null)
            return true
        }

        // SLASH segment
        private fun path_abempty_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_abempty_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SLASH)
            r = r && segment(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // SLASH (segment_nz (SLASH segment)*)?
        private fun path_absolute(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_absolute")) return false
            if (!nextTokenIs(b, SLASH)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SLASH)
            r = r && path_absolute_1(b, l + 1)
            exit_section_(b, m, PATH_ABSOLUTE, r)
            return r
        }

        // (segment_nz (SLASH segment)*)?
        private fun path_absolute_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_absolute_1")) return false
            path_absolute_1_0(b, l + 1)
            return true
        }

        // segment_nz (SLASH segment)*
        private fun path_absolute_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_absolute_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = segment_nz(b, l + 1)
            r = r && path_absolute_1_0_1(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (SLASH segment)*
        private fun path_absolute_1_0_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_absolute_1_0_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!path_absolute_1_0_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "path_absolute_1_0_1", c)) break
            }
            return true
        }

        // SLASH segment
        private fun path_absolute_1_0_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_absolute_1_0_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SLASH)
            r = r && segment(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // pchar{0}
        private fun path_empty(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_empty")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, PATH_EMPTY, "<path empty>")
            r = pchar(b, l + 1)
            r = r && path_empty_1(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // {0}
        private fun path_empty_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_empty_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, "0")
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // segment_nz_nc (SLASH segment)*
        private fun path_noscheme(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_noscheme")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, PATH_NOSCHEME, "<path noscheme>")
            r = segment_nz_nc(b, l + 1)
            r = r && path_noscheme_1(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // (SLASH segment)*
        private fun path_noscheme_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_noscheme_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!path_noscheme_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "path_noscheme_1", c)) break
            }
            return true
        }

        // SLASH segment
        private fun path_noscheme_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_noscheme_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SLASH)
            r = r && segment(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // uri_reference wsps quoted_marker?
        private fun path_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_req")) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, PATH_REQ, "<path req>")
            r = uri_reference(b, l + 1)
            p = r // pin = 1
            r = r && report_error_(b, wsps(b, l + 1))
            r = p && path_req_2(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // quoted_marker?
        private fun path_req_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_req_2")) return false
            quoted_marker(b, l + 1)
            return true
        }

        /* ********************************************************** */
        // segment_nz (SLASH segment)*
        private fun path_rootless(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_rootless")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, PATH_ROOTLESS, "<path rootless>")
            r = segment_nz(b, l + 1)
            r = r && path_rootless_1(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // (SLASH segment)*
        private fun path_rootless_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_rootless_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!path_rootless_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "path_rootless_1", c)) break
            }
            return true
        }

        // SLASH segment
        private fun path_rootless_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "path_rootless_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SLASH)
            r = r && segment(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // unreserved | pct_encoded | SUB_DELIMS | DOLLAR_SIGN | COLON | AT | PLUS
        fun pchar(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "pchar")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, PCHAR, "<pchar>")
            r = unreserved(b, l + 1)
            if (!r) r = pct_encoded(b, l + 1)
            if (!r) r = consumeToken(b, SUB_DELIMS)
            if (!r) r = consumeToken(b, DOLLAR_SIGN)
            if (!r) r = consumeToken(b, COLON)
            if (!r) r = consumeToken(b, AT)
            if (!r) r = consumeToken(b, PLUS)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // PERCENT_SIGN hexdig
        private fun pct_encoded(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "pct_encoded")) return false
            if (!nextTokenIs(b, PERCENT_SIGN)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, PERCENT_SIGN)
            r = r && hexdig(b, l + 1)
            exit_section_(b, m, PCT_ENCODED, r)
            return r
        }

        /* ********************************************************** */
        // DIGIT*
        fun port(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "port")) return false
            val m = enter_section_(b, l, _NONE_, PORT, "<port>")
            while (true) {
                val c = current_position_(b)
                if (!consumeToken(b, DIGIT)) break
                if (!empty_element_parsed_guard_(b, "port", c)) break
            }
            exit_section_(b, l, m, true, false, null)
            return true
        }

        /* ********************************************************** */
        // SQUOTE (PYTHON_STR_C | DQUOTE)* SQUOTE |
        //                    DQUOTE (PYTHON_STR_C | SQUOTE)* DQUOTE
        private fun python_str(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "python_str")) return false
            if (!nextTokenIs(b, "<python str>", DQUOTE, SQUOTE)) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, PYTHON_STR, "<python str>")
            r = python_str_0(b, l + 1)
            if (!r) r = python_str_1(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // SQUOTE (PYTHON_STR_C | DQUOTE)* SQUOTE
        private fun python_str_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "python_str_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SQUOTE)
            r = r && python_str_0_1(b, l + 1)
            r = r && consumeToken(b, SQUOTE)
            exit_section_(b, m, null, r)
            return r
        }

        // (PYTHON_STR_C | DQUOTE)*
        private fun python_str_0_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "python_str_0_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!python_str_0_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "python_str_0_1", c)) break
            }
            return true
        }

        // PYTHON_STR_C | DQUOTE
        private fun python_str_0_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "python_str_0_1_0")) return false
            var r: Boolean
            r = consumeToken(b, PYTHON_STR_C)
            if (!r) r = consumeToken(b, DQUOTE)
            return r
        }

        // DQUOTE (PYTHON_STR_C | SQUOTE)* DQUOTE
        private fun python_str_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "python_str_1")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, DQUOTE)
            r = r && python_str_1_1(b, l + 1)
            r = r && consumeToken(b, DQUOTE)
            exit_section_(b, m, null, r)
            return r
        }

        // (PYTHON_STR_C | SQUOTE)*
        private fun python_str_1_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "python_str_1_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!python_str_1_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "python_str_1_1", c)) break
            }
            return true
        }

        // PYTHON_STR_C | SQUOTE
        private fun python_str_1_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "python_str_1_1_0")) return false
            var r: Boolean
            r = consumeToken(b, PYTHON_STR_C)
            if (!r) r = consumeToken(b, SQUOTE)
            return r
        }

        /* ********************************************************** */
        // (pchar | SLASH | QUESTION_MARK)*
        fun query(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "query")) return false
            val m = enter_section_(b, l, _NONE_, QUERY, "<query>")
            while (true) {
                val c = current_position_(b)
                if (!query_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "query", c)) break
            }
            exit_section_(b, l, m, true, false, null)
            return true
        }

        // pchar | SLASH | QUESTION_MARK
        private fun query_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "query_0")) return false
            var r: Boolean
            r = pchar(b, l + 1)
            if (!r) r = consumeToken(b, SLASH)
            if (!r) r = consumeToken(b, QUESTION_MARK)
            return r
        }

        /* ********************************************************** */
        // SEMICOLON wsps marker
        private fun quoted_marker(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "quoted_marker")) return false
            if (!nextTokenIs(b, SEMICOLON)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SEMICOLON)
            r = r && wsps(b, l + 1)
            r = r && marker(b, l + 1)
            exit_section_(b, m, QUOTED_MARKER, r)
            return r
        }

        /* ********************************************************** */
        // REFER WHITE_SPACE+ uri_reference
        private fun refer_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "refer_req")) return false
            if (!nextTokenIs(b, REFER)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, REFER_REQ, null)
            r = consumeToken(b, REFER)
            p = r // pin = 1
            r = r && report_error_(b, refer_req_1(b, l + 1))
            r = p && uri_reference(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun refer_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "refer_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "refer_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // (unreserved | pct_encoded | SUB_DELIMS | DOLLAR_SIGN)*
        private fun reg_name(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "reg_name")) return false
            val m = enter_section_(b, l, _NONE_, REG_NAME, "<reg name>")
            while (true) {
                val c = current_position_(b)
                if (!reg_name_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "reg_name", c)) break
            }
            exit_section_(b, l, m, true, false, null)
            return true
        }

        // unreserved | pct_encoded | SUB_DELIMS | DOLLAR_SIGN
        private fun reg_name_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "reg_name_0")) return false
            var r: Boolean
            r = unreserved(b, l + 1)
            if (!r) r = pct_encoded(b, l + 1)
            if (!r) r = consumeToken(b, SUB_DELIMS)
            if (!r) r = consumeToken(b, DOLLAR_SIGN)
            return r
        }

        /* ********************************************************** */
        // SLASH SLASH authority path_abempty | path_absolute | path_noscheme | path_empty
        private fun relative_part(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "relative_part")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, RELATIVE_PART, "<relative part>")
            r = relative_part_0(b, l + 1)
            if (!r) r = path_absolute(b, l + 1)
            if (!r) r = path_noscheme(b, l + 1)
            if (!r) r = path_empty(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // SLASH SLASH authority path_abempty
        private fun relative_part_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "relative_part_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeTokens(b, 0, SLASH, SLASH)
            r = r && authority(b, l + 1)
            r = r && path_abempty(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // relative_part (QUESTION_MARK query)? (SHARP fragment)?
        private fun relative_ref(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "relative_ref")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, RELATIVE_REF, "<relative ref>")
            r = relative_part(b, l + 1)
            r = r && relative_ref_1(b, l + 1)
            r = r && relative_ref_2(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // (QUESTION_MARK query)?
        private fun relative_ref_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "relative_ref_1")) return false
            relative_ref_1_0(b, l + 1)
            return true
        }

        // QUESTION_MARK query
        private fun relative_ref_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "relative_ref_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, QUESTION_MARK)
            r = r && query(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (SHARP fragment)?
        private fun relative_ref_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "relative_ref_2")) return false
            relative_ref_2_0(b, l + 1)
            return true
        }

        // SHARP fragment
        private fun relative_ref_2_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "relative_ref_2_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SHARP)
            r = r && fragment(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // REQUIRE_HASHES
        private fun require_hashes_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "require_hashes_req")) return false
            if (!nextTokenIs(b, REQUIRE_HASHES)) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, REQUIRE_HASHES)
            exit_section_(b, m, REQUIRE_HASHES_REQ, r)
            return r
        }

        /* ********************************************************** */
        // line_*
        private fun requirementsFile(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "requirementsFile")) return false
            while (true) {
                val c = current_position_(b)
                if (!line_(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "requirementsFile", c)) break
            }
            return true
        }

        /* ********************************************************** */
        // LETTER (LETTER | DIGIT | PLUS | MINUS | DOT)*
        fun scheme(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "scheme")) return false
            if (!nextTokenIs(b, LETTER)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, LETTER)
            r = r && scheme_1(b, l + 1)
            exit_section_(b, m, SCHEME, r)
            return r
        }

        // (LETTER | DIGIT | PLUS | MINUS | DOT)*
        private fun scheme_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "scheme_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!scheme_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "scheme_1", c)) break
            }
            return true
        }

        // LETTER | DIGIT | PLUS | MINUS | DOT
        private fun scheme_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "scheme_1_0")) return false
            var r: Boolean
            r = consumeToken(b, LETTER)
            if (!r) r = consumeToken(b, DIGIT)
            if (!r) r = consumeToken(b, PLUS)
            if (!r) r = consumeToken(b, MINUS)
            if (!r) r = consumeToken(b, DOT)
            return r
        }

        /* ********************************************************** */
        // pchar*
        fun segment(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "segment")) return false
            val m = enter_section_(b, l, _NONE_, SEGMENT, "<segment>")
            while (true) {
                val c = current_position_(b)
                if (!pchar(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "segment", c)) break
            }
            exit_section_(b, l, m, true, false, null)
            return true
        }

        /* ********************************************************** */
        // pchar+
        private fun segment_nz(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "segment_nz")) return false
            val r: Boolean
            val m = enter_section_(b, l, _NONE_, SEGMENT_NZ, "<segment nz>")
            r = pchar(b, l + 1)
            while (r) {
                val c = current_position_(b)
                if (!pchar(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "segment_nz", c)) break
            }
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // (unreserved | pct_encoded | SUB_DELIMS | DOLLAR_SIGN | AT)+
        private fun segment_nz_nc(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "segment_nz_nc")) return false
            val r: Boolean
            val m = enter_section_(b, l, _NONE_, SEGMENT_NZ_NC, "<segment nz nc>")
            r = segment_nz_nc_0(b, l + 1)
            while (r) {
                val c = current_position_(b)
                if (!segment_nz_nc_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "segment_nz_nc", c)) break
            }
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // unreserved | pct_encoded | SUB_DELIMS | DOLLAR_SIGN | AT
        private fun segment_nz_nc_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "segment_nz_nc_0")) return false
            var r: Boolean
            r = unreserved(b, l + 1)
            if (!r) r = pct_encoded(b, l + 1)
            if (!r) r = consumeToken(b, SUB_DELIMS)
            if (!r) r = consumeToken(b, DOLLAR_SIGN)
            if (!r) r = consumeToken(b, AT)
            return r
        }

        /* ********************************************************** */
        // name_req
        //                           | option
        //                           | path_req
        //                           | url_req
        private fun specification(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "specification")) return false
            var r: Boolean
            r = name_req(b, l + 1)
            if (!r) r = option(b, l + 1)
            if (!r) r = path_req(b, l + 1)
            if (!r) r = url_req(b, l + 1)
            return r
        }

        /* ********************************************************** */
        // TRUSTED_HOST WHITE_SPACE+ host (COLON port)?
        private fun trusted_host_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "trusted_host_req")) return false
            if (!nextTokenIs(b, TRUSTED_HOST)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, TRUSTED_HOST_REQ, null)
            r = consumeToken(b, TRUSTED_HOST)
            p = r // pin = 1
            r = r && report_error_(b, trusted_host_req_1(b, l + 1))
            r = p && report_error_(b, host(b, l + 1)) && r
            r = p && trusted_host_req_3(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // WHITE_SPACE+
        private fun trusted_host_req_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "trusted_host_req_1")) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, WHITE_SPACE)
            while (r) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "trusted_host_req_1", c)) break
            }
            exit_section_(b, m, null, r)
            return r
        }

        // (COLON port)?
        private fun trusted_host_req_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "trusted_host_req_3")) return false
            trusted_host_req_3_0(b, l + 1)
            return true
        }

        // COLON port
        private fun trusted_host_req_3_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "trusted_host_req_3_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, COLON)
            r = r && port(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // LETTER | DIGIT | MINUS | DOT | UNDERSCORE | TILDA
        fun unreserved(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "unreserved")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, UNRESERVED, "<unreserved>")
            r = consumeToken(b, LETTER)
            if (!r) r = consumeToken(b, DIGIT)
            if (!r) r = consumeToken(b, MINUS)
            if (!r) r = consumeToken(b, DOT)
            if (!r) r = consumeToken(b, UNDERSCORE)
            if (!r) r = consumeToken(b, TILDA)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // scheme (AT | COLON) hier_part (QUESTION_MARK query)? (SHARP fragment)?
        fun uri(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "uri")) return false
            if (!nextTokenIs(b, LETTER)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, URI, null)
            r = scheme(b, l + 1)
            r = r && uri_1(b, l + 1)
            p = r // pin = 2
            r = r && report_error_(b, hier_part(b, l + 1))
            r = p && report_error_(b, uri_3(b, l + 1)) && r
            r = p && uri_4(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // AT | COLON
        private fun uri_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "uri_1")) return false
            var r: Boolean
            r = consumeToken(b, AT)
            if (!r) r = consumeToken(b, COLON)
            return r
        }

        // (QUESTION_MARK query)?
        private fun uri_3(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "uri_3")) return false
            uri_3_0(b, l + 1)
            return true
        }

        // QUESTION_MARK query
        private fun uri_3_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "uri_3_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, QUESTION_MARK)
            r = r && query(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        // (SHARP fragment)?
        private fun uri_4(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "uri_4")) return false
            uri_4_0(b, l + 1)
            return true
        }

        // SHARP fragment
        private fun uri_4_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "uri_4_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, SHARP)
            r = r && fragment(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // uri | relative_ref
        private fun uri_reference(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "uri_reference")) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, URI_REFERENCE, "<uri reference>")
            r = uri(b, l + 1)
            if (!r) r = relative_ref(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        /* ********************************************************** */
        // name wsps extras? wsps urlspec wsps quoted_marker?
        private fun url_req(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "url_req")) return false
            if (!nextTokenIs(b, IDENTIFIER)) return false
            var r: Boolean
            val p: Boolean
            val m = enter_section_(b, l, _NONE_, URL_REQ, null)
            r = name(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && url_req_2(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && urlspec(b, l + 1)
            p = r // pin = 5
            r = r && report_error_(b, wsps(b, l + 1))
            r = p && url_req_6(b, l + 1) && r
            exit_section_(b, l, m, r, p, null)
            return r || p
        }

        // extras?
        private fun url_req_2(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "url_req_2")) return false
            extras(b, l + 1)
            return true
        }

        // quoted_marker?
        private fun url_req_6(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "url_req_6")) return false
            quoted_marker(b, l + 1)
            return true
        }

        /* ********************************************************** */
        // AT wsps uri_reference
        fun urlspec(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "urlspec")) return false
            if (!nextTokenIs(b, AT)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, AT)
            r = r && wsps(b, l + 1)
            r = r && uri_reference(b, l + 1)
            exit_section_(b, m, URLSPEC, r)
            return r
        }

        /* ********************************************************** */
        // (unreserved | pct_encoded | env_variable | SUB_DELIMS | DOLLAR_SIGN | COLON)*
        fun userinfo(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "userinfo")) return false
            val m = enter_section_(b, l, _NONE_, USERINFO, "<userinfo>")
            while (true) {
                val c = current_position_(b)
                if (!userinfo_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "userinfo", c)) break
            }
            exit_section_(b, l, m, true, false, null)
            return true
        }

        // unreserved | pct_encoded | env_variable | SUB_DELIMS | DOLLAR_SIGN | COLON
        private fun userinfo_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "userinfo_0")) return false
            var r: Boolean
            r = unreserved(b, l + 1)
            if (!r) r = pct_encoded(b, l + 1)
            if (!r) r = env_variable(b, l + 1)
            if (!r) r = consumeToken(b, SUB_DELIMS)
            if (!r) r = consumeToken(b, DOLLAR_SIGN)
            if (!r) r = consumeToken(b, COLON)
            return r
        }

        /* ********************************************************** */
        // LETTER (LETTER | DIGIT | UNDERSCORE)*
        private fun variable_name(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "variable_name")) return false
            if (!nextTokenIs(b, LETTER)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, LETTER)
            r = r && variable_name_1(b, l + 1)
            exit_section_(b, m, VARIABLE_NAME, r)
            return r
        }

        // (LETTER | DIGIT | UNDERSCORE)*
        private fun variable_name_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "variable_name_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!variable_name_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "variable_name_1", c)) break
            }
            return true
        }

        // LETTER | DIGIT | UNDERSCORE
        private fun variable_name_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "variable_name_1_0")) return false
            var r: Boolean
            r = consumeToken(b, LETTER)
            if (!r) r = consumeToken(b, DIGIT)
            if (!r) r = consumeToken(b, UNDERSCORE)
            return r
        }

        /* ********************************************************** */
        // VERSION_CMP
        private fun version_cmp_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "version_cmp_stmt")) return false
            if (!nextTokenIs(b, VERSION_CMP)) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, VERSION_CMP)
            exit_section_(b, m, VERSION_CMP_STMT, r)
            return r
        }

        /* ********************************************************** */
        // version_one (wsps COMMA wsps version_one)*
        private fun version_many(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "version_many")) return false
            if (!nextTokenIs(b, VERSION_CMP)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = version_one(b, l + 1)
            r = r && version_many_1(b, l + 1)
            exit_section_(b, m, VERSION_MANY, r)
            return r
        }

        // (wsps COMMA wsps version_one)*
        private fun version_many_1(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "version_many_1")) return false
            while (true) {
                val c = current_position_(b)
                if (!version_many_1_0(b, l + 1)) break
                if (!empty_element_parsed_guard_(b, "version_many_1", c)) break
            }
            return true
        }

        // wsps COMMA wsps version_one
        private fun version_many_1_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "version_many_1_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = wsps(b, l + 1)
            r = r && consumeToken(b, COMMA)
            r = r && wsps(b, l + 1)
            r = r && version_one(b, l + 1)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // version_cmp_stmt wsps version_stmt
        private fun version_one(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "version_one")) return false
            if (!nextTokenIs(b, VERSION_CMP)) return false
            var r: Boolean
            val m = enter_section_(b)
            r = version_cmp_stmt(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && version_stmt(b, l + 1)
            exit_section_(b, m, VERSION_ONE, r)
            return r
        }

        /* ********************************************************** */
        // VERSION
        private fun version_stmt(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "version_stmt")) return false
            if (!nextTokenIs(b, VERSION)) return false
            val r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, VERSION)
            exit_section_(b, m, VERSION_STMT, r)
            return r
        }

        /* ********************************************************** */
        // LPARENTHESIS wsps version_many wsps RPARENTHESIS | version_many
        fun versionspec(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "versionspec")) return false
            if (!nextTokenIs(b, "<versionspec>", LPARENTHESIS, VERSION_CMP)) return false
            var r: Boolean
            val m = enter_section_(b, l, _NONE_, VERSIONSPEC, "<versionspec>")
            r = versionspec_0(b, l + 1)
            if (!r) r = version_many(b, l + 1)
            exit_section_(b, l, m, r, false, null)
            return r
        }

        // LPARENTHESIS wsps version_many wsps RPARENTHESIS
        private fun versionspec_0(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "versionspec_0")) return false
            var r: Boolean
            val m = enter_section_(b)
            r = consumeToken(b, LPARENTHESIS)
            r = r && wsps(b, l + 1)
            r = r && version_many(b, l + 1)
            r = r && wsps(b, l + 1)
            r = r && consumeToken(b, RPARENTHESIS)
            exit_section_(b, m, null, r)
            return r
        }

        /* ********************************************************** */
        // WHITE_SPACE*
        private fun wsps(b: PsiBuilder, l: Int): Boolean {
            if (!recursion_guard_(b, l, "wsps")) return false
            while (true) {
                val c = current_position_(b)
                if (!consumeToken(b, WHITE_SPACE)) break
                if (!empty_element_parsed_guard_(b, "wsps", c)) break
            }
            return true
        }
    }
}
