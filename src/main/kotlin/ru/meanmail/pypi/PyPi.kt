package ru.meanmail.pypi

import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.util.io.HttpRequests
import com.jetbrains.python.packaging.PyPackageVersion
import com.jetbrains.python.packaging.PyPackageVersionComparator
import com.jetbrains.python.packaging.PyPackageVersionNormalizer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.meanmail.compareTo
import ru.meanmail.createVersionspec
import ru.meanmail.formatPackageName
import ru.meanmail.getSdk
import ru.meanmail.pypi.serializers.PackageInfo
import java.io.IOException
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Future

const val PYPI_URL = "https://pypi.org"
val EXPIRATION_TIMEOUT: Duration = Duration.ofHours(1L)
val format = Json { ignoreUnknownKeys = true }
val cache = mutableMapOf<String, Pair<PackageInfo, Instant>>()

fun getUserAgent(): String {
    return ApplicationNamesInfo.getInstance().productName +
            "/" + ApplicationInfo.getInstance().fullVersion
}

fun loadPackage(packageName: String): PackageInfo {
    return HttpRequests.request("$PYPI_URL/pypi/$packageName/json")
        .userAgent(getUserAgent()).connect {
            return@connect format.decodeFromString<PackageInfo>(it.readString())
        }
}

fun getPackageInfo(packageName: String): PackageInfo? {
    val formattedPackageName = formatPackageName(packageName)

    var (packageInfo, instant) = cache.getOrDefault(
        formattedPackageName, null to null
    )
    if (packageInfo != null && instant?.isAfter(Instant.now()) == true) {
        return packageInfo
    }

    try {
        packageInfo = loadPackage(packageName)
        cache[formattedPackageName] = packageInfo to Instant.now() + EXPIRATION_TIMEOUT
    } catch (var3: IOException) {
        return null
    }

    return packageInfo
}

val PYTHON_VERSION_PATTERN = "Python (.*)".toRegex()

fun getPythonVersion(project: Project): String? {
    val sdk = getSdk(project) ?: return null
    val versionString = sdk.versionString ?: return null
    val matchResult = PYTHON_VERSION_PATTERN.find(versionString) ?: return null
    return matchResult.groups[1]?.value
}

fun getVersionsList(project: Project, packageName: String): List<PyPackageVersion> {
    val pythonVersion = getPythonVersion(project)
    val packageInfo = ApplicationManager.getApplication()
        .executeOnPooledThread<PackageInfo?> {
            return@executeOnPooledThread getPackageInfo(packageName)
        }.get() ?: return emptyList()

    val infoVersion = packageInfo.info.version

    val currentVersion = if (infoVersion != null) {
        PyPackageVersionNormalizer.normalize(infoVersion)
    } else {
        null
    }

    return packageInfo.releases
        .filter {
            it.value.any {
                if (it.requires_python == null || pythonVersion == null) {
                    return@any true
                }
                var isActual = false
                ProgressManager.getInstance().runInReadActionWithWriteActionPriority({
                    isActual = createVersionspec(project, it.requires_python)
                        ?.isActual(pythonVersion) ?: true
                }, null)
                return@any isActual
            }
        }
        .mapNotNull {
            PyPackageVersionNormalizer.normalize(it.key)
        }
        .filter {
            currentVersion != null && it <= currentVersion
        }
        .sortedWith(PyPackageVersionComparator::compare)
        .reversed()
}

fun getVersionsAsync(project: Project, packageName: String): Future<List<PyPackageVersion>> {
    return ApplicationManager.getApplication()
        .executeOnPooledThread<List<PyPackageVersion>> {
            return@executeOnPooledThread getVersionsList(project, packageName)
        }
}
