package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.annotations.Nls
import ru.meanmail.formatPackageName
import ru.meanmail.psi.*


class DuplicatedInspection : LocalInspectionTool() {
    @Nls
    override fun getDisplayName(): String {
        return "Duplicated package"
    }

    override fun buildVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean,
                              session: LocalInspectionToolSession): PsiElementVisitor {
        return Visitor(holder, isOnTheFly, session)
    }

    companion object {
        const val MAX_LENGTH: Int = 32

        class Visitor(holder: ProblemsHolder,
                      onTheFly: Boolean,
                      session: LocalInspectionToolSession) :
                BaseInspectionVisitor(holder, onTheFly, session) {

            override fun visitRequirementsFile(element: RequirementsFile) {
                val names = mutableMapOf<String, Int>()
                val psiDocumentManager = PsiDocumentManager.getInstance(element.project)
                val document = psiDocumentManager.getDocument(element.containingFile)

                for (req in element.children) {
                    val (text, type) = when (req) {
                        is NameReq -> formatPackageName(req.name.text) to "package"
                        is UrlReq -> formatPackageName(req.name.text) to "package"
                        is ReferReq -> req.uriReference?.text to "file"
                        is EditableReq -> req.uriReference?.text to "url"
                        is PathReq -> req.uriReference.text to "path"
                        else -> null
                    } ?: continue

                    val textOffset = req.textOffset
                    val lineNumber = document!!.getLineNumber(textOffset) + 1

                    if (text in names) {
                        val line = names[text]
                        val name = if (text.length <= MAX_LENGTH) {
                            text
                        } else {
                            text.substring(0, MAX_LENGTH - 3) + "..."
                        }

                        val message = "The '$name' $type on line $lineNumber " +
                                "is already defined on line $line"
                        holder.registerProblem(element, message)
                    } else {
                        names[text] = lineNumber
                    }
                }
            }
        }
    }
}
