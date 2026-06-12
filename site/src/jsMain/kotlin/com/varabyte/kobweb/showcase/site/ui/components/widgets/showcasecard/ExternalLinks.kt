package com.varabyte.kobweb.showcase.site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.dom.svg.Line
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Polyline
import com.varabyte.kobweb.compose.dom.svg.SVGFillType
import com.varabyte.kobweb.compose.dom.svg.SVGStrokeLineCap
import com.varabyte.kobweb.compose.dom.svg.SVGStrokeLineJoin
import com.varabyte.kobweb.compose.dom.svg.SVGStrokeType
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.showcase.site.ui.components.KobwebJustifyContent
import com.varabyte.kobweb.showcase.site.ui.styles.frostedGlass
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.*

val ExternalLinkBtnStyle = CssStyle {
    base {
        Modifier
            .position(Position.Absolute)
            .top(0.5.cssRem)
            .right(0.5.cssRem)
            .width(2.5.cssRem)
            .height(2.5.cssRem)
            .borderRadius(0.375.cssRem)
            .frostedGlass(Colors.Black.copyf(alpha = 0.45f), 4.px)
            .border(1.px, LineStyle.Solid, Colors.White.copyf(alpha = 0.25f))
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .justifyContent(KobwebJustifyContent.Center)
            .textDecorationLine(TextDecorationLine.None)
            .cursor(Cursor.Pointer)
            .zIndex(2)
            .transition(
                Transition.of("background-color", 0.18.s, TransitionTimingFunction.EaseOut),
                Transition.of("border-color", 0.18.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        Modifier
            .backgroundColor(Colors.Black.copyf(alpha = 0.85f))
            .border(1.px, LineStyle.Solid, Colors.White.copyf(alpha = 0.6f))
    }
}

@Composable
fun ExternalLinkIcon(
    modifier: Modifier = Modifier,
    color: Color = Colors.White,
    iconSize: CSSSizeValue<CSSUnit.rem> = 1.2.cssRem
) {
    Svg(
        attrs = modifier
            .width(iconSize)
            .height(iconSize)
            .color(color)
            .toAttrs {
                viewBox(0, 0, 24, 24)
                fill(SVGFillType.None)
                stroke(SVGStrokeType.CurrentColor)
                strokeWidth(2.5f)
                strokeLineCap(SVGStrokeLineCap.Round)
                strokeLineJoin(SVGStrokeLineJoin.Round)
            }
    ) {
        Path(attrs = Modifier.toAttrs {
            d("M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6")
        })
        Polyline(attrs = Modifier.toAttrs {
            points(15 to 3, 21 to 3, 21 to 9)
        })
        Line(attrs = Modifier.toAttrs {
            x1(10)
            y1(14)
            x2(21)
            y2(3)
        })
    }
}
