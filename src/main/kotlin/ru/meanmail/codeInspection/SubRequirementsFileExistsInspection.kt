package ru.meanmail.codeInspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.annotations.Nls
import ru.meanmail.resolveFile

class SubRequirementsFileExistsInspection : LocalInspectionTool() {
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

//            override fun visitRequirementsFilenameStmt(element: RequirementsFilenameStmt) {
//                val filename = element.filename ?: return
//                val directory = element.containingFile.containingDirectory.virtualFile
//                val file = resolveFile(filename, directory)
//
//                if (file == null) {
//                    val description = "File '${element.filename}' is not exists"
//                    holder.registerProblem(element, description)
//                } else if (file.isDirectory) {
//                    val description = "'${element.filename}' is a directory"
//                    holder.registerProblem(element, description)
//                }
//            }
        }
    }
}
