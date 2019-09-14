package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface MarkerAnd : PsiElement {

    val markerExprList: List<MarkerExpr>

}
