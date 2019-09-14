package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface IPLiteral : PsiElement {

    val iPv6Address: IPv6Address?

    val iPvFuture: IPvFuture?

}
