package com.varabyte.kobweb_showcase_site.ui.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.core.AppGlobals
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.worker.rememberWorker
import com.varabyte.kobweb_showcase_site.model.ShowcaseSite
import com.varabyte.kobweb_showcase_site.ui.components.layouts.PageLayoutData
import com.varabyte.kobweb_showcase_site.ui.components.sections.HeroSection
import com.varabyte.kobweb_showcase_site.ui.components.widgets.ShowcaseCard
import com.varabyte.kobweb_showcase_site.ui.components.widgets.SkeletonCard
import com.varabyte.kobweb_showcase_site.worker.FetchShowcaseWorker
import org.jetbrains.compose.web.css.cssRem

@InitRoute
fun initShowcasePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Showcase"))
}

@Page
@Layout(".ui.components.layouts.PageLayout")
@Composable
fun ShowcasePage() {
    var sites by remember { mutableStateOf<List<ShowcaseSite>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val worker = rememberWorker {
        FetchShowcaseWorker { response ->
            sites = response.sites
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        val dataUrl = AppGlobals.getValue("SHOWCASE_DATA_URL")
        worker.postInput(dataUrl)
    }

    Column(
        Modifier.fillMaxWidth().gap(2.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeroSection()

        if (isLoading) {
            SimpleGrid(
                numColumns = numColumns(base = 1, sm = 2, md = 3),
                modifier = Modifier.fillMaxWidth().gap(1.5.cssRem)
            ) {
                repeat(6) { SkeletonCard() }
            }
        } else {
            SimpleGrid(
                numColumns = numColumns(base = 1, sm = 2, md = 3),
                modifier = Modifier.fillMaxWidth().gap(1.5.cssRem)
            ) {
                sites.forEach { site -> ShowcaseCard(site) }
            }
        }
    }
}

