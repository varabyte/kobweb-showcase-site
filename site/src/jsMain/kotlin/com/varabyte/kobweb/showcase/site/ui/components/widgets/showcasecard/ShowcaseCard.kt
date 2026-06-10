package com.varabyte.kobweb.showcase.site.ui.components.widgets.showcasecard

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.showcase.site.model.ShowcaseSite
import com.varabyte.kobweb.showcase.site.ui.locales.AppStrings
import com.varabyte.kobweb.showcase.site.ui.styles.frostedGlass
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@OptIn(ExperimentalComposeWebApi::class)
val ShowcaseCardStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            .frostedGlass(palette.surface.toRgb().copyf(alpha = if (colorMode.isLight) 0.45f else 0.35f), 2.px)
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
                            .fontWeight(FontWeight.Bold)
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
