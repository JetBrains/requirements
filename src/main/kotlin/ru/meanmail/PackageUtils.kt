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
import java.io.IOException
import java.time.LocalDateTime

val cache = mutableMapOf<String, Pair<PyPackageVersion?, LocalDateTime>>()

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

fun getCurrentVersion(project: Project, packageName: String): PyPackageVersion? {
    val pyPackage = getPackage(project, packageName) ?: return null
    if (!pyPackage.isInstalled) {
        return null
    }
    return PyPackageVersionNormalizer.normalize(pyPackage.version)
}

fun getVersions(
    project: Project,
    packageName: String,
    version: String?
): Triple<PyPackageVersion?, PyPackageVersion?, PyPackageVersion?> {
    val installed = getCurrentVersion(project, packageName)
    val latest = getLatestVersion(project, packageName)
    val required = if (version == null) {
        latest
    } else {
        PyPackageVersionNormalizer.normalize(version)
    }

    return Triple(
        first = required,
        second = installed,
        third = latest
    )
}

fun getLatestVersion(project: Project, packageName: String): PyPackageVersion? {
    val key = formatPackageName(packageName)
    val cached = cache[key]
    if (cached != null) {
        val actual = cached.second.plusDays(1).isAfter(LocalDateTime.now())
        if (actual) {
            return cached.first
        }
    }

    val latestVersion: String

    try {
        latestVersion = PyPIPackageUtil.INSTANCE
            .fetchLatestPackageVersion(project, packageName) ?: return null
    } catch (e: IOException) {
        return null
    }
    val version = PyPackageVersionNormalizer.normalize(latestVersion) ?: return null
    cache[key] = version to LocalDateTime.now()
    return version
}

fun installPackage(
    project: Project, packageName: String,
    version: String, versionCmp: String,
    onInstalled: (() -> Unit)?
) {
    val currentVersion = getCurrentVersion(project, packageName)
    if (currentVersion?.presentableText == version) {
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

    val text = "$packageName$versionCmp$version"
    val title = "Installing '$text'"

    // TODO Use versionCmp

    val task = object : Task.Backgroundable(project, title) {
        override fun run(indicator: ProgressIndicator) {
            indicator.text = this.title
            indicator.isIndeterminate = true

            try {
                val packageManager = getPackageManager(project)

                if (packageManager != null) {
                    packageManager.install(text)
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
