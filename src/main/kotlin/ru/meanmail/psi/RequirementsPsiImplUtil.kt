package ru.meanmail.psi

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
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
}
