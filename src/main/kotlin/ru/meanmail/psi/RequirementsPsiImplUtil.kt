package ru.meanmail.psi

import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.roots.ProjectRootManager
import com.jetbrains.python.packaging.PyPackageManager
import com.jetbrains.python.sdk.PythonSdkType

object RequirementsPsiImplUtil {
    fun getSdk(project: Project): Sdk? {
        val projectRootManager = ProjectRootManager.getInstance(project)
        val projectSdk = projectRootManager.projectSdk ?: return null
        if (projectSdk.sdkType is PythonSdkType) {
            return projectSdk
        }
        return null
    }

    fun getPackageManager(project: Project): PyPackageManager? {
        val sdk = getSdk(project) ?: return null
        return PyPackageManager.getInstance(sdk)
    }
}
