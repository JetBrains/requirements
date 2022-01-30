package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface PythonStr : PsiElement {
    val pythonDquoteStr: PythonDquoteStr?
    val pythonSquoteStr: PythonSquoteStr?
}
