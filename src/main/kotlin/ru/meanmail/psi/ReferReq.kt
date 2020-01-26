package ru.meanmail.psi

import RequirementType

interface ReferReq : NamedElement, Requirement {

    val uriReference: UriReference?

    override fun enabled(values: Map<String, String?>): Boolean {
        return true
    }

    override val displayName: String
        get() {
            return uriReference?.text ?: ""
        }

    override val type: RequirementType
        get() = RequirementType.REFERENCE

}
