package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFileSystemItem
import org.jetbrains.annotations.Nls
import ru.meanmail.psi.UriReference

class ReferenceExistsInspection : LocalInspectionTool() {
    @Nls
    override fun getDisplayName(): String {
        return "File is not exists"
    }

    override fun buildVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean,
                              session: LocalInspectionToolSession): PsiElementVisitor {
        return Visitor(holder, isOnTheFly, session)
    }

    companion object {
        class Visitor(holder: ProblemsHolder,
                      onTheFly: Boolean,
                      session: LocalInspectionToolSession) :
                BaseInspectionVisitor(holder, onTheFly, session) {

            override fun visitUriReference(element: UriReference) {
                val reference = element.reference
                val relativeRef = element.relativeRef

                if (relativeRef != null) {
                    if (reference?.resolve() == null) {
                        val description = "File '${relativeRef.text}' is not exists"
                        holder.registerProblem(element, description)
                    } else {
                        val file = reference.resolve() as? PsiFileSystemItem
                        if (file?.isDirectory == true) {
                            val description = "'${relativeRef.text}' is a directory"
                            holder.registerProblem(element, description)
                        }
                    }
                } else if (reference == null) {
                    val description = "'${element.text}' is unknown reference"
                    holder.registerProblem(element, description)
                }
            }
        }
    }
}
