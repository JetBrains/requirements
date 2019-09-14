package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface EditableReq : PsiElement {

    val uriReference: UriReference?

}
