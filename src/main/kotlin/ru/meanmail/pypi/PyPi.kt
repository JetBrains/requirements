package ru.meanmail.pypi

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.util.io.HttpRequests
import com.jetbrains.python.packaging.PyPackageService
import com.jetbrains.python.packaging.PyPackageVersion
import com.jetbrains.python.packaging.PyPackageVersionComparator
import com.jetbrains.python.packaging.PyPackageVersionNormalizer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.meanmail.compareTo
import ru.meanmail.fileTypes.createVersionspec
import ru.meanmail.canonicalizeName
import ru.meanmail.pypi.serializers.FileInfo
import ru.meanmail.pypi.serializers.Info
import ru.meanmail.pypi.serializers.PackageInfo
import java.io.IOException
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Future
import javax.swing.text.MutableAttributeSet
import javax.swing.text.html.HTML
import javax.swing.text.html.HTMLEditorKit
import javax.swing.text.html.parser.ParserDelegator

const val PYPI_URL = "https://pypi.org"
val EXPIRATION_TIMEOUT: Duration = Duration.ofHours(1L)
val format = Json { ignoreUnknownKeys = true }
val cache = mutableMapOf<String, Pair<PackageInfo, Instant>>()


fun getExtraIndexes(): List<String> {
    val packageService = PyPackageService.getInstance()

    return packageService.additionalRepositories
}

fun loadPackageFromPyPi(packageName: String, version: PyPackageVersion? = null): PackageInfo {
    val url = if (version == null) {
        "$PYPI_URL/pypi/$packageName/json"
    } else {
        "$PYPI_URL/pypi/$packageName/${version.presentableText}/json"
    }

    return HttpRequests.request(url)
        .productNameAsUserAgent().connect {
            return@connect format.decodeFromString<PackageInfo>(it.readString())
        }
}

fun mergeReleases(listOfReleases: List<Map<String, List<FileInfo>>>): Map<String, List<FileInfo>> {
    if (listOfReleases.isEmpty()) {
        return mapOf()
    }
    return listOfReleases.fold(mutableMapOf()) { result, releases ->
        releases.forEach { (key, value) ->
            result.merge(key, value) { info1, info2 -> info1 + info2 }
        }
        return@fold result
    }
}

fun mergePackageInfos(infos: List<PackageInfo>): PackageInfo {
    if (infos.size == 1) {
        return infos[0]
    }
    return PackageInfo(
        infos[0].info,
        mergeReleases(infos.map { it.releases })
    )
}

fun loadPackageFromSimple(indexUrl: String, packageName: String): PackageInfo {
    return HttpRequests.request("$indexUrl$packageName")
        .productNameAsUserAgent().connect {
            val releases: MutableMap<String, MutableList<FileInfo>> = mutableMapOf()
            ParserDelegator().parse(it.reader, object : HTMLEditorKit.ParserCallback() {
                var isATag = false
                var href: String? = null

                override fun handleStartTag(tag: HTML.Tag, attrs: MutableAttributeSet, pos: Int) {
                    if (HTML.Tag.A == tag) {
                        isATag = true
                        href = attrs.getAttribute(HTML.Attribute.HREF).toString()
                    }
                }

                override fun handleText(data: CharArray?, pos: Int) {
                    if (isATag) {
                        val fileName = String(data!!)
                        val packageFilePattern = ("^$packageName-(?<version>.*?)((.tar.gz|.zip)|-" +
                                "(?<pyversion>py2|py3|py2.py3)-([^-]+)-([^-]+).whl)\$").toRegex()
                        val matchResult = packageFilePattern.find(fileName)
                        if (matchResult != null) {
                            val version = matchResult.groups["version"]?.value!!
                            val pythonVersion = matchResult.groups["pyversion"]?.value
                            releases.getOrPut(version) { mutableListOf() }.add(
                                FileInfo(
                                    filename = fileName,
                                    url = href ?: "",
                                    requires_python = when (pythonVersion) {
                                        "py2" -> {
                                            "~=2.0"
                                        }
                                        "py3" -> {
                                            "~=3.0"
                                        }
                                        "py2.py3" -> {
                                            "~=2.0,~=3.0"
                                        }
                                        else -> {
                                            null
                                        }
                                    }
                                )
                            )
                        }
                    }
                }

                override fun handleEndTag(tag: HTML.Tag, pos: Int) {
                    if (HTML.Tag.A == tag) {
                        isATag = false
                        href = null
                    }
                }
            }, true)
            return@connect PackageInfo(
                Info(),
                releases
            )
        }
}

fun loadPackage(packageName: String, version: PyPackageVersion? = null): PackageInfo {
    val packageInfos: MutableList<PackageInfo> = mutableListOf()
    val packageService = PyPackageService.getInstance()
    val extraIndexes = getExtraIndexes()
    if (!packageService.PYPI_REMOVED || extraIndexes.isEmpty()) {
        packageInfos.add(loadPackageFromPyPi(packageName, version))
    }
    for (extraIndexUrl in extraIndexes) {
        packageInfos.add(loadPackageFromSimple(extraIndexUrl, packageName))
    }
    return mergePackageInfos(packageInfos)
}

fun getPackageInfo(packageName: String, version: PyPackageVersion? = null): PackageInfo? {
    val canonizedPackageName = canonicalizeName(packageName)
    val key = if (version != null)
        "$canonizedPackageName@${version.presentableText}"
    else canonizedPackageName

    var (packageInfo, instant) = cache.getOrDefault(
        key, null to null
    )
    if (packageInfo != null && instant?.isAfter(Instant.now()) == true) {
        return packageInfo
    }

    try {
        packageInfo = loadPackage(packageName, version)
        cache["$canonizedPackageName@${packageInfo.info.version}"] = packageInfo to Instant.now() + EXPIRATION_TIMEOUT
    } catch (var3: IOException) {
        return null
    }

    return packageInfo
}

val PYTHON_VERSION_PATTERN = "Python ([\\d.]+).*".toRegex()

fun getPythonVersion(sdk: Sdk): String? {
    val versionString = sdk.versionString ?: return null
    val matchResult = PYTHON_VERSION_PATTERN.find(versionString) ?: return null
    return matchResult.groups[1]?.value
}

fun getVersionsList(project: Project, sdk: Sdk, packageName: String): List<PyPackageVersion> {
    val pythonVersion = getPythonVersion(sdk)
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
            it.value.any { fileInfo ->
                if (fileInfo.requires_python == null || pythonVersion == null) {
                    return@any true
                }
                var isActual = false
                ProgressManager.getInstance().runInReadActionWithWriteActionPriority({
                    isActual = createVersionspec(project, fileInfo.requires_python)
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

fun getVersionsAsync(project: Project, sdk: Sdk, packageName: String): Future<List<PyPackageVersion>> {
    return ApplicationManager.getApplication()
        .executeOnPooledThread<List<PyPackageVersion>> {
            return@executeOnPooledThread getVersionsList(project, sdk, packageName)
        }
}
