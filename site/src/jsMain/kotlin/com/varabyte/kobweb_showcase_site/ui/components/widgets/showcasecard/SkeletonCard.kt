package com.varabyte.kobweb_showcase_site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color.Companion.rgb
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb_showcase_site.ui.theme.SiteColors
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

// Shimmer animation for when card content is loading
val ShimmerAnim = Keyframes {
    0.percent { Modifier.opacity(1f) }
    50.percent { Modifier.opacity(0.45f) }
    100.percent { Modifier.opacity(1f) }
}

val SkeletonBaseStyle = CssStyle.base {
    val base = if (colorMode == ColorMode.DARK) SiteColors.SkeletonBase else rgb(0xE0E4EC)
    Modifier
        .backgroundColor(base)
        .borderRadius(0.375.cssRem)
        .animation(
            ShimmerAnim.toAnimation(
                duration = 1.5.s,
                timingFunction = AnimationTimingFunction.EaseInOut,
                iterationCount = AnimationIterationCount.Infinite,
            )
        )
}

@Composable
private fun SkeletonBox(modifier: Modifier) {
    Div(SkeletonBaseStyle.toModifier().then(modifier).toAttrs()) {}
}

@Composable
fun SkeletonCard() {
    val palette = ColorMode.current.toSitePalette()
    Column(
        Modifier
            .fillMaxWidth()
            .backgroundColor(palette.surface)
            .border(1.px, LineStyle.Solid, palette.border)
            .borderRadius(0.75.cssRem)
            .overflow(Overflow.Hidden)
    ) {
        // Image placeholder
        SkeletonBox(Modifier.fillMaxWidth().height(180.px).borderRadius(0.px))
        Column(Modifier.padding(1.25.cssRem).gap(0.75.cssRem)) {
            SkeletonBox(Modifier.width(60.percent).height(1.1.cssRem))   // title
            SkeletonBox(Modifier.fillMaxWidth().height(0.85.cssRem))     // desc line 1
            SkeletonBox(Modifier.width(80.percent).height(0.85.cssRem))  // desc line 2
            Row(Modifier.margin(top = 0.5.cssRem).gap(0.4.cssRem)) {
                repeat(3) {
                    SkeletonBox(Modifier.width(3.5.cssRem).height(1.3.cssRem).borderRadius(2.cssRem))
                }
            }
        }
    }
}
