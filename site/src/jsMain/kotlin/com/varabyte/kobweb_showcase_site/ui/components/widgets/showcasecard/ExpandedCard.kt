package com.varabyte.kobweb_showcase_site.ui.components.widgets.showcasecard

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb_showcase_site.model.ShowcaseSite
import com.varabyte.kobweb_showcase_site.ui.locales.AppStrings
import com.varabyte.kobweb_showcase_site.ui.styles.frostedGlass
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems.Companion.Center
import org.jetbrains.compose.web.dom.*

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
            .frostedGlass(palette.surface.toRgb().copyf(alpha = if (colorMode.isLight) 0.5f else 0.3f), 8.px)
            .border(1.px, LineStyle.Solid, palette.border)
            .borderRadius(1.cssRem)
            .overflow(Overflow.Hidden)
            .maxWidth(640.px)
            .width(92.percent)
            .maxHeight(90.vh)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .cursor(Cursor.Auto)
            .boxShadow(0.px, 24.px, 64.px, color = Colors.Black.copyf(alpha = 0.45f))
    }
}

@Composable
fun ExpandedCard(site: ShowcaseSite, onDismiss: () -> Unit) {
    val palette = ColorMode.current.toSitePalette()
    val isLight = ColorMode.current.isLight

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
            Box(Modifier.fillMaxWidth().position(Position.Relative)) {
                Img(
                    src = site.imageUrl,
                    alt = site.name,
                    attrs = Modifier
                        .fillMaxWidth()
                        .maxHeight(360.px)
                        .objectFit(ObjectFit.Cover)
                        .styleModifier { property("object-position", "top center") }
                        .display(DisplayStyle.Block)
                        .toAttrs()
                )

                Box(
                    Modifier
                        .position(Position.Absolute)
                        .top(0.75.cssRem)
                        .right(0.75.cssRem)
                        .width(2.5.cssRem)
                        .height(2.5.cssRem)
                        .frostedGlass(Colors.Black.copyf(alpha = 0.5f), 8.px)
                        .borderRadius(50.percent)
                        .display(DisplayStyle.Flex)
                        .alignItems(Center)
                        .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.Center)
                        .cursor(Cursor.Pointer)
                        .onClick { onDismiss() }
                ) {
                    CloseIcon(Modifier.color(Colors.White))
                }
            }

            Column(Modifier.padding(1.5.cssRem).gap(0.75.cssRem).styleModifier { property("overflow-y", "auto") }) {
                H2(
                    attrs = Modifier
                        .margin(0.px)
                        .fontSize(1.25.cssRem)
                        .color(palette.primary)
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
                    SpanText("Type: ${site.siteType}",
                        modifier = Modifier
                            .fontSize(0.85.cssRem)
                            .color(palette.textMuted)
                            .fontWeight(FontWeight.SemiBold))
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
                        .frostedGlass(palette.primary.toRgb().copyf(alpha = if (isLight) 0.15f else 0.25f), 4.px)
                        .border(1.px, LineStyle.Solid, palette.primary.toRgb().copyf(alpha = if (isLight) 0.3f else 0.4f))
                        .color(palette.primary)
                        .borderRadius(0.5.cssRem)
                        .fontWeight(FontWeight.Medium)
                        .fontSize(0.95.cssRem)
                        .textDecorationLine(TextDecorationLine.None)
                        .cursor(Cursor.Pointer)
                        .display(DisplayStyle.InlineBlock)
                        .transition(
                            Transition.of("background-color", 0.2.s, TransitionTimingFunction.EaseOut)
                        )
                        .toAttrs {
                            attr("target", "_blank")
                            attr("rel", "noopener noreferrer")
                        }
                ) { Text(AppStrings.VISIT_SITE) }
            }
        }
    }
}
