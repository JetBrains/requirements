package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Query : PsiElement {

    val pcharList: List<Pchar>

}
