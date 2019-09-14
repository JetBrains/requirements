package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface PathAbsolute : PsiElement {

    val segmentList: List<Segment>

    val segmentNz: SegmentNz?

}
