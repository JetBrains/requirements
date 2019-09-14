package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Marker : PsiElement {

    val markerOr: MarkerOr

}
