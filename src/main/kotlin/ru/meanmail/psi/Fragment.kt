package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Fragment : PsiElement {

    val pcharList: List<Pchar>

}
