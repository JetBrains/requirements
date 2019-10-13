package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface HierPart : PsiElement {

    val authority: Authority?

    val pathAbempty: PathAbempty?

    val pathAbsolute: PathAbsolute?

    val pathEmpty: PathEmpty?

    val pathRootless: PathRootless?

}
