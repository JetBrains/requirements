package ru.meanmail.psi

import RequirementType

interface EditableReq : NamedElement, Requirement {

    val uriReference: UriReference?

    override fun enabled(values: Map<String, String?>): Boolean {
        return true
    }

    override val displayName: String
        get() {
            return uriReference?.text ?: ""
        }

    override val type: RequirementType
        get() = RequirementType.EDITABLE

    override fun toRepresentation(): String {
        return "true"
    }
}
