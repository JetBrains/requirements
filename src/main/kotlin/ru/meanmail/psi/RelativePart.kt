package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface RelativePart : PsiElement {

    val authority: Authority?

    val pathAbempty: PathAbempty?

    val pathAbsolute: PathAbsolute?

    val pathEmpty: PathEmpty?

    val pathNoscheme: PathNoscheme?

}
