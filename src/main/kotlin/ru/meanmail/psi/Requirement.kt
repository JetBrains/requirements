package ru.meanmail.psi

import ru.meanmail.RequirementType
import com.intellij.psi.PsiElement

interface Requirement : PsiElement {

    val type: RequirementType

    val displayName: String

    fun enabled(values: Map<String, String?>): Boolean

}
