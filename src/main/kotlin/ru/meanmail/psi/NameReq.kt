package ru.meanmail.psi

interface NameReq : NamedElement, Requirement {

    val extras: Extras?

    val name: Name

    val quotedMarker: QuotedMarker?

    val versionspec: Versionspec?

    override fun enabled(values: Map<String, String?>): Boolean {
        return quotedMarker?.logical()?.check(values) ?: true
    }

    override val displayName: String
        get() {
            return name.text
        }

}
