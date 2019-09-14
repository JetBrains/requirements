package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface PathReq : PsiElement {

    val uriReference: UriReference

}
