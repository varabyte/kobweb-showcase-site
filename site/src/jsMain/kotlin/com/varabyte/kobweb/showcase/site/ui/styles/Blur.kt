package com.varabyte.kobweb.showcase.site.ui.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import org.jetbrains.compose.web.css.CSSLengthOrPercentageValue
import org.jetbrains.compose.web.css.px


// Base64 string of the SVG noise filter to prevent CSS blur banding
private const val NOISE_B64 = "url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMDAiIGhlaWdodD0iMjAwIj4KICA8ZmlsdGVyIGlkPSJub2lzZUZpbHRlciI+CiAgICA8ZmVUdXJidWxlbmNlIHR5cGU9ImZyYWN0YWxOb2lzZSIgYmFzZUZyZXF1ZW5jeT0iMC42NSIgbnVtT2N0YXZlcz0iMyIgc3RpdGNoVGlsZXM9InN0aXRjaCIvPgogIDwvZmlsdGVyPgogIDxyZWN0IHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiIGZpbHRlcj0idXJsKCNub2lzZUZpbHRlcikiIG9wYWNpdHk9IjAuMTIiLz4KPC9zdmc+')"

/**
 * Applies a frosted glass effect using backdrop-filter and an SVG noise overlay
 * to prevent banding artifacts in Chromium and WebKit browsers.
 */
fun Modifier.frostedGlass(
    backgroundColor: Color,
    blurRadius: CSSLengthOrPercentageValue = 12.px
): Modifier = this.styleModifier {
    property("background", "linear-gradient(rgba(0,0,0,0), rgba(0,0,0,0)), ${NOISE_B64}")
    property("background-color", backgroundColor.toString())
    property("backdrop-filter", "blur($blurRadius)")
    property("-webkit-backdrop-filter", "blur($blurRadius)")
}