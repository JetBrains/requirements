package ru.meanmail

import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.util.io.HttpRequests
import com.jetbrains.python.packaging.PyPackageVersion
import com.jetbrains.python.packaging.PyPackageVersionComparator
import com.jetbrains.python.packaging.PyPackageVersionNormalizer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.time.Duration
import java.time.Instant

const val PYPI_URL = "https://pypi.org"
val EXPIRATION_TIMEOUT: Duration = Duration.ofHours(1L)
val format = Json { ignoreUnknownKeys = true }
val cache = mutableMapOf<String, Pair<PackageInfo, Instant>>()

fun getUserAgent(): String {
    return ApplicationNamesInfo.getInstance().productName +
            "/" + ApplicationInfo.getInstance().fullVersion
}

@Serializable
data class FileInfo(
    val comment_text: String,
    val digests: Map<String, String>,
    val downloads: Int,
    val filename: String,
    val has_sig: Boolean,
    val md5_digest: String,
    val packagetype: String,
    val python_version: String,
    val size: Int,
    val upload_time_iso_8601: String,
    val url: String,
    val yanked: Boolean,
    val yanked_reason: String?,
)

@Serializable
data class PackageInfo(val releases: Map<String, List<FileInfo>>)

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

fun getVersionsList(packageName: String): List<PyPackageVersion> {
    val packageInfo = ApplicationManager.getApplication()
        .executeOnPooledThread<PackageInfo?> {
            return@executeOnPooledThread getPackageInfo(packageName)
        }.get() ?: return emptyList()

    return packageInfo.releases.keys
        .sortedWith(PyPackageVersionComparator.STR_COMPARATOR.reversed())
        .mapNotNull {
            PyPackageVersionNormalizer.normalize(it)
        }
}
