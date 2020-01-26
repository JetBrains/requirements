package ru.meanmail.psi

import RequirementType

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

    override val type: RequirementType
        get() = RequirementType.URL

    override fun toRepresentation(): String {
        return quotedMarker?.logical()?.toRepresentation() ?: "false"
    }

}
