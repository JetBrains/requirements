package ru.meanmail.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import ru.meanmail.RequirementsFileType

object RequirementsElementFactory {
    fun createPackage(project: Project, name: String): RequirementsPackageStmt {
        val file = createFile(project, name)
        return file.firstChild as RequirementsPackageStmt
    }
    
    fun createSimplePackage(project: Project, name: String): RequirementsSimplePackageStmt {
        val file = createFile(project, name)
        return file.firstChild as RequirementsSimplePackageStmt
    }
    
    fun createRequirements(project: Project, name: String): RequirementsRequirementStmt {
        val file = createFile(project, name)
        return file.firstChild as RequirementsRequirementStmt
    }
    
    fun createFile(project: Project, text: String): RequirementsFile {
        val name = "requirements.txt";
        return PsiFileFactory.getInstance(project).createFileFromText(name, RequirementsFileType, text) as RequirementsFile;
    }
}
