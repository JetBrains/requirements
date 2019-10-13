package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface IPvFuture : PsiElement {

    val hexdigList: List<Hexdig>

    val unreservedList: List<Unreserved>

}
