package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Uri : PsiElement {

    val fragment: Fragment?

    val hierPart: HierPart?

    val query: Query?

    val scheme: Scheme

}
