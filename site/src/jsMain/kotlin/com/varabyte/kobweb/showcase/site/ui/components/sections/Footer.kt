package com.varabyte.kobweb.showcase.site.ui.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.showcase.site.ui.locales.AppStrings
import com.varabyte.kobweb.showcase.site.ui.styles.FooterStyle
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text


@Composable
fun Footer(modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()

    Row(
        FooterStyle.toModifier().then(modifier).gap(1.5.cssRem),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Built with Kobweb
        Span(Modifier.textAlign(TextAlign.Center).toAttrs()) {
            SpanText(AppStrings.BUILT_WITH)
            Link(AppStrings.KOBWEB_URL) {
                Image(
                    "/kobweb-logo.png", "Kobweb Logo", Modifier.height(2.cssRem).display(DisplayStyle.Block)
                )
            }
        }

        // Separator
        SpanText(
            "·", modifier = Modifier.color(palette.textMuted).fontSize(1.2.cssRem)
        )

        // Credit
        Link(
            AppStrings.FOOTER_CREDIT_URL,
            modifier = Modifier.color(palette.primary).fontWeight(FontWeight.SemiBold).fontSize(0.85.cssRem)
                .textDecorationLine(TextDecorationLine.None)
                .border(1.px, LineStyle.Solid, palette.primary.toRgb().copyf(alpha = 0.35f)).borderRadius(0.5.cssRem)
                .padding(topBottom = 0.2.cssRem, leftRight = 0.6.cssRem)
                .boxShadow(0.px, 0.px, 8.px, color = palette.primary.toRgb().copyf(alpha = 0.25f))
        ) {
            Text(AppStrings.FOOTER_CREDIT_TEXT)
        }
    }
}
