package com.varabyte.kobweb.showcase.site.model

import kotlinx.serialization.Serializable

@Serializable
data class ShowcaseSite(
    val issueNumber: Int,
    val name: String,
    val url: String,
    val imageUrl: String,
    val description: String,
    val siteType: String = "",
    val tags: List<String> = emptyList(),
)

@Serializable
data class ShowcaseSiteRequest(
    /** A URL to a json file containing metadata for all Kobweb demo sites. */
    val dataUrl: String
)

@Serializable
data class ShowcaseSiteResponse(
    val sites: List<ShowcaseSite>
)