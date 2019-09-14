package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface QuotedMarker : PsiElement {

    val marker: Marker

}
