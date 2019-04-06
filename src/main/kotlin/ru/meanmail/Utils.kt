package ru.meanmail

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.jetbrains.python.packaging.*
import ru.meanmail.psi.RequirementsPsiImplUtil


fun getPackage(project: Project, packageName: String): PyPackage? {
    val packageManager = RequirementsPsiImplUtil.getPackageManager(project) ?: return null
    val packages = packageManager.refreshAndGetPackages(false)
    return PyPackageUtil.findPackage(packages, packageName)
}

fun isInstalled(project: Project, packageName: String?, version: String?): Boolean {
    packageName ?: return false
    val pyPackage = getPackage(project, packageName) ?: return false
    if (!pyPackage.isInstalled) {
        return false
    }
    version ?: return true
    val thisVersion = PyPackageVersionNormalizer.normalize(version) ?: return true
    val installedVersion = PyPackageVersionNormalizer.normalize(pyPackage.version) ?: return false
    return PyPackageVersionComparator.compare(thisVersion, installedVersion) == 0
}

fun installPackage(project: Project, packageName: String,
                   version: String, relation: String,
                   onInstalled: (() -> Unit)?) {
    val text = "$packageName$relation$version"
    val title = "Installing '$text'"
    
    // TODO Use relation
    
    val task = object : Task.Backgroundable(project, title) {
        override fun run(indicator: ProgressIndicator) {
            indicator.text = this.title
            indicator.isIndeterminate = true
            
            val application = ApplicationManager.getApplication()
            
            application.runReadAction {
                try {
                    val packageManager = RequirementsPsiImplUtil.getPackageManager(project)
                    
                    if (packageManager != null) {
                        packageManager.install(text)
                    } else {
                        Notification("pip",
                                title,
                                "Package manager is not available",
                                NotificationType.ERROR).notify(project)
                        return@runReadAction
                    }
                    val pyPackage = getPackage(project, packageName)
                    
                    if (pyPackage == null) {
                        Notification("pip",
                                title,
                                "Failed. Not installed",
                                NotificationType.ERROR).notify(project)
                        return@runReadAction
                    }
                    
                    Notification("pip",
                            "${pyPackage.name} (${pyPackage.version})",
                            "Successfully installed",
                            NotificationType.INFORMATION).notify(project)
                    if (onInstalled != null) {
                        onInstalled()
                    }
                } catch (e: PyExecutionException) {
                    Notification(e.command,
                            e.stdout,
                            e.stderr,
                            NotificationType.ERROR).notify(project)
                }
            }
        }
    }
    
    ProgressManager.getInstance().run(task)
}
