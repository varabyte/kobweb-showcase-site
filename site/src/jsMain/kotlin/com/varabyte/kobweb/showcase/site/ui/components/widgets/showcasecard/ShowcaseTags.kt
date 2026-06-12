package com.varabyte.kobweb.showcase.site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.BackgroundRepeat
import com.varabyte.kobweb.compose.css.BackgroundSize
import com.varabyte.kobweb.compose.css.CSSPosition
import com.varabyte.kobweb.compose.css.Edge
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Text

// Special styling for the Varabyte-Official tag
// Using some hand-picked and tested numbers for timings
val GlintAnim = Keyframes {
    val startPos = Modifier.background { position(CSSPosition(90.percent, Edge.CenterY)) }
    val endPos = Modifier.background { position(CSSPosition(10.percent, Edge.CenterY)) }
    0.percent { startPos }
    40.percent { endPos }
    100.percent { endPos }
}

val OfficialTagStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        val shadowAlpha = if(colorMode.isLight) 0.6f else 0.4f
        Modifier.backgroundColor(Color.rgb(0x161B22))
            .color(Colors.White)
            .borderRadius(2.cssRem)
            .padding(leftRight = 0.6.cssRem, topBottom = 0.2.cssRem)
            .margin(right = 0.4.cssRem, bottom = 0.4.cssRem)
            .fontSize(0.75.cssRem)
            .fontWeight(700)
            .fontStyle(FontStyle.Italic)
            .border(1.px, LineStyle.Solid, Colors.White.copyf(alpha = 0.4f))
            .boxShadow(0.px, 0.px, 12.px, color = palette.primary.toRgb().copyf(alpha = shadowAlpha))
            .background {
                image(
                    linearGradient(110.deg) {
                        add(Colors.Transparent, 40.percent)
                        add(Colors.White.copyf(alpha = 0.05f), 45.percent)
                        add(Colors.White.copyf(alpha = 0.6f), 50.percent)
                        add(Colors.White.copyf(alpha = 0.05f), 55.percent)
                        add(Colors.Transparent, 60.percent)
                    }
                )
                size(BackgroundSize.of(300.percent, 100.percent))
                repeat(BackgroundRepeat.NoRepeat)
            }
    }
    cssRule(CSSMediaQuery.Raw("(prefers-reduced-motion: no-preference)")) {
        Modifier.animation(
            GlintAnim.toAnimation(
                duration = 10.s,
                timingFunction = AnimationTimingFunction.Linear,
                iterationCount = AnimationIterationCount.Infinite
            )
        )
    }
}

val CustomTagStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        val bgAlpha = if (colorMode.isLight) 0.35f else 0.18f
        val borderAlpha = if (colorMode.isLight) 0.8f else 0.4f
        Modifier
            .backgroundColor(palette.accent.toRgb().copyf(alpha = bgAlpha))
            .color(palette.textPrimary.toRgb().copyf(alpha = 0.7f))
            .borderRadius(2.cssRem)
            .padding(leftRight = 0.6.cssRem, topBottom = 0.2.cssRem)
            .margin(right = 0.4.cssRem, bottom = 0.4.cssRem)
            .fontSize(0.75.cssRem)
            .fontWeight(700)
            .fontStyle(FontStyle.Italic)
            .border(1.px, LineStyle.Solid, palette.accent.toRgb().copyf(alpha = borderAlpha))
    }
}

@Composable
internal fun TagItem(tag: String) {
    Box((if (tag == "Varabyte-Official") OfficialTagStyle else CustomTagStyle).toModifier()) {
        Text(tag)
    }
}
