package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Userinfo : PsiElement {

    val envVariableList: List<EnvVariable>

    val pctEncodedList: List<PctEncoded>

    val unreservedList: List<Unreserved>

}
