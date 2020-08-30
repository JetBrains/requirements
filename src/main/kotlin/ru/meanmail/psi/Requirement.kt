package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.RequirementType

interface Requirement : PsiElement {

    val type: RequirementType

    val displayName: String

    fun enabled(values: Map<String, String?>): Boolean

}
