package ru.meanmail.pypi.serializers

import kotlinx.serialization.Serializable

@Serializable
data class PackageInfo(
    val info: Info,
    val releases: Map<String, List<FileInfo>>
)
