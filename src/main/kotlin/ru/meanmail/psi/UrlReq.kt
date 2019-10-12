package ru.meanmail.psi

interface UrlReq : NamedElement {

    val extras: Extras?

    val name: Name

    val quotedMarker: QuotedMarker?

    val urlspec: Urlspec

}
