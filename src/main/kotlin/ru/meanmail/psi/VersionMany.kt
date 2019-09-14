package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface VersionMany : PsiElement {

    val versionOneList: List<VersionOne>

}
