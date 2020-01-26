package ru.meanmail.psi

interface EditableReq : NamedElement, Requirement {

    val uriReference: UriReference?

    override fun enabled(values: Map<String, String?>): Boolean {
        return true
    }

    override val displayName: String
        get() {
            return uriReference?.text ?: ""
        }

}
