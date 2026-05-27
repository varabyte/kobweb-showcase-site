package com.varabyte.kobweb_showcase_site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems.Companion.Center
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text


val ExternalLinkBtnStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .position(Position.Absolute)
            .top(0.5.cssRem)
            .right(0.5.cssRem)
            .width(2.5.cssRem)
            .height(2.5.cssRem)
            .borderRadius(0.375.cssRem)
            .backgroundColor(palette.surface.toRgb().copyf(alpha = 0.5f))
            .border(1.px, LineStyle.Solid, palette.border)
            .display(DisplayStyle.Flex)
            .alignItems(Center)
            .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.Center)
            .cursor(Cursor.Pointer)
            .zIndex(2)
            .transition(
                Transition.of("background-color", 0.18.s, TransitionTimingFunction.EaseOut),
                Transition.of("border-color", 0.18.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.primary.toRgb().copyf(alpha = 0.15f))
            .border(1.px, LineStyle.Solid, palette.primary)
    }
}


@Composable
internal fun ExternalLinkIcon(iconSize: CSSSizeValue<CSSUnit.rem> = 4.cssRem) {
    Span(
        attrs = Modifier
            .fontSize(iconSize)
            .lineHeight(1.0)
            .color(Color.rgb(0x00FF87))
            .toAttrs()
    ) {
        Text("↗")
    }
}
