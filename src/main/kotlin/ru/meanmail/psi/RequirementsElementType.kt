package ru.meanmail.psi

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import ru.meanmail.RequirementsLanguage

class RequirementsElementType(@NonNls debugName: String) : IElementType(debugName, RequirementsLanguage)
