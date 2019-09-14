package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface NameReq : PsiElement {

    val extras: Extras?

    val name: Name

    val quotedMarker: QuotedMarker?

    val versionspec: Versionspec?

}
