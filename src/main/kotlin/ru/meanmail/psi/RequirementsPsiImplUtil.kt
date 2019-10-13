package ru.meanmail.psi

import com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiElement
import com.jetbrains.python.packaging.PyPackageManager
import com.jetbrains.python.sdk.PythonSdkType

object RequirementsPsiImplUtil {

    @JvmStatic
    fun getPackageManager(project: Project): PyPackageManager? {
        val projectRootManager = ProjectRootManager.getInstance(project)
        val projectSdk = projectRootManager.projectSdk ?: return null
        if (projectSdk.sdkType is PythonSdkType) {
            return PyPackageManager.getInstance(projectSdk)
        }
        return null
    }

    @JvmStatic
    fun setVersion(element: VersionOne, newVersion: String): PsiElement {
        val version = ElementFactory.createVersion(newVersion)
        runWriteCommandAction(element.project,
                "Update package version",
                "Requirements", Runnable {
            element.node.replaceChild(element.node.lastChildNode, version)
        })
        return element
    }
}
