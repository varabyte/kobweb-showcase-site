package com.varabyte.kobweb_showcase_site.ui.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb_showcase_site.model.ShowcaseSite
import com.varabyte.kobweb_showcase_site.ui.locales.AppStrings
import com.varabyte.kobweb_showcase_site.ui.theme.SiteColors
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems.Companion.Center
import org.jetbrains.compose.web.dom.*

@OptIn(ExperimentalComposeWebApi::class)
val ShowcaseCardStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            .backgroundColor(palette.surface)
            .border(1.px, LineStyle.Solid, palette.border)
            .borderRadius(0.75.cssRem)
            .overflow(Overflow.Hidden)
            .position(Position.Relative)
            .cursor(Cursor.Pointer)
            .boxShadow(0.px, 2.px, 8.px, color = palette.border)
            .transition(
                Transition.of("transform", 0.22.s, TransitionTimingFunction.EaseOut),
                Transition.of("box-shadow", 0.22.s, TransitionTimingFunction.EaseOut),
                Transition.of("border-color", 0.22.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        val palette = colorMode.toSitePalette()
        Modifier
            .scale(1.025)
            .border(1.px, LineStyle.Solid, palette.primary)
            .boxShadow(0.px, 8.px, 28.px, color = palette.primary.toRgb().copyf(alpha = 0.25f))
    }
}

// Ensure the image wrapper enforces the aspect ratio safely
// (e.g., 16:9 equivalent is often nice for cards, we'll use a fixed height for now but enforce centering)
val CardImageWrapperStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            // Setting a fixed aspect ratio context for the container
            .height(200.px)
            .position(Position.Relative)
            .backgroundColor(palette.surfaceHover)
            .overflow(Overflow.Hidden)
            .display(DisplayStyle.Flex)
            .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.Center)
            .alignItems(Center)
    }
}

val CardImageStyle = CssStyle {
    base {
        Modifier
            // object-fit: cover ensures the image fills the 200px height container completely,
            // cropping equally from sides/top/bottom if the aspect ratio doesn't perfectly match
            .fillMaxWidth()
            .height(100.percent)
            .objectFit(ObjectFit.Cover)
            .opacity(0)
            .transition(Transition.of("opacity", 0.4.s, TransitionTimingFunction.EaseIn))
    }
}

val ExpandedOverlayStyle = CssStyle {
    base {
        Modifier
            .position(Position.Fixed)
            .top(0.px).left(0.px)
            .width(100.percent).height(100.percent)
            .backgroundColor(Colors.Black.copyf(alpha = 0.72f))
            .zIndex(100)
            .display(DisplayStyle.Flex)
            .alignItems(Center)
            .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.Center)
            .cursor(Cursor.Pointer)
    }
}

val ExpandedCardStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.surface)
            .border(1.px, LineStyle.Solid, palette.border)
            .borderRadius(1.cssRem)
            .overflow(Overflow.Hidden)
            .maxWidth(640.px)
            .width(92.percent)
            .cursor(Cursor.Auto)
            .boxShadow(0.px, 24.px, 64.px, color = Colors.Black.copyf(alpha = 0.45f))
    }
}

val ExternalLinkBtnStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .position(Position.Absolute)
            .top(0.5.cssRem)
            .right(0.5.cssRem)
            .width(1.75.cssRem)
            .height(1.75.cssRem)
            .borderRadius(0.375.cssRem)
            .backgroundColor(palette.surface.toRgb().copyf(alpha = 0.85f))
            .border(1.px, LineStyle.Solid, palette.border)
            .display(DisplayStyle.Flex)
            .alignItems(Center)
            .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.Center)
            .cursor(Cursor.Pointer)
            .zIndex(2)
            .transition(
                Transition.of("background-color", 0.18.s, TransitionTimingFunction.EaseOut),
                Transition.of("border-color",     0.18.s, TransitionTimingFunction.EaseOut),
            )
    }
    hover {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.primary.toRgb().copyf(alpha = 0.15f))
            .border(1.px, LineStyle.Solid, palette.primary)
    }
}

// Special styling for the Varabyte-Official tag
val GlintAnim = Keyframes {
    0.percent { Modifier.styleModifier { property("background-position", "-200% center") } }
    100.percent { Modifier.styleModifier { property("background-position", "200% center") } }
}

val OfficialTagStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Color.rgb(0x161B22)) // Dark base for contrast
            .color(Colors.White)
            .borderRadius(2.cssRem)
            .padding(leftRight = 0.6.cssRem, topBottom = 0.2.cssRem)
            .margin(right = 0.4.cssRem, bottom = 0.4.cssRem)
            .fontSize(0.75.cssRem)
            .fontWeight(700)
            .border(1.px, LineStyle.Solid, Colors.White.copyf(alpha = 0.3f))
            .boxShadow(0.px, 0.px, 8.px, color = Colors.White.copyf(alpha = 0.5f))
            .styleModifier {
                property("background-image", "linear-gradient(110deg, transparent 40%, rgba(255, 255, 255, 0.8) 50%, transparent 60%)")
                property("background-size", "200% auto")
            }
            .animation(
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
        Modifier
            .backgroundColor(SiteColors.KobwebGold.toRgb().copyf(alpha = 0.15f))
            .color(SiteColors.KobwebGold)
            .borderRadius(2.cssRem)
            .padding(leftRight = 0.6.cssRem, topBottom = 0.2.cssRem)
            .margin(right = 0.4.cssRem, bottom = 0.4.cssRem)
            .fontSize(0.75.cssRem)
            .fontWeight(500)
            .border(1.px, LineStyle.Solid, SiteColors.KobwebGold.toRgb().copyf(alpha = 0.3f))
    }
}

@Composable
private fun ExternalLinkIcon(color: String = "currentColor") {
    Span(
        attrs = Modifier
            .fontSize(0.85.cssRem)
            .lineHeight(1.0)
            .color(Color.rgb(0x00FF87))
            .toAttrs()
    ) {
        Text("↗")
    }
}

@Composable
private fun TagItem(tag: String) {
    Box((if (tag == "Varabyte-Official") OfficialTagStyle else CustomTagStyle).toModifier()) {
        Text(tag)
    }
}

/**
 * Normalizes and sorts tags so that "Varabyte-Official" is always first.
 */
private fun List<String>.normalizeAndSortTags(): List<String> {
    val normalized = this
        .flatMap { it.split(",") }
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinct() // Prevent duplicates

    return normalized.sortedByDescending { it == "Varabyte-Official" }
}

@Composable
private fun ExpandedCard(site: ShowcaseSite, onDismiss: () -> Unit) {
    val palette = ColorMode.current.toSitePalette()

    Div(
        attrs = ExpandedOverlayStyle.toModifier()
            .onClick { onDismiss() }
            .toAttrs()
    ) {
        Div(
            attrs = ExpandedCardStyle.toModifier()
                .onClick { it.stopPropagation() }
                .toAttrs()
        ) {
            Img(
                src = site.imageUrl,
                alt = site.name,
                attrs = Modifier
                    .fillMaxWidth()
                    // Fixed max height for expanded view, cover ensures it centers and crops nicely
                    .maxHeight(360.px)
                    .objectFit(ObjectFit.Cover)
                    .display(DisplayStyle.Block)
                    .toAttrs()
            )
            Column(Modifier.padding(1.5.cssRem).gap(0.75.cssRem)) {
                H2(
                    attrs = Modifier
                        .margin(0.px)
                        .fontSize(1.25.cssRem)
                        .color(palette.textPrimary)
                        .toAttrs()
                ) { Text(site.name) }

                P(attrs = Modifier
                    .margin(0.px)
                    .fontSize(0.9.cssRem)
                    .color(palette.textMuted)
                    .lineHeight(1.6)
                    .toAttrs()
                ) { Text(site.description) }

                if (site.siteType.isNotEmpty()) {
                    SpanText("Type: ${site.siteType}", modifier = Modifier.fontSize(0.85.cssRem).color(palette.textMuted).fontWeight(FontWeight.SemiBold))
                }

                Row(Modifier.flexWrap(FlexWrap.Wrap)) {
                    site.tags.normalizeAndSortTags().forEach { tag ->
                        TagItem(tag)
                    }
                }

                A(
                    href = site.url,
                    attrs = Modifier
                        .margin(top = 0.5.cssRem)
                        .padding(topBottom = 0.6.cssRem, leftRight = 1.4.cssRem)
                        .backgroundColor(palette.primary)
                        .color(Color.rgb(0x06080B))
                        .borderRadius(0.5.cssRem)
                        .fontWeight(FontWeight.SemiBold)
                        .fontSize(0.95.cssRem)
                        .textDecorationLine(TextDecorationLine.None)
                        .cursor(Cursor.Pointer)
                        .display(DisplayStyle.InlineBlock)
                        .toAttrs {
                            attr("target", "_blank")
                            attr("rel", "noopener noreferrer")
                        }
                ) { Text("${AppStrings.VISIT_SITE} ↗") }
            }
        }
    }
}

@Composable
fun ShowcaseCard(site: ShowcaseSite) {
    val palette = ColorMode.current.toSitePalette()
    var expanded by remember { mutableStateOf(false) }

    if (expanded) {
        ExpandedCard(site, onDismiss = { expanded = false })
    }

    Box(
        ShowcaseCardStyle.toModifier().onClick { expanded = true }
    ) {
        Column(Modifier.fillMaxWidth()) {

            Box(CardImageWrapperStyle.toModifier()) {
                Img(
                    src = site.imageUrl,
                    alt = site.name,
                    attrs = CardImageStyle.toModifier().toAttrs {
                        attr("onload", "this.style.opacity='1'")
                    }
                )
            }

            Column(Modifier.padding(1.25.cssRem).gap(0.5.cssRem)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    SpanText(
                        site.name,
                        modifier = Modifier
                            .fontWeight(FontWeight.SemiBold)
                            .fontSize(1.05.cssRem)
                            .color(palette.primary)
                            .flexGrow(1)
                    )
                }
                P(attrs = Modifier
                    .margin(0.px)
                    .fontSize(0.9.cssRem)
                    .color(palette.textMuted)
                    .lineHeight(1.55)
                    .toAttrs()
                ) { Text(site.description) }

                Row(Modifier.flexWrap(FlexWrap.Wrap).margin(top = 0.5.cssRem)) {
                    site.tags.normalizeAndSortTags().forEach { tag ->
                        TagItem(tag)
                    }
                }
            }
        }

        A(
            href = site.url,
            attrs = ExternalLinkBtnStyle.toModifier()
                .onClick { it.stopPropagation() }
                .toAttrs {
                    attr("target", "_blank")
                    attr("rel", "noopener noreferrer")
                    attr("title", AppStrings.VISIT_SITE)
                }
        ) {
            ExternalLinkIcon()
        }
    }
}
