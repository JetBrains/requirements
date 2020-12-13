package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.Logical

interface VersionMany : PsiElement {

    val versionOneList: List<VersionOne>

    fun logical(): Logical
}
