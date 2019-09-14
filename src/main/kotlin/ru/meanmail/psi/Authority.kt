package ru.meanmail.psi

import com.intellij.psi.PsiElement

interface Authority : PsiElement {

    val host: Host

    val port: Port?

    val userinfo: Userinfo?

}
