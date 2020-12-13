package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.Logical

interface VersionOne : PsiElement {

    val versionCmp: VersionCmpStmt

    val version: VersionStmt

    fun logical(): Logical

}
