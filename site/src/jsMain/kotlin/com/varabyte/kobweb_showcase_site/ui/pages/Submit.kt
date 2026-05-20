package com.varabyte.kobweb_showcase_site.ui.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight.Companion.SemiBold
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb_showcase_site.ui.components.layouts.PageLayoutData
import com.varabyte.kobweb_showcase_site.ui.locales.AppStrings
import com.varabyte.kobweb_showcase_site.ui.theme.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initSubmitPage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData(AppStrings.SUBMIT_PAGE_TITLE))
}

@Page
@Layout(".ui.components.layouts.PageLayout")
@Composable
fun SubmitPage() {
    val palette = ColorMode.current.toSitePalette()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(topBottom = 4.cssRem, leftRight = 2.cssRem)
            .gap(1.5.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        H1(attrs = Modifier
            .color(palette.primary)
            .textAlign(TextAlign.Center)
            .toAttrs()
        ) {
            Text(AppStrings.SUBMIT_PAGE_TITLE)
        }

        P(attrs = Modifier
            .textAlign(TextAlign.Center)
            .color(palette.textMuted)
            .maxWidth(40.cssRem)
            .toAttrs()
        ) {
            Text(AppStrings.SUBMIT_PAGE_DESC)
        }

        Link(
            path = "https://github.com/varabyte/kobweb-showcase-db/issues/new?template=submit-entry.yml",
            text = AppStrings.OPEN_ISSUE_BTN,
            modifier = Modifier
                .color(palette.primary)
                .fontSize(1.05.cssRem)
                .fontWeight(SemiBold)
        )
    }
}
