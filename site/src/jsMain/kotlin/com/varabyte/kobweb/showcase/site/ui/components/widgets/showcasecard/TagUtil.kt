package com.varabyte.kobweb.showcase.site.ui.components.widgets.showcasecard

/**
 * Normalizes and sorts tags so that "Varabyte-Official" is always first.
 */
internal fun List<String>.normalizeAndSortTags(): List<String> {
    val normalized = this
        .flatMap { it.split(",") }
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinct()

    return normalized.sortedByDescending { it == "Varabyte-Official" }
}
