package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Pchar : PsiElement {

    val pctEncoded: PctEncoded?

    val unreserved: Unreserved?

}
