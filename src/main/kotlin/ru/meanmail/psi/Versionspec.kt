package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Versionspec : PsiElement {

    val versionOneList: List<VersionOne?>
    fun isActual(version: String): Boolean

}
