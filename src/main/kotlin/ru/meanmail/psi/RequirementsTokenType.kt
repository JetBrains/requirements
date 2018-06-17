package ru.meanmail.psi


import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import ru.meanmail.RequirementsLanguage

class RequirementsTokenType(@NonNls debugName: String) : IElementType(debugName, RequirementsLanguage) {
    
    override fun toString(): String {
        return "RequirementsTokenType." + super.toString()
    }
}
