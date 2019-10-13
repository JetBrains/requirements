package ru.meanmail.psi

interface UriReference : NamedElement {

    val relativeRef: RelativeRef?

    val uri: Uri?

}
