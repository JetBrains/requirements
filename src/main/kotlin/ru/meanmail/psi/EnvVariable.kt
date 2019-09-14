package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface EnvVariable : PsiElement {

    val variableName: VariableName

}
