package ru.meanmail

import com.intellij.execution.ExecutionException
import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil
import com.intellij.psi.tree.IElementType
import com.jetbrains.python.packaging.*
import ru.meanmail.psi.RequirementsPsiImplUtil
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

fun formatPackageName(packageName: String): String {
    return packageName.replace('_', '-').toLowerCase()
}

fun getPackage(project: Project, packageName: String): PyPackage? {
    val packageManager = RequirementsPsiImplUtil
            .getPackageManager(project) ?: return null
    val packages: List<PyPackage>
    try {
        packages = packageManager.refreshAndGetPackages(false)
    } catch (e: ExecutionException) {
        return null
    }
    val formattedPackageName = formatPackageName(packageName)

    return packages.firstOrNull { formatPackageName(it.name) == formattedPackageName }
}

fun getInstalledPackage(project: Project, packageName: String): PyPackage? {
    val pyPackage = getPackage(project, packageName) ?: return null
    if (!pyPackage.isInstalled) {
        return null
    }
    return pyPackage
}

fun getVersions(project: Project,
                packageName: String): Pair<PyPackage?, PyPackageVersion?> {
    val installed = getInstalledPackage(project, packageName)
    val latest = getLatestVersion(project, packageName)

    return Pair(
            first = installed,
            second = latest
    )
}

val cache = mutableMapOf<String, Pair<PyPackageVersion?, LocalDateTime>>()

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
    val version = PyPackageVersionNormalizer
            .normalize(latestVersion) ?: return null
    cache[key] = version to LocalDateTime.now()
    return version
}

fun installPackage(project: Project, packageName: String,
                   requirement: PyRequirement,
                   onInstalled: ((PyPackage) -> Unit)?) {
    val installedPackage = getInstalledPackage(project, packageName)
    if (installedPackage?.matches(requirement) == true) {
        Notification("pip",
                "$packageName (${installedPackage.version})",
                "Successfully installed",
                NotificationType.INFORMATION).notify(project)
        if (onInstalled != null) {
            onInstalled(installedPackage)
        }
        return
    }

    val title = "Installing '${requirement.presentableText}'"

    val task = object : Task.Backgroundable(project, title) {
        override fun run(indicator: ProgressIndicator) {
            indicator.text = this.title
            indicator.isIndeterminate = true

            try {
                val packageManager = RequirementsPsiImplUtil.getPackageManager(project)

                if (packageManager != null) {
                    packageManager.install(requirement.presentableText)
                } else {
                    Notification("pip",
                            title,
                            "Package manager is not available",
                            NotificationType.ERROR).notify(project)
                    return
                }
                val pyPackage = getPackage(project, packageName)

                if (pyPackage == null) {
                    Notification("pip",
                            title,
                            "Failed. Not installed",
                            NotificationType.ERROR).notify(project)
                    return
                }

                Notification("pip",
                        "${pyPackage.name} (${pyPackage.version})",
                        "Successfully installed",
                        NotificationType.INFORMATION).notify(project)
                if (onInstalled != null) {
                    onInstalled(pyPackage)
                }
            } catch (e: PyExecutionException) {
                Notification(e.command,
                        e.stdout,
                        e.stderr,
                        NotificationType.ERROR).notify(project)
            }
        }
    }

    ProgressManager.getInstance().run(task)
}

fun resolveFile(filepath: String, base: VirtualFile): VirtualFile? {
    val target = File(filepath)
    return if (target.isAbsolute) {
        LocalFileSystem.getInstance().findFileByIoFile(target)
    } else {
        base.findFileByRelativePath(filepath)
    }
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


fun compareVersions(actual: PyPackageVersion?,
                    operation: String,
                    required: PyPackageVersion?): Boolean {
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

fun createNodeFromText(type: IElementType, text: String): ASTNode {
    val node = ASTFactory.leaf(type, text)
    CodeEditUtil.setNodeGenerated(node, true)
    return node
}
