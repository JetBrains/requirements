package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface SegmentNz : PsiElement {

    val pcharList: List<Pchar>

}
