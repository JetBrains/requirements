package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface MarkerExpr : PsiElement {

    val marker: Marker?

    val markerOp: MarkerOp?

    val markerVarList: List<MarkerVar>

}
