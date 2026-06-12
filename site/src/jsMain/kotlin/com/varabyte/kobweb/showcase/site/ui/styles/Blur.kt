package com.varabyte.kobweb.showcase.site.ui.styles

import com.varabyte.kobweb.compose.css.CSSLengthNumericValue
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.css.functions.url
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backdropFilter
import com.varabyte.kobweb.compose.ui.modifiers.background
import org.jetbrains.compose.web.css.px

// Base64 string of the SVG noise filter to prevent CSS blur banding
private const val NOISE_B64 = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMDAiIGhlaWdodD0iMjAwIj4KICA8ZmlsdGVyIGlkPSJub2lzZUZpbHRlciI+CiAgICA8ZmVUdXJidWxlbmNlIHR5cGU9ImZyYWN0YWxOb2lzZSIgYmFzZUZyZXF1ZW5jeT0iMC42NSIgbnVtT2N0YXZlcz0iMyIgc3RpdGNoVGlsZXM9InN0aXRjaCIvPgogIDwvZmlsdGVyPgogIDxyZWN0IHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiIGZpbHRlcj0idXJsKCNub2lzZUZpbHRlcikiIG9wYWNpdHk9IjAuMTIiLz4KPC9zdmc+"

/**
 * Applies a frosted glass effect using backdrop-filter and an SVG noise overlay
 * to prevent banding artifacts in Chromium and WebKit browsers.
 */
fun Modifier.frostedGlass(
    backgroundColor: Color,
    blurRadius: CSSLengthNumericValue = 12.px
) = this
    .background {
        image(url(NOISE_B64))
        color(backgroundColor)
    }
    .backdropFilter(blur(blurRadius))
