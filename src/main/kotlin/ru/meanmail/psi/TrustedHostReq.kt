package ru.meanmail.psi

interface TrustedHostReq : NamedElement {

    val host: Host?

    val port: Port?
}
