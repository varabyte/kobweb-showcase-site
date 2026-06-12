package com.varabyte.kobweb.showcase.site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.BackgroundRepeat
import com.varabyte.kobweb.compose.css.BackgroundSize
import com.varabyte.kobweb.compose.css.CSSPosition
import com.varabyte.kobweb.compose.css.Edge
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.extendedBy
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
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

private val tagBackground by StyleVariable<CSSColorValue>()
private val tagColor by StyleVariable<CSSColorValue>()
private val tagBorderColor by StyleVariable<CSSColorValue>()

val BasicTagStyle = CssStyle.base {
    Modifier
        .backgroundColor(tagBackground.value())
        .color(tagColor.value())
        .fontSize(0.75.cssRem)
        .fontWeight(700)
        .fontStyle(FontStyle.Italic)
        .borderRadius(2.cssRem)
        .border(1.px, LineStyle.Solid, tagBorderColor.value())
        .padding(leftRight = 0.6.cssRem, topBottom = 0.2.cssRem)
        .margin(right = 0.4.cssRem, bottom = 0.4.cssRem)
        .userSelect(UserSelect.None)
}

/** A tag that sticks out to grab the user's attention as something special. */
val OfficialTagStyle = BasicTagStyle.extendedBy {
    base {
        val palette = colorMode.toSitePalette()
        val shadowAlpha = if(colorMode.isLight) 0.6f else 0.4f
        Modifier
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

@Composable
internal fun TagItem(tag: String) {
    val colorMode = ColorMode.current
    val palette = colorMode.toSitePalette()
    val tagStyleModifier = if (tag != "Varabyte-Official") {
        BasicTagStyle.toModifier()
            .setVariable(tagBackground, palette.accent.toRgb().copyf(alpha = if (colorMode.isLight) 0.35f else 0.18f))
            .setVariable(tagColor, palette.textPrimary.toRgb().copyf(alpha = 0.7f))
            .setVariable(tagBorderColor, palette.accent.toRgb().copyf(alpha = if (colorMode.isLight) 0.8f else 0.4f))
    } else {
        OfficialTagStyle.toModifier()
            .setVariable(tagBackground, Color.rgb(0x161B22))
            .setVariable(tagColor, Colors.White)
            .setVariable(tagBorderColor, Colors.White.copyf(alpha = 0.4f))
    }
    Box(tagStyleModifier) {
        Text(tag)
    }
}
