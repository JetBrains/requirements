package ru.meanmail.pypi.serializers

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val author: String? = null,
    val author_email: String? = null,
    val bugtrack_url: String? = null,
    val classifiers: List<String>? = null,
    val description: String? = null,
    val description_content_type: String? = null,
    val docs_url: String? = null,
    val download_url: String? = null,
    val downloads: Map<String, Int>? = null,
    val home_page: String? = null,
    val keywords: String? = null,
    val license: String? = null,
    val maintainer: String? = null,
    val maintainer_email: String? = null,
    val name: String? = null,
    val package_url: String? = null,
    val platform: String? = null,
    val project_url: String? = null,
    val project_urls: Map<String, String>? = null,
    val release_url: String? = null,
    val requires_dist: List<String>? = null,
    val requires_python: String? = null,
    val summary: String? = null,
    val version: String? = null,
    val yanked: Boolean = false,
    val yanked_reason: String? = null
)
