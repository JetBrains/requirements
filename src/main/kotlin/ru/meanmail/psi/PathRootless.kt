package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface PathRootless : PsiElement {

    val segmentList: List<Segment>

    val segmentNz: SegmentNz

}
