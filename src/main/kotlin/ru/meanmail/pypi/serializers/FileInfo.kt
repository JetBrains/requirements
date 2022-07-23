package ru.meanmail.pypi.serializers

import kotlinx.serialization.Serializable

@Serializable
data class FileInfo(
    val comment_text: String? = null,
    val digests: Map<String, String> = mapOf(),
    val downloads: Int = -1,
    val filename: String,
    val has_sig: Boolean = false,
    val md5_digest: String = "",
    val packagetype: String = "",
    val python_version: String = "",
    val requires_python: String? = null,
    val size: Int = 0,
    val upload_time_iso_8601: String = "1970-01-01T00:00:00.000000Z",
    val url: String,
    val yanked: Boolean = false,
    val yanked_reason: String? = null,
)
