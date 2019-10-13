package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Ls32 : PsiElement {

    val iPv4Address: IPv4Address?

    val h16: H16?

    val h16Colon: H16Colon?

}
