package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface PathAbempty : PsiElement {

    val segmentList: List<Segment>

}
