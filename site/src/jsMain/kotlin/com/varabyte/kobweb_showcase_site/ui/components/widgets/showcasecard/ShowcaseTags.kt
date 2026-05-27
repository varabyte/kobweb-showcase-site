package com.varabyte.kobweb_showcase_site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb_showcase_site.ui.theme.SiteColors
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Text

// Special styling for the Varabyte-Official tag
val GlintAnim = Keyframes {
    0.percent { Modifier.styleModifier { property("background-position", "-200% center") } }
    100.percent { Modifier.styleModifier { property("background-position", "200% center") } }
}

val OfficialTagStyle = CssStyle {
    base {
        Modifier.backgroundColor(Color.rgb(0x161B22)) // Dark base for contrast
            .color(Colors.White).borderRadius(2.cssRem).padding(leftRight = 0.6.cssRem, topBottom = 0.2.cssRem)
            .margin(right = 0.4.cssRem, bottom = 0.4.cssRem).fontSize(0.75.cssRem).fontWeight(700)
            .border(1.px, LineStyle.Solid, Colors.White.copyf(alpha = 0.3f))
            .boxShadow(0.px, 0.px, 8.px, color = Colors.White.copyf(alpha = 0.5f)).styleModifier {
                property(
                    "background-image",
                    "linear-gradient(110deg, transparent 40%, rgba(255, 255, 255, 0.8) 50%, transparent 60%)"
                )
                property("background-size", "200% auto")
            }.animation(
                GlintAnim.toAnimation(
                    duration = 2.5.s,
                    timingFunction = AnimationTimingFunction.Linear,
                    iterationCount = AnimationIterationCount.Infinite
                )
            )
    }
}

val CustomTagStyle = CssStyle {
    base {
        Modifier.backgroundColor(SiteColors.KobwebGold.toRgb().copyf(alpha = 0.15f)).color(SiteColors.KobwebGold)
            .borderRadius(2.cssRem).padding(leftRight = 0.6.cssRem, topBottom = 0.2.cssRem)
            .margin(right = 0.4.cssRem, bottom = 0.4.cssRem).fontSize(0.75.cssRem).fontWeight(500)
            .border(1.px, LineStyle.Solid, SiteColors.KobwebGold.toRgb().copyf(alpha = 0.3f))
    }
}

@Composable
internal fun TagItem(tag: String) {
    Box((if (tag == "Varabyte-Official") OfficialTagStyle else CustomTagStyle).toModifier()) {
        Text(tag)
    }
}
