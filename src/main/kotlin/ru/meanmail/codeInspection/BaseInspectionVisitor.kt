package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.NameReq
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.psi.UriReference

open class BaseInspectionVisitor(val holder: ProblemsHolder,
                                 val onTheFly: Boolean,
                                 val session: LocalInspectionToolSession) : PsiElementVisitor() {
    override fun visitElement(element: PsiElement?) {
        when (element) {
            is RequirementsFile -> visitRequirementsFile(element)
            is UriReference -> visitUriReference(element)
            is NameReq -> visitNameReq(element)
            else -> super.visitElement(element)
        }
    }

    open fun visitUriReference(element: UriReference) {
        super.visitElement(element)
    }

    open fun visitNameReq(element: NameReq) {
        super.visitElement(element)
    }

    open fun visitRequirementsFile(element: RequirementsFile) {
        super.visitElement(element)
    }
}
