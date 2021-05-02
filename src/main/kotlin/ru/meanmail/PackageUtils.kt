package ru.meanmail

import com.intellij.execution.ExecutionException
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.roots.ProjectRootManager
import com.jetbrains.python.packaging.*
import com.jetbrains.python.sdk.PythonSdkType
import ru.meanmail.notification.Notifier

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

fun getPackages(project: Project): List<PyPackage> {
    val packageManager = getPackageManager(project) ?: return emptyList()
    return ApplicationManager.getApplication()
        .executeOnPooledThread<List<PyPackage>> {
            try {
                return@executeOnPooledThread packageManager.refreshAndGetPackages(false)
            } catch (e: ExecutionException) {
                return@executeOnPooledThread emptyList()
            }
        }.get() ?: return emptyList()
}

fun getPackage(project: Project, packageName: String): PyPackage? {
    val packages = getPackages(project)
    val formattedPackageName = formatPackageName(packageName)

    return packages.firstOrNull { formatPackageName(it.name) == formattedPackageName }
}

fun getInstalledPackages(project: Project): List<PyPackage> {
    val packages = getPackages(project)

    return packages.filter { it.isInstalled }
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
        Notifier.notifyInformation(
            project, "$packageName (${version})", "Successfully installed"
        )
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
                    Notifier.notifyError(
                        project, title, "Package manager is not available"
                    )
                    return
                }
                val pyPackage = getPackage(project, packageName)

                if (pyPackage == null || pyPackage.version != version) {
                    Notifier.notifyError(
                        project, title, "Failed. Not installed"
                    )
                    return
                }

                Notifier.notifyInformation(
                    project,
                    "${pyPackage.name} (${pyPackage.version})",
                    "Successfully installed"
                )
                if (onInstalled != null) {
                    onInstalled()
                }
            } catch (e: PyExecutionException) {
                Notifier.notifyError(
                    project, e.command, e.toString()
                )
            }
        }
    }

    ProgressManager.getInstance().run(task)
}

fun uninstallPackage(
    project: Project, packageName: String, onInstalled: (() -> Unit)?
) {
    val installedVersion = getPackage(project, packageName)
    if (installedVersion?.isInstalled != true) {
        Notifier.notifyInformation(
            project, packageName, "Successfully uninstalled"
        )
        if (onInstalled != null) {
            onInstalled()
        }
        return
    }

    val title = "Uninstalling '$packageName'"

    val task = object : Task.Backgroundable(project, title) {
        override fun run(indicator: ProgressIndicator) {
            indicator.text = this.title
            indicator.isIndeterminate = true

            try {
                val packageManager = getPackageManager(project)

                if (packageManager != null) {
                    packageManager.uninstall(listOf(installedVersion))
                } else {
                    Notifier.notifyError(
                        project, title, "Package manager is not available"
                    )
                    return
                }

                Notifier.notifyInformation(
                    project,
                    packageName,
                    "Successfully uninstalled"
                )
                if (onInstalled != null) {
                    onInstalled()
                }
            } catch (e: PyExecutionException) {
                Notifier.notifyError(
                    project, e.command, e.toString()
                )
            }
        }
    }

    ProgressManager.getInstance().run(task)
}

operator fun PyPackageVersion?.compareTo(other: PyPackageVersion?): Int {
    if (this == null && other == null) {
        return 0
    }
    if (this == null) {
        return -1
    }
    if (other == null) {
        return 1
    }
    val partsThis = this.release.split('.')
    val partsOther = other.release.split('.')
    val diff = partsThis.size - partsOther.size
    var a = this
    var b = other
    if (diff != 0) {
        val tail = Array(kotlin.math.abs(diff)) { _ -> "0" }
        if (diff > 0) {
            b = other.copy(
                release = (partsOther + tail).joinToString(".")
            )
        } else {
            a = this.copy(
                release = (partsThis + tail).joinToString(".")
            )
        }
    }
    return PyPackageVersionComparator.compare(a, b)
}

fun compareVersions(
    actual: PyPackageVersion?,
    operation: String,
    required: PyPackageVersion?
): Boolean {
    if (operation == "===") {
        return actual?.presentableText == required?.presentableText
    }

    actual ?: return false
    required ?: return false

    if (operation == "==") {
        return actual.compareTo(required) == 0
    }

    if (operation == "~=") {
        val parts = required.release.split('.')
        if (parts.size < 2) {
            return compareVersions(actual, "==", required)
        }
        val partsAsInt = parts.subList(0, parts.size - 2).map { it.toInt() }
        val lastGroup = parts[parts.size - 2].toInt() + 1

        val maxRequired = required.copy(
            release = (partsAsInt + listOf(lastGroup)).joinToString(".")
        )
        return compareVersions(actual, ">=", required) &&
                compareVersions(actual, "<", maxRequired)
    }

    if (operation == "!=") {
        return actual.compareTo(required) != 0
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
