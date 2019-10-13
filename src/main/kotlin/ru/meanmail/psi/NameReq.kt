package ru.meanmail.psi

interface NameReq : NamedElement {

    val extras: Extras?

    val name: Name

    val quotedMarker: QuotedMarker?

    val versionspec: Versionspec?

}
