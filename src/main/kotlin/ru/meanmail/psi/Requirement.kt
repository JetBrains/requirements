package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Requirement : PsiElement {

    val displayName: String

    fun enabled(values: Map<String, String?>): Boolean

    fun toRepresentation(): String
}
