package com.varabyte.kobweb.showcase.site.ui.components.widgets.showcasecard

import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.showcase.site.ui.components.KobwebJustifyContent
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems.Companion.Center

val CardImageWrapperStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            .height(200.px)
            .position(Position.Relative)
            .backgroundColor(palette.surfaceHover)
            .overflow(Overflow.Hidden)
            .display(DisplayStyle.Flex)
            .justifyContent(KobwebJustifyContent.Center)
            .alignItems(Center)
    }
}

val CardImageStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .height(100.percent)
            .objectFit(ObjectFit.Cover)
            .styleModifier { property("object-position", "top center") }
            .opacity(0)
            .transition(Transition.of("opacity", 0.4.s, TransitionTimingFunction.EaseIn))
    }
}