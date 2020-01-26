package ru.meanmail.psi

import RequirementType
import com.intellij.psi.PsiElement

interface Requirement : PsiElement {

    val type: RequirementType

    val displayName: String

    fun enabled(values: Map<String, String?>): Boolean

    fun toRepresentation(): String
}
