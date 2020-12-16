package ru.meanmail.pypi.serializers

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val author: String?,
    val author_email: String?,
    val bugtrack_url: String?,
    val classifiers: List<String>?,
    val description: String?,
    val description_content_type: String?,
    val docs_url: String?,
    val download_url: String?,
    val downloads: Map<String, Int>?,
    val home_page: String?,
    val keywords: String?,
    val license: String?,
    val maintainer: String?,
    val maintainer_email: String?,
    val name: String?,
    val package_url: String?,
    val platform: String?,
    val project_url: String?,
    val project_urls: Map<String, String>?,
    val release_url: String?,
    val requires_dist: List<String>?,
    val requires_python: String?,
    val summary: String?,
    val version: String?,
    val yanked: Boolean,
    val yanked_reason: String?
)
