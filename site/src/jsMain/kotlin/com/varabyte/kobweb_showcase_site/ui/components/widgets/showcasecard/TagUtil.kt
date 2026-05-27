package com.varabyte.kobweb_showcase_site.ui.components.widgets.showcasecard

/**
 * Normalizes and sorts tags so that "Varabyte-Official" is always first.
 */
internal fun List<String>.normalizeAndSortTags(): List<String> {
    val normalized = this
        .flatMap { it.split(",") }
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinct() // Prevent duplicates

    return normalized.sortedByDescending { it == "Varabyte-Official" }
}
