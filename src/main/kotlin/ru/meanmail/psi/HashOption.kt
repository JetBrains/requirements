package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface HashOption : PsiElement {

    val hexdigList: List<Hexdig?>

}
