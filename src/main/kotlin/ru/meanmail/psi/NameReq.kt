package ru.meanmail.psi

import ru.meanmail.RequirementType

interface NameReq : NamedElement, Requirement {

    val name: Name

    val extras: Extras?

    val versionspec: Versionspec?

    val hashOptionList: List<HashOption?>

    val quotedMarker: QuotedMarker?

    override fun enabled(values: Map<String, String?>): Boolean {
        return quotedMarker?.logical()?.check(values) ?: true
    }

    override val displayName: String
        get() {
            return name.text
        }

    val requirement: String
        get() {
            return "${name.text}${extras?.text ?: ""}${versionspec?.text ?: ""}"
        }

    override val type: RequirementType
        get() = RequirementType.NAME

    fun setVersion(newVersion: String)

}
