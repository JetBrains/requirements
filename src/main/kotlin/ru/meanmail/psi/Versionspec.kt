package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Versionspec : PsiElement {

    val versionMany: VersionMany

}
