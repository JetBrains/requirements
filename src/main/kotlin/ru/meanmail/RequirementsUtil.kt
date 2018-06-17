package ru.meanmail

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.indexing.FileBasedIndex
import ru.meanmail.psi.RequirementsPackageStmt

object RequirementsUtil {
    fun findPackages(project: Project, packageName: String): List<RequirementsPackageStmt> {
        val result = mutableListOf<RequirementsPackageStmt>()
        val virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, RequirementsFileType,
                GlobalSearchScope.allScope(project))
        for (virtualFile in virtualFiles) {
            val requirementsFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (requirementsFile != null) {
                val packageStmts = PsiTreeUtil.getChildrenOfType(requirementsFile, RequirementsPackageStmt::class.java)
                if (packageStmts != null) {
                    for (packageStmt in packageStmts) {
                        if (packageName == packageStmt.packageName) {
                            result.add(packageStmt)
                        }
                    }
                }
            }
        }
        return result
    }
    
    fun findPackages(project: Project): List<RequirementsPackageStmt> {
        val result = mutableListOf<RequirementsPackageStmt>()
        val virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, RequirementsFileType,
                GlobalSearchScope.allScope(project))
        for (virtualFile in virtualFiles) {
            val requirementsFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (requirementsFile != null) {
                val packageStmts = PsiTreeUtil.getChildrenOfType(requirementsFile, RequirementsPackageStmt::class.java)
                if (packageStmts != null) {
                    result.addAll(packageStmts)
                }
            }
        }
        return result.toList();
    }
}
