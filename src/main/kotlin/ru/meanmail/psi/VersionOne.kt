package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface VersionOne : PsiElement {

    val version: VersionStmt

    val versionCmp: VersionCmpStmt

    fun setVersion(newVersion: String)
}
