package ru.meanmail

import com.intellij.execution.ExecutionException
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.roots.ProjectRootManager
import com.jetbrains.python.packaging.*
import com.jetbrains.python.sdk.PythonSdkType

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

fun getPackage(project: Project, packageName: String): PyPackage? {
    val packageManager = getPackageManager(project) ?: return null
    val packages: List<PyPackage>
    try {
        packages = packageManager.refreshAndGetPackages(false)
    } catch (e: ExecutionException) {
        return null
    }
    val formattedPackageName = formatPackageName(packageName)

    return packages.firstOrNull { formatPackageName(it.name) == formattedPackageName }
}

fun getInstalledVersion(project: Project, packageName: String): PyPackageVersion? {
    val pyPackage = getPackage(project, packageName) ?: return null
    if (!pyPackage.isInstalled) {
        return null
    }
    return PyPackageVersionNormalizer.normalize(pyPackage.version)
}

fun installPackage(
    project: Project, packageName: String,
    version: String,
    onInstalled: (() -> Unit)?
) {
    val installedVersion = getInstalledVersion(project, packageName)
    if (installedVersion?.presentableText == version) {
        Notification(
            "pip",
            "$packageName (${version})",
            "Successfully installed",
            NotificationType.INFORMATION
        ).notify(project)
        if (onInstalled != null) {
            onInstalled()
        }
        return
    }

    val text = "$packageName version $version"
    val title = "Installing '$text'"

    val task = object : Task.Backgroundable(project, title) {
        override fun run(indicator: ProgressIndicator) {
            indicator.text = this.title
            indicator.isIndeterminate = true

            try {
                val packageManager = getPackageManager(project)

                if (packageManager != null) {
                    packageManager.install("$packageName==$version")
                } else {
                    Notification(
                        "pip",
                        title,
                        "Package manager is not available",
                        NotificationType.ERROR
                    ).notify(project)
                    return
                }
                val pyPackage = getPackage(project, packageName)

                if (pyPackage == null) {
                    Notification(
                        "pip",
                        title,
                        "Failed. Not installed",
                        NotificationType.ERROR
                    ).notify(project)
                    return
                }

                Notification(
                    "pip",
                    "${pyPackage.name} (${pyPackage.version})",
                    "Successfully installed",
                    NotificationType.INFORMATION
                ).notify(project)
                if (onInstalled != null) {
                    onInstalled()
                }
            } catch (e: PyExecutionException) {
                Notification(
                    e.command,
                    e.stdout,
                    e.stderr,
                    NotificationType.ERROR
                ).notify(project)
            }
        }
    }

    ProgressManager.getInstance().run(task)
}

operator fun PyPackageVersion?.compareTo(b: PyPackageVersion?): Int {
    if (this == null && b == null) {
        return 0
    }
    if (this == null) {
        return -1
    }
    if (b == null) {
        return 1
    }
    return PyPackageVersionComparator.compare(this, b)
}

fun compareVersions(
    actual: PyPackageVersion?,
    operation: String,
    required: PyPackageVersion?
): Boolean {
    if (operation == "===") {
        return actual == required
    }

    actual ?: return false
    required ?: return false

    if (operation == "==") {
        return actual == required
    }

    if (operation == "~=") {
        return actual >= required
    }

    if (operation == "!=") {
        return actual != required
    }

    if (operation == ">=") {
        return actual >= required
    }

    if (operation == "<=") {
        return actual <= required
    }

    if (operation == ">") {
        return actual > required
    }
    if (operation == "<") {
        return actual < required
    }

    return false
}
