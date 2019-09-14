package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Extras : PsiElement {

    val extrasList: ExtrasList?

}
