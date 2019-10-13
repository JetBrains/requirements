package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface RelativeRef : PsiElement {

    val fragment: Fragment?

    val query: Query?

    val relativePart: RelativePart

}
