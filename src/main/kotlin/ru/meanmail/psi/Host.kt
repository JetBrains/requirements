package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Host : PsiElement {

    val ipLiteral: IPLiteral?

    val iPv4Address: IPv4Address?

    val regName: RegName?

}
