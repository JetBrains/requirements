package ru.meanmail.pypi.serializers

import kotlinx.serialization.Serializable

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
    val requires_python: String?,
    val size: Int,
    val upload_time_iso_8601: String,
    val url: String,
    val yanked: Boolean,
    val yanked_reason: String?,
)
