package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface PathNoscheme : PsiElement {

    val segmentList: List<Segment>

    val segmentNzNc: SegmentNzNc

}
