package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Option : PsiElement {

    val constraintReq: ConstraintReq?

    val editableReq: EditableReq?

    val extraIndexUrlReq: ExtraIndexUrlReq?

    val findLinksReq: FindLinksReq?

    val indexUrlReq: IndexUrlReq?

    val noBinaryReq: NoBinaryReq?

    val noIndexReq: NoIndexReq?

    val onlyBinaryReq: OnlyBinaryReq?

    val referReq: ReferReq?

    val requireHashesReq: RequireHashesReq?

    val trustedHostReq: TrustedHostReq?

}
