package ru.meanmail

import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import ru.meanmail.psi.RequirementsPsiImplUtil
import java.io.IOException
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

const val OS_NAME = "os_name"
const val SYS_PLATFORM = "sys_platform"
const val PLATFORM_MACHINE = "platform_machine"
const val PLATFORM_PYTHON_IMPLEMENTATION = "platform_python_implementation"
const val PLATFORM_RELEASE = "platform_release"
const val PLATFORM_SYSTEM = "platform_system"
const val PLATFORM_VERSION = "platform_version"
const val PYTHON_VERSION = "python_version"
const val PYTHON_FULL_VERSION = "python_full_version"
const val IMPLEMENTATION_NAME = "implementation_name"
const val IMPLEMENTATION_VERSION = "implementation_version"
const val EXTRA = "extra"

val VERSION_VARIABLES = listOf(
        IMPLEMENTATION_VERSION,
        PLATFORM_RELEASE,
        PYTHON_FULL_VERSION,
        PYTHON_VERSION
)

val markersCache = mutableMapOf<String, Pair<Map<String, String?>, LocalDateTime>>()

fun getMarkers(project: Project): Map<String, String?> {
    val sdk = RequirementsPsiImplUtil.getSdk(project) ?: return emptyMap()

    val cached = markersCache[sdk.name]
    if (cached != null) {
        val actual = cached.second.plusDays(1).isAfter(LocalDateTime.now())
        if (actual) {
            return cached.first
        }
    }
    val scriptResource = RequirementsPsiImplUtil::class.java
            .getResource("/python_info.py")

    val script = createTempFile()
    script.writeText(scriptResource.readText())

    val result = execPython(sdk, script.path) ?: return emptyMap()

    val values = result.lines().map {
        val items = it.split(": ".toRegex(), limit = 2)
        val key = items[0]
        val value = items.getOrNull(1)

        return@map key to value
    }.toTypedArray()

    val markers = mapOf(*values)

    markersCache[sdk.name] = markers to LocalDateTime.now()

    return markers
}

fun execPython(sdk: Sdk, path: String): String? {
    try {
        val parts = listOf(sdk.homePath, path)
        val proc = ProcessBuilder(*parts.toTypedArray())
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

        proc.waitFor(5, TimeUnit.SECONDS)
        val errors = proc.errorStream.bufferedReader().readText()
        if (errors.isNotEmpty()) {
            throw IOException(errors)
        }
        return proc.inputStream.bufferedReader().readText()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}
