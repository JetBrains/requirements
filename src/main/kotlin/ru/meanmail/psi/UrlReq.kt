package ru.meanmail.psi

interface UrlReq : NamedElement, Requirement {

    val extras: Extras?

    val name: Name

    val quotedMarker: QuotedMarker?

    val urlspec: Urlspec

    override fun enabled(values: Map<String, String?>): Boolean {
        return quotedMarker?.logical()?.check(values) ?: true
    }

    override val displayName: String
        get() {
            return name.text
        }

    override fun toRepresentation(): String {
        return quotedMarker?.logical()?.toRepresentation() ?: "false"
    }

}
