package ru.meanmail.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor

class Visitor : PsiElementVisitor() {
    fun visitIPLiteral(o: IPLiteral) {
        visitPsiElement(o)
    }

    fun visitIPv4Address(o: IPv4Address) {
        visitPsiElement(o)
    }

    fun visitIPv6Address(o: IPv6Address) {
        visitPsiElement(o)
    }

    fun visitIPvFuture(o: IPvFuture) {
        visitPsiElement(o)
    }

    fun visitAuthority(o: Authority) {
        visitPsiElement(o)
    }

    fun visitBinaryList(o: BinaryList) {
        visitPsiElement(o)
    }

    fun visitConstraintReq(o: ConstraintReq) {
        visitPsiElement(o)
    }

    fun visitDecOctet(o: DecOctet) {
        visitPsiElement(o)
    }

    fun visitEditableReq(o: EditableReq) {
        visitPsiElement(o)
    }

    fun visitEnvVariable(o: EnvVariable) {
        visitPsiElement(o)
    }

    fun visitExtraIndexUrlReq(o: ExtraIndexUrlReq) {
        visitPsiElement(o)
    }

    fun visitExtras(o: Extras) {
        visitPsiElement(o)
    }

    fun visitExtrasList(o: ExtrasList) {
        visitPsiElement(o)
    }

    fun visitFindLinksReq(o: FindLinksReq) {
        visitPsiElement(o)
    }

    fun visitFragment(o: Fragment) {
        visitPsiElement(o)
    }

    fun visitHashOption(o: HashOption) {
        visitPsiElement(o)
    }

    fun visitH16(o: H16) {
        visitPsiElement(o)
    }

    fun visitH16Colon(o: H16Colon) {
        visitPsiElement(o)
    }

    fun visitHexdig(o: Hexdig) {
        visitPsiElement(o)
    }

    fun visitHierPart(o: HierPart) {
        visitPsiElement(o)
    }

    fun visitHost(o: Host) {
        visitPsiElement(o)
    }

    fun visitIndexUrlReq(o: IndexUrlReq) {
        visitPsiElement(o)
    }

    fun visitLs32(o: Ls32) {
        visitPsiElement(o)
    }

    fun visitMarker(o: Marker) {
        visitPsiElement(o)
    }

    fun visitMarkerAnd(o: MarkerAnd) {
        visitPsiElement(o)
    }

    fun visitMarkerExpr(o: MarkerExpr) {
        visitPsiElement(o)
    }

    fun visitMarkerOp(o: MarkerOp) {
        visitPsiElement(o)
    }

    fun visitMarkerOr(o: MarkerOr) {
        visitPsiElement(o)
    }

    fun visitMarkerVar(o: MarkerVar) {
        visitPsiElement(o)
    }

    fun visitName(o: Name) {
        visitPsiElement(o)
    }

    fun visitNameReq(o: NameReq) {
        visitPsiElement(o)
    }

    fun visitNoBinaryReq(o: NoBinaryReq) {
        visitPsiElement(o)
    }

    fun visitNoIndexReq(o: NoIndexReq) {
        visitPsiElement(o)
    }

    fun visitNz(o: Nz) {
        visitPsiElement(o)
    }

    fun visitOnlyBinaryReq(o: OnlyBinaryReq) {
        visitPsiElement(o)
    }

    fun visitOption(o: Option) {
        visitPsiElement(o)
    }

    fun visitPathAbempty(o: PathAbempty) {
        visitPsiElement(o)
    }

    fun visitPathAbsolute(o: PathAbsolute) {
        visitPsiElement(o)
    }

    fun visitPathEmpty(o: PathEmpty) {
        visitPsiElement(o)
    }

    fun visitPathNoscheme(o: PathNoscheme) {
        visitPsiElement(o)
    }

    fun visitPathReq(o: PathReq) {
        visitPsiElement(o)
    }

    fun visitPathRootless(o: PathRootless) {
        visitPsiElement(o)
    }

    fun visitPchar(o: Pchar) {
        visitPsiElement(o)
    }

    fun visitPctEncoded(o: PctEncoded) {
        visitPsiElement(o)
    }

    fun visitPort(o: Port) {
        visitPsiElement(o)
    }

    fun visitPythonStr(o: PythonStr) {
        visitPsiElement(o)
    }

    fun visitQuery(o: Query) {
        visitPsiElement(o)
    }

    fun visitQuotedMarker(o: QuotedMarker) {
        visitPsiElement(o)
    }

    fun visitReferReq(o: ReferReq) {
        visitPsiElement(o)
    }

    fun visitRegName(o: RegName) {
        visitPsiElement(o)
    }

    fun visitRelativePart(o: RelativePart) {
        visitPsiElement(o)
    }

    fun visitRelativeRef(o: RelativeRef) {
        visitPsiElement(o)
    }

    fun visitRequireHashesReq(o: RequireHashesReq) {
        visitPsiElement(o)
    }

    fun visitScheme(o: Scheme) {
        visitPsiElement(o)
    }

    fun visitSegment(o: Segment) {
        visitPsiElement(o)
    }

    fun visitSegmentNz(o: SegmentNz) {
        visitPsiElement(o)
    }

    fun visitSegmentNzNc(o: SegmentNzNc) {
        visitPsiElement(o)
    }

    fun visitTrustedHostReq(o: TrustedHostReq) {
        visitPsiElement(o)
    }

    fun visitUnreserved(o: Unreserved) {
        visitPsiElement(o)
    }

    fun visitUri(o: Uri) {
        visitPsiElement(o)
    }

    fun visitUriReference(o: UriReference) {
        visitPsiElement(o)
    }

    fun visitUrlReq(o: UrlReq) {
        visitPsiElement(o)
    }

    fun visitUrlspec(o: Urlspec) {
        visitPsiElement(o)
    }

    fun visitUserinfo(o: Userinfo) {
        visitPsiElement(o)
    }

    fun visitVariableName(o: VariableName) {
        visitPsiElement(o)
    }

    fun visitVersionCmpStmt(o: VersionCmpStmt) {
        visitPsiElement(o)
    }

    fun visitVersionMany(o: VersionMany) {
        visitPsiElement(o)
    }

    fun visitVersionOne(o: VersionOne) {
        visitPsiElement(o)
    }

    fun visitVersionStmt(o: VersionStmt) {
        visitPsiElement(o)
    }

    fun visitVersionspec(o: Versionspec) {
        visitPsiElement(o)
    }

    fun visitPsiElement(o: PsiElement) {
        visitElement(o)
    }
}
