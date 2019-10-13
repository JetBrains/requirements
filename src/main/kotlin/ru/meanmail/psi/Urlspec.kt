package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Urlspec : PsiElement {

    val uriReference: UriReference

}
