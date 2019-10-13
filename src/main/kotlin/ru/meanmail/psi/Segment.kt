package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Segment : PsiElement {

    val pcharList: List<Pchar>

}
