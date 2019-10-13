package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface MarkerVar : PsiElement {

    val pythonStr: PythonStr?

}
