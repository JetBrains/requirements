package ru.meanmail.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import ru.meanmail.psi.impl.*

interface Types {
    object Factory {
        fun createElement(node: ASTNode): PsiElement {
            when (val type = node.elementType) {
                AUTHORITY -> return AuthorityImpl(node)
                CONSTRAINT_REQ -> return ConstraintReqImpl(node)
                DEC_OCTET -> return DecOctetImpl(node)
                EDITABLE_REQ -> return EditableReqImpl(node)
                ENV_VARIABLE -> return EnvVariableImpl(node)
                EXTRAS -> return ExtrasImpl(node)
                EXTRAS_LIST -> return ExtrasListImpl(node)
                EXTRA_INDEX_URL_REQ -> return ExtraIndexUrlReqImpl(node)
                FIND_LINKS_REQ -> return FindLinksReqImpl(node)
                FRAGMENT -> return FragmentImpl(node)
                HEXDIG -> return HexdigImpl(node)
                HIER_PART -> return HierPartImpl(node)
                HOST -> return HostImpl(node)
                H_16 -> return H16Impl(node)
                H_16_COLON -> return H16ColonImpl(node)
                INDEX_URL_REQ -> return IndexUrlReqImpl(node)
                IP_LITERAL -> return IPLiteralImpl(node)
                I_PV_4_ADDRESS -> return IPv4AddressImpl(node)
                I_PV_6_ADDRESS -> return IPv6AddressImpl(node)
                I_PV_FUTURE -> return IPvFutureImpl(node)
                LS_32 -> return Ls32Impl(node)
                MARKER -> return MarkerImpl(node)
                MARKER_AND -> return MarkerAndImpl(node)
                MARKER_EXPR -> return MarkerExprImpl(node)
                MARKER_OP -> return MarkerOpImpl(node)
                MARKER_OR -> return MarkerOrImpl(node)
                MARKER_VAR -> return MarkerVarImpl(node)
                NAME -> return NameImpl(node)
                NAME_REQ -> return NameReqImpl(node)
                NO_BINARY_REQ -> return NoBinaryReqImpl(node)
                NO_INDEX_REQ -> return NoIndexReqImpl(node)
                NZ -> return NzImpl(node)
                ONLY_BINARY_REQ -> return OnlyBinaryReqImpl(node)
                OPTION -> return OptionImpl(node)
                PATH_ABEMPTY -> return PathAbemptyImpl(node)
                PATH_ABSOLUTE -> return PathAbsoluteImpl(node)
                PATH_EMPTY -> return PathEmptyImpl(node)
                PATH_NOSCHEME -> return PathNoschemeImpl(node)
                PATH_REQ -> return PathReqImpl(node)
                PATH_ROOTLESS -> return PathRootlessImpl(node)
                PCHAR -> return PcharImpl(node)
                PCT_ENCODED -> return PctEncodedImpl(node)
                PORT -> return PortImpl(node)
                PYTHON_STR -> return PythonStrImpl(node)
                QUERY -> return QueryImpl(node)
                QUOTED_MARKER -> return QuotedMarkerImpl(node)
                REFER_REQ -> return ReferReqImpl(node)
                REG_NAME -> return RegNameImpl(node)
                RELATIVE_PART -> return RelativePartImpl(node)
                RELATIVE_REF -> return RelativeRefImpl(node)
                REQUIRE_HASHES_REQ -> return RequireHashesReqImpl(node)
                SCHEME -> return SchemeImpl(node)
                SEGMENT -> return SegmentImpl(node)
                SEGMENT_NZ -> return SegmentNzImpl(node)
                SEGMENT_NZ_NC -> return SegmentNzNcImpl(node)
                TRUSTED_HOST_REQ -> return TrustedHostReqImpl(node)
                UNRESERVED -> return UnreservedImpl(node)
                URI -> return UriImpl(node)
                URI_REFERENCE -> return UriReferenceImpl(node)
                URLSPEC -> return UrlspecImpl(node)
                URL_REQ -> return UrlReqImpl(node)
                USERINFO -> return UserinfoImpl(node)
                VARIABLE_NAME -> return VariableNameImpl(node)
                VERSIONSPEC -> return VersionspecImpl(node)
                VERSION_CMP_STMT -> return VersionCmpStmtImpl(node)
                VERSION_MANY -> return VersionManyImpl(node)
                VERSION_ONE -> return VersionOneImpl(node)
                VERSION_STMT -> return VersionStmtImpl(node)
                else -> throw AssertionError("Unknown element type: $type")
            }
        }
    }

    companion object {
        val AUTHORITY: IElementType = RequirementsElementType("AUTHORITY")
        val CONSTRAINT_REQ: IElementType = RequirementsElementType("CONSTRAINT_REQ")
        val DEC_OCTET: IElementType = RequirementsElementType("DEC_OCTET")
        val EDITABLE_REQ: IElementType = RequirementsElementType("EDITABLE_REQ")
        val ENV_VARIABLE: IElementType = RequirementsElementType("ENV_VARIABLE")
        val EXTRAS: IElementType = RequirementsElementType("EXTRAS")
        val EXTRAS_LIST: IElementType = RequirementsElementType("EXTRAS_LIST")
        val EXTRA_INDEX_URL_REQ: IElementType = RequirementsElementType("EXTRA_INDEX_URL_REQ")
        val FIND_LINKS_REQ: IElementType = RequirementsElementType("FIND_LINKS_REQ")
        val FRAGMENT: IElementType = RequirementsElementType("FRAGMENT")
        val HEXDIG: IElementType = RequirementsElementType("HEXDIG")
        val HIER_PART: IElementType = RequirementsElementType("HIER_PART")
        val HOST: IElementType = RequirementsElementType("HOST")
        val H_16: IElementType = RequirementsElementType("H_16")
        val H_16_COLON: IElementType = RequirementsElementType("H_16_COLON")
        val INDEX_URL_REQ: IElementType = RequirementsElementType("INDEX_URL_REQ")
        val IP_LITERAL: IElementType = RequirementsElementType("IP_LITERAL")
        val I_PV_4_ADDRESS: IElementType = RequirementsElementType("I_PV_4_ADDRESS")
        val I_PV_6_ADDRESS: IElementType = RequirementsElementType("I_PV_6_ADDRESS")
        val I_PV_FUTURE: IElementType = RequirementsElementType("I_PV_FUTURE")
        val LS_32: IElementType = RequirementsElementType("LS_32")
        val MARKER: IElementType = RequirementsElementType("MARKER")
        val MARKER_AND: IElementType = RequirementsElementType("MARKER_AND")
        val MARKER_EXPR: IElementType = RequirementsElementType("MARKER_EXPR")
        val MARKER_OP: IElementType = RequirementsElementType("MARKER_OP")
        val MARKER_OR: IElementType = RequirementsElementType("MARKER_OR")
        val MARKER_VAR: IElementType = RequirementsElementType("MARKER_VAR")
        val NAME: IElementType = RequirementsElementType("NAME")
        val NAME_REQ: IElementType = RequirementsElementType("NAME_REQ")
        val NO_BINARY_REQ: IElementType = RequirementsElementType("NO_BINARY_REQ")
        val NO_INDEX_REQ: IElementType = RequirementsElementType("NO_INDEX_REQ")
        val NZ: IElementType = RequirementsElementType("NZ")
        val ONLY_BINARY_REQ: IElementType = RequirementsElementType("ONLY_BINARY_REQ")
        val OPTION: IElementType = RequirementsElementType("OPTION")
        val PATH_ABEMPTY: IElementType = RequirementsElementType("PATH_ABEMPTY")
        val PATH_ABSOLUTE: IElementType = RequirementsElementType("PATH_ABSOLUTE")
        val PATH_EMPTY: IElementType = RequirementsElementType("PATH_EMPTY")
        val PATH_NOSCHEME: IElementType = RequirementsElementType("PATH_NOSCHEME")
        val PATH_REQ: IElementType = RequirementsElementType("PATH_REQ")
        val PATH_ROOTLESS: IElementType = RequirementsElementType("PATH_ROOTLESS")
        val PCHAR: IElementType = RequirementsElementType("PCHAR")
        val PCT_ENCODED: IElementType = RequirementsElementType("PCT_ENCODED")
        val PORT: IElementType = RequirementsElementType("PORT")
        val PYTHON_STR: IElementType = RequirementsElementType("PYTHON_STR")
        val QUERY: IElementType = RequirementsElementType("QUERY")
        val QUOTED_MARKER: IElementType = RequirementsElementType("QUOTED_MARKER")
        val REFER_REQ: IElementType = RequirementsElementType("REFER_REQ")
        val REG_NAME: IElementType = RequirementsElementType("REG_NAME")
        val RELATIVE_PART: IElementType = RequirementsElementType("RELATIVE_PART")
        val RELATIVE_REF: IElementType = RequirementsElementType("RELATIVE_REF")
        val REQUIRE_HASHES_REQ: IElementType = RequirementsElementType("REQUIRE_HASHES_REQ")
        val SCHEME: IElementType = RequirementsElementType("SCHEME")
        val SEGMENT: IElementType = RequirementsElementType("SEGMENT")
        val SEGMENT_NZ: IElementType = RequirementsElementType("SEGMENT_NZ")
        val SEGMENT_NZ_NC: IElementType = RequirementsElementType("SEGMENT_NZ_NC")
        val TRUSTED_HOST_REQ: IElementType = RequirementsElementType("TRUSTED_HOST_REQ")
        val UNRESERVED: IElementType = RequirementsElementType("UNRESERVED")
        val URI: IElementType = RequirementsElementType("URI")
        val URI_REFERENCE: IElementType = RequirementsElementType("URI_REFERENCE")
        val URLSPEC: IElementType = RequirementsElementType("URLSPEC")
        val URL_REQ: IElementType = RequirementsElementType("URL_REQ")
        val USERINFO: IElementType = RequirementsElementType("USERINFO")
        val VARIABLE_NAME: IElementType = RequirementsElementType("VARIABLE_NAME")
        val VERSIONSPEC: IElementType = RequirementsElementType("VERSIONSPEC")
        val VERSION_CMP_STMT: IElementType = RequirementsElementType("VERSION_CMP_STMT")
        val VERSION_MANY: IElementType = RequirementsElementType("VERSION_MANY")
        val VERSION_ONE: IElementType = RequirementsElementType("VERSION_ONE")
        val VERSION_STMT: IElementType = RequirementsElementType("VERSION_STMT")
        val AND: IElementType = RequirementsTokenType("AND")
        val AT: IElementType = RequirementsTokenType("AT")
        val COLON: IElementType = RequirementsTokenType("COLON")
        val COMMA: IElementType = RequirementsTokenType("COMMA")
        val COMMENT: IElementType = RequirementsTokenType("COMMENT")
        val CONSTRAINT: IElementType = RequirementsTokenType("CONSTRAINT")
        val DIGIT: IElementType = RequirementsTokenType("DIGIT")
        val DOLLAR_SIGN: IElementType = RequirementsTokenType("DOLLAR_SIGN")
        val DOT: IElementType = RequirementsTokenType("DOT")
        val DQUOTE: IElementType = RequirementsTokenType("DQUOTE")
        val EDITABLE: IElementType = RequirementsTokenType("EDITABLE")
        val ENV_VAR: IElementType = RequirementsTokenType("ENV_VAR")
        val EOL: IElementType = RequirementsTokenType("EOL")
        val EXTRA_INDEX_URL: IElementType = RequirementsTokenType("EXTRA_INDEX_URL")
        val FIND_LINKS: IElementType = RequirementsTokenType("FIND_LINKS")
        val IDENTIFIER: IElementType = RequirementsTokenType("IDENTIFIER")
        val IN: IElementType = RequirementsTokenType("IN")
        val INDEX_URL: IElementType = RequirementsTokenType("INDEX_URL")
        val LBRACE: IElementType = RequirementsTokenType("LBRACE")
        val LETTER: IElementType = RequirementsTokenType("LETTER")
        val LONG_OPTION: IElementType = RequirementsTokenType("LONG_OPTION")
        val LPARENTHESIS: IElementType = RequirementsTokenType("LPARENTHESIS")
        val LSBRACE: IElementType = RequirementsTokenType("LSBRACE")
        val MINUS: IElementType = RequirementsTokenType("MINUS")
        val NOT: IElementType = RequirementsTokenType("NOT")
        val NO_BINARY: IElementType = RequirementsTokenType("NO_BINARY")
        val NO_INDEX: IElementType = RequirementsTokenType("NO_INDEX")
        val ONLY_BINARY: IElementType = RequirementsTokenType("ONLY_BINARY")
        val OR: IElementType = RequirementsTokenType("OR")
        val PERCENT_SIGN: IElementType = RequirementsTokenType("PERCENT_SIGN")
        val PLUS: IElementType = RequirementsTokenType("PLUS")
        val PYTHON_STR_C: IElementType = RequirementsTokenType("PYTHON_STR_C")
        val QUESTION_MARK: IElementType = RequirementsTokenType("QUESTION_MARK")
        val RBRACE: IElementType = RequirementsTokenType("RBRACE")
        val REFER: IElementType = RequirementsTokenType("REFER")
        val REQUIRE_HASHES: IElementType = RequirementsTokenType("REQUIRE_HASHES")
        val RPARENTHESIS: IElementType = RequirementsTokenType("RPARENTHESIS")
        val RSBRACE: IElementType = RequirementsTokenType("RSBRACE")
        val SEMICOLON: IElementType = RequirementsTokenType("SEMICOLON")
        val SHARP: IElementType = RequirementsTokenType("SHARP")
        val SHORT_OPTION: IElementType = RequirementsTokenType("SHORT_OPTION")
        val SLASH: IElementType = RequirementsTokenType("SLASH")
        val SQUOTE: IElementType = RequirementsTokenType("SQUOTE")
        val SUB_DELIMS: IElementType = RequirementsTokenType("SUB_DELIMS")
        val TILDA: IElementType = RequirementsTokenType("TILDA")
        val TRUSTED_HOST: IElementType = RequirementsTokenType("TRUSTED_HOST")
        val UNDERSCORE: IElementType = RequirementsTokenType("UNDERSCORE")
        val VERSION: IElementType = RequirementsTokenType("VERSION")
        val VERSION_CMP: IElementType = RequirementsTokenType("VERSION_CMP")
        val WHITE_SPACE: IElementType = RequirementsTokenType("WHITE_SPACE")
    }
}
