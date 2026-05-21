package com.varabyte.kobweb_showcase_site.ui.styles

import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import com.varabyte.kobweb.silk.theme.modifyStyleBase
import org.jetbrains.compose.web.css.*

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyle("html") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("no-preference"))) {
            Modifier.scrollBehavior(ScrollBehavior.Smooth)
        }
    }

    // min-height:100% on both so things grow with content — no hard height cap at viewport
    ctx.stylesheet.registerStyleBase("html, body") {
        Modifier
            .minHeight(100.percent)
            .margin(0.px)
            .padding(0.px)
    }

    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily("Inter", "system-ui", "-apple-system", "sans-serif")
            .fontSize(16.px)
            .lineHeight(1.6)
    }

    ctx.stylesheet.registerStyleBase(":root") {
        Modifier
    }

    ctx.theme.modifyStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

val HeadlineTextStyle = CssStyle.base {
    Modifier
        .fontFamily("Inter", "system-ui", "sans-serif")
        .fontWeight(700)
        .fontSize(2.5.cssRem)
        .lineHeight(1.2)
        .textAlign(TextAlign.Start)
}

val SubheadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(1.cssRem)
        .lineHeight(1.6)
        .textAlign(TextAlign.Start)
        .color(colorMode.toPalette().color.toRgb().copyf(alpha = 0.7f))
}

val CircleButtonVariant = ButtonStyle.addVariantBase {
    Modifier
        .padding(0.px)
        .borderRadius(50.percent)
        .width(2.75.cssRem)
        .height(2.75.cssRem)
}

val UncoloredButtonVariant = ButtonStyle.addVariantBase {
    Modifier.setVariable(ButtonVars.BackgroundDefaultColor, Colors.Transparent)
}
