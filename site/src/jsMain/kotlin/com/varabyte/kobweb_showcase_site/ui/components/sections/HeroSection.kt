package com.varabyte.kobweb_showcase_site.ui.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb_showcase_site.ui.locales.AppStrings
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


val HeroPrimaryBoxStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 2.cssRem, leftRight = 2.cssRem)
            .backgroundColor(palette.primary.toRgb().copyf(alpha = 0.08f))
            .border(1.px, LineStyle.Solid, palette.primary.toRgb().copyf(alpha = 0.25f))
            .borderRadius(1.25.cssRem)
            .boxShadow(0.px, 0.px, 32.px, color = palette.primary.toRgb().copyf(alpha = 0.10f))
            .transition(
                Transition.of("border-color", 0.22.s, TransitionTimingFunction.EaseOut),
                Transition.of("box-shadow",   0.22.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        val palette = colorMode.toSitePalette()
        Modifier
            .border(1.px, LineStyle.Solid, palette.primary.toRgb().copyf(alpha = 0.65f))
            .boxShadow(0.px, 0.px, 48.px, color = palette.primary.toRgb().copyf(alpha = 0.22f))
    }
}

val HeroNoteBoxStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 1.25.cssRem, leftRight = 2.cssRem)
            .backgroundColor(palette.accent.toRgb().copyf(alpha = 0.07f))
            .border(1.px, LineStyle.Solid, palette.accent.toRgb().copyf(alpha = 0.22f))
            .borderRadius(1.cssRem)
            .boxShadow(0.px, 0.px, 24.px, color = palette.accent.toRgb().copyf(alpha = 0.08f))
            .transition(
                Transition.of("border-color", 0.22.s, TransitionTimingFunction.EaseOut),
                Transition.of("box-shadow",   0.22.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        val palette = colorMode.toSitePalette()
        Modifier
            .border(1.px, LineStyle.Solid, palette.accent.toRgb().copyf(alpha = 0.60f))
            .boxShadow(0.px, 0.px, 36.px, color = palette.accent.toRgb().copyf(alpha = 0.18f))
    }
}

// Composables

@Composable
fun HeroSection() {
    val palette = ColorMode.current.toSitePalette()

    Column(
        Modifier.fillMaxWidth().gap(1.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Primary hero box
        Column(
            HeroPrimaryBoxStyle.toModifier().gap(0.75.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Site headline
            H1(attrs = Modifier
                .margin(0.px)
                .fontSize(clamp(2.cssRem, 5.vw, 3.cssRem))
                .fontWeight(FontWeight.ExtraBold)
                .color(palette.primary)
                .textAlign(TextAlign.Center)
                .styleModifier {
                    // Glow outline matching card hover behaviour
                    property(
                        "text-shadow",
                        "0 0 24px ${palette.primary.toRgb().copyf(alpha = 0.55f).toString()}, " +
                                "0 0 48px ${palette.primary.toRgb().copyf(alpha = 0.25f).toString()}"
                    )
                }
                .toAttrs()
            ) {
                Text(AppStrings.HERO_HEADLINE)
            }

            // Subtitle
            P(attrs = Modifier
                .margin(0.px)
                .maxWidth(44.cssRem)
                .textAlign(TextAlign.Center)
                .fontSize(1.05.cssRem)
                .lineHeight(1.65)
                .color(palette.textMuted)
                .toAttrs()
            ) {
                Text(AppStrings.HERO_SUBTEXT)
            }
        }

        // Note box (yellow tint)
        Column(
            HeroNoteBoxStyle.toModifier().gap(0.6.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Muted note about IssueOps DB
            P(attrs = Modifier
                .margin(0.px)
                .textAlign(TextAlign.Center)
                .fontSize(0.9.cssRem)
                .color(palette.textMuted)
                .toAttrs()
            ) {
                Text(AppStrings.HERO_DB_NOTE + " ")
                Link(
                    AppStrings.HERO_DB_URL,
                    AppStrings.HERO_DB_LINK_TEXT,
                    modifier = Modifier
                        .color(palette.accent)
                        .fontWeight(FontWeight.SemiBold)
                )
            }

            // Prominent template pitch
            P(attrs = Modifier
                .margin(0.px)
                .textAlign(TextAlign.Center)
                .fontSize(0.95.cssRem)
                .color(palette.textPrimary)
                .toAttrs()
            ) {
                Text(AppStrings.HERO_TEMPLATE_PITCH + " ")
                Link(
                    AppStrings.HERO_TEMPLATE_URL,
                    AppStrings.HERO_TEMPLATE_LINK_TEXT,
                    modifier = Modifier
                        .color(palette.primary)
                        .fontWeight(FontWeight.Bold)
                )
            }

            // Template description in muted small text
            P(attrs = Modifier
                .margin(0.px)
                .textAlign(TextAlign.Center)
                .fontSize(0.8.cssRem)
                .color(palette.textMuted)
                .maxWidth(40.cssRem)
                .toAttrs()
            ) {
                Text(AppStrings.HERO_TEMPLATE_DESC)
            }
        }
    }
}
