package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface VersionCmpStmt : PsiElement {

    val isExact: Boolean
        get() = text == "=="

}
