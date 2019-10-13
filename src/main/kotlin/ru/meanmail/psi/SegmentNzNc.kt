package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface SegmentNzNc : PsiElement {

    val pctEncodedList: List<PctEncoded>

    val unreservedList: List<Unreserved>

}
