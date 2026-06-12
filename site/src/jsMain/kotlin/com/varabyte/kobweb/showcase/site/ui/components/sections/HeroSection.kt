package com.varabyte.kobweb.showcase.site.ui.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.RadialGradient
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.css.functions.radialGradient
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.showcase.site.ui.components.KobwebColor
import com.varabyte.kobweb.showcase.site.ui.locales.AppStrings
import com.varabyte.kobweb.showcase.site.ui.styles.frostedGlass
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

private fun Modifier.glowingTextBackground(baseColor: KobwebColor, isLight: Boolean): Modifier = this
    .color(if (isLight) Colors.Black else baseColor)
    .padding(leftRight = 0.75.cssRem, topBottom = 0.15.cssRem)
    .borderRadius(1.cssRem)
    .fontWeight(FontWeight.SemiBold)
    .textDecorationLine(TextDecorationLine.None)
    .backgroundImage(radialGradient(RadialGradient.Shape.Ellipse, CSSPosition.Center) {
        add(baseColor.toRgb().copyf(alpha = if (isLight) 0.5f else 0.25f), 0.percent)
        add(Colors.Transparent, 70.percent)
    })

val HeroPrimaryBoxStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        val bgAlpha = if (colorMode.isLight) 0.3f else 0.15f
        val borderAlpha = if (colorMode.isLight) 0.4f else 0.2f
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 2.cssRem, leftRight = 2.cssRem)
            .frostedGlass(palette.surface.toRgb().copyf(alpha = bgAlpha), 4.px)
            .border(2.px, LineStyle.Solid, palette.primary.toRgb().copyf(alpha = borderAlpha))
            .borderRadius(1.25.cssRem)
            .boxShadow(0.px, 0.px, 24.px, color = palette.primary.toRgb().copyf(alpha = 0.15f))
            .transition(
                Transition.of("border-color", 0.22.s, TransitionTimingFunction.EaseOut),
                Transition.of("box-shadow",   0.22.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        val palette = colorMode.toSitePalette()
        Modifier
            .border(2.px, LineStyle.Solid, palette.primary)
            .boxShadow(0.px, 0.px, 48.px, color = palette.primary.toRgb().copyf(alpha = if (colorMode.isLight) 0.7f else 0.8f))
    }
}

val HeroNoteBoxStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        val bgAlpha = if (colorMode.isLight) 0.5f else 0.15f
        val borderAlpha = if (colorMode.isLight) 0.4f else 0.2f
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 1.25.cssRem, leftRight = 2.cssRem)
            .frostedGlass(palette.surface.toRgb().copyf(alpha = bgAlpha), 4.px)
            .border(2.px, LineStyle.Solid, palette.accent.toRgb().copyf(alpha = borderAlpha))
            .borderRadius(1.cssRem)
            .boxShadow(0.px, 0.px, 24.px, color = palette.accent.toRgb().copyf(alpha = 0.15f))
            .transition(
                Transition.of("border-color", 0.22.s, TransitionTimingFunction.EaseOut),
                Transition.of("box-shadow",   0.22.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        val palette = colorMode.toSitePalette()
        Modifier
            .border(2.px, LineStyle.Solid, palette.accent)
            .boxShadow(0.px, 0.px, 36.px, color = palette.accent.toRgb().copyf(alpha = if (colorMode.isLight) 0.5f else 0.8f))
    }
}

@Composable
fun HeroSection() {
    val palette = ColorMode.current.toSitePalette()
    val isLight = ColorMode.current.isLight

    Column(
        Modifier.fillMaxWidth().gap(1.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            HeroPrimaryBoxStyle.toModifier().gap(0.75.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            H1(attrs = Modifier
                .margin(0.px)
                .fontSize(clamp(2.cssRem, 5.vw, 3.cssRem))
                .fontWeight(FontWeight.Bold)
                .color(palette.primary)
                .textAlign(TextAlign.Center)
                .textShadow(
                    TextShadow.of(0.px, 0.px, 12.px, palette.primary.toRgb().copyf(alpha = if (isLight) 0.3f else 0.65f)),
                    TextShadow.of(0.px, 0.px, 24.px, palette.primary.toRgb().copyf(alpha = if (isLight) 0.1f else 0.35f))
                )
                .toAttrs()
            ) {
                Text(AppStrings.HERO_HEADLINE)
            }

            P(attrs = Modifier
                .margin(0.px)
                .maxWidth(44.cssRem)
                .textAlign(TextAlign.Center)
                .fontSize(1.05.cssRem)
                .lineHeight(1.6)
                .color(palette.textPrimary)
                .fontWeight(FontWeight.Normal)
                .toAttrs()
            ) {
                Text("A curated gallery of real-world websites and apps with ")
                Link(
                    "https://kobweb.varabyte.com",
                    "Kobweb",
                    modifier = Modifier
                        .color(palette.primary)
                        .fontWeight(FontWeight.SemiBold)
                        .textDecorationLine(TextDecorationLine.None)
                        .textShadow(0.px, 0.px, 10.px, palette.primary.toRgb().copyf(alpha = 0.5f))
                )
                Text(", the Kotlin-first web framework.")
            }
        }

        Column(
            HeroNoteBoxStyle.toModifier().gap(0.6.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            P(attrs = Modifier
                .margin(0.px)
                .textAlign(TextAlign.Center)
                .fontSize(0.9.cssRem)
                .color(palette.textPrimary)
                .fontWeight(FontWeight.Normal)
                .toAttrs()
            ) {
                Text(AppStrings.HERO_DB_NOTE + " ")
                Link(
                    AppStrings.HERO_DB_URL,
                    AppStrings.HERO_DB_LINK_TEXT,
                    modifier = Modifier.glowingTextBackground(palette.accent, isLight)
                )
            }

            P(attrs = Modifier
                .margin(0.px)
                .textAlign(TextAlign.Center)
                .fontSize(0.95.cssRem)
                .color(palette.textPrimary)
                .fontWeight(FontWeight.Normal)
                .toAttrs()
            ) {
                Text(AppStrings.HERO_TEMPLATE_PITCH + " ")
                Link(
                    AppStrings.HERO_TEMPLATE_URL,
                    AppStrings.HERO_TEMPLATE_LINK_TEXT,
                    modifier = Modifier.glowingTextBackground(palette.primary, isLight)
                )
            }

            P(attrs = Modifier
                .margin(0.px)
                .textAlign(TextAlign.Center)
                .fontSize(0.85.cssRem)
                .color(palette.textMuted)
                .fontWeight(FontWeight.Normal)
                .maxWidth(40.cssRem)
                .toAttrs()
            ) {
                Text(AppStrings.HERO_TEMPLATE_DESC)
            }
        }
    }
}
