package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import ru.meanmail.psi.NamedElement

abstract class RequirementsNamedElementImpl(node: ASTNode) :
        NamedElement, ASTWrapperPsiElement(node)
