package ru.meanmail.psi

import ru.meanmail.RequirementType

interface PathReq : NamedElement, Requirement {

    val uriReference: UriReference

    val quotedMarker: QuotedMarker?

    override fun enabled(values: Map<String, String?>): Boolean {
        return quotedMarker?.logical()?.check(values) ?: true
    }

    override val displayName: String
        get() {
            return uriReference.text
        }

    override val type: RequirementType
        get() = RequirementType.PATH

}
