package com.varabyte.kobweb.showcase.site.ui.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.*

val FooterStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.nearBackground)
        .borderTop(1.px, LineStyle.Solid, palette.border)
        .padding(topBottom = 1.5.cssRem, leftRight = 2.cssRem)
}
