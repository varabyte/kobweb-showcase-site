package com.varabyte.kobweb_showcase_site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.dom.svg.Line
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Polyline
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.*


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
            .alignItems(AlignItems.Center)
            .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.Center)
            .textDecorationLine(TextDecorationLine.None)
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
fun ExternalLinkIcon(
    modifier: Modifier = Modifier,
    color: Color = ColorMode.current.toSitePalette().primary,
    iconSize: CSSSizeValue<CSSUnit.rem> = 1.2.cssRem
) {
    com.varabyte.kobweb.compose.dom.svg.Svg(
        attrs = modifier
            .width(iconSize)
            .height(iconSize)
            .color(color)
            .toAttrs {
                attr("viewBox", "0 0 24 24")
                attr("fill", "none")
                attr("stroke", "currentColor")
                attr("stroke-width", "2.5")
                attr("stroke-linecap", "round")
                attr("stroke-linejoin", "round")
            }
    ) {
        Path(attrs = Modifier.toAttrs {
            attr("d", "M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6")
        })
        Polyline(attrs = Modifier.toAttrs {
            attr("points", "15 3 21 3 21 9")
        })
        Line(attrs = Modifier.toAttrs {
            attr("x1", "10")
            attr("y1", "14")
            attr("x2", "21")
            attr("y2", "3")
        })
    }
}
