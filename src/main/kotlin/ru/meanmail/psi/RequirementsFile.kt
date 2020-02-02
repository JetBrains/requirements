package ru.meanmail.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import ru.meanmail.RequirementsFileType
import ru.meanmail.RequirementsLanguage
import ru.meanmail.getMarkers

class RequirementsFile(viewProvider: FileViewProvider) :
        PsiFileBase(viewProvider, RequirementsLanguage) {

    override fun getFileType(): FileType {
        return RequirementsFileType
    }

    override fun toString(): String {
        return "Requirements File"
    }

    fun requirements(): List<Requirement> {
        val requirements = mutableListOf<Requirement>()
        for (child in this.children) {
            if (child is Requirement) {
                requirements.add(child)
            }
        }

        return requirements
    }

    fun enabledRequirements(): List<Requirement> {
        return requirements().filter { it.enabled(getMarkers(project)) }
    }

    fun disabledRequirements(): List<Requirement> {
        return requirements().filter { !it.enabled(getMarkers(project)) }
    }

}
