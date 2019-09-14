package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface ReferReq : PsiElement {

    val uriReference: UriReference?

}
