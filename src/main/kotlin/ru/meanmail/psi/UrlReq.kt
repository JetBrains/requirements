package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface UrlReq : PsiElement {

    val extras: Extras?

    val name: Name

    val quotedMarker: QuotedMarker?

    val urlspec: Urlspec

}
