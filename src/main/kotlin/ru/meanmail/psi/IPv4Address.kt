package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface IPv4Address : PsiElement {

    val decOctetList: List<DecOctet>

}
