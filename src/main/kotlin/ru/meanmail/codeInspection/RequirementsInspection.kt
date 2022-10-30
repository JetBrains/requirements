package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool

abstract class RequirementsInspection : LocalInspectionTool() {
    override fun getDescriptionFileName(): String {
        return "requirements.${this::class.simpleName}"
    }
}
