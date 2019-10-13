package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface MarkerOr : PsiElement {

    val markerAndList: List<MarkerAnd>

}
