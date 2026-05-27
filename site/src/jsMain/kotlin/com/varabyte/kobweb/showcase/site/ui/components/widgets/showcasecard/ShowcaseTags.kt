package com.varabyte.kobweb.showcase.site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Text

// Special styling for the Varabyte-Official tag
// Using some hand-picked and tested numbers for timings
val GlintAnim = Keyframes {
    0.percent { Modifier.styleModifier { property("background-position", "90% center") } }
    40.percent { Modifier.styleModifier { property("background-position", "10% center") } }
    100.percent { Modifier.styleModifier { property("background-position", "10% center") } }
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
            .styleModifier {
                property(
                    "background-image",
                    """linear-gradient(110deg, 
                        |transparent 40%, 
                        |rgba(255, 255, 255, 0.05) 45%, 
                        |rgba(255, 255, 255, 0.6) 50%, 
                        |rgba(255, 255, 255, 0.05) 55%, 
                        |transparent 60%)""".trimMargin()
                )
                property("background-size", "300% 100%")
                property("background-repeat", "no-repeat")
            }.animation(
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
