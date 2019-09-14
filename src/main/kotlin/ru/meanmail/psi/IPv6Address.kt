package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface IPv6Address : PsiElement {

    val h16: H16?

    val h16ColonList: List<H16Colon>

    val ls32: Ls32?

}
