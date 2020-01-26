package ru.meanmail.psi

interface PathReq : NamedElement, Requirement {

    val uriReference: UriReference

    override fun enabled(values: Map<String, String?>): Boolean {
        return true
    }

    override val displayName: String
        get() {
            return uriReference.text
        }

}
