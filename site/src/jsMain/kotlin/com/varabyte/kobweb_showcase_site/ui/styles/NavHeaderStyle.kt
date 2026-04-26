package com.varabyte.kobweb_showcase_site.ui.styles

import com.varabyte.kobweb.compose.css.BackdropFilter
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.*

val NavHeaderStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 0.75.cssRem, leftRight = 1.5.cssRem)
        .position(Position.Sticky)
        .top(0.px)
        .zIndex(10)
        .backgroundColor(palette.nearBackground)
        .borderBottom(1.px, LineStyle.Solid, palette.border)
}
