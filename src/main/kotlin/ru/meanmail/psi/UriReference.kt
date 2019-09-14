package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface UriReference : PsiElement {

    val relativeRef: RelativeRef?

    val uri: Uri?

}
