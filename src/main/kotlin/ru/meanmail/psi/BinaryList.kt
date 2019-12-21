package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface BinaryList : PsiElement {

    val extrasList: ExtrasList?

}
