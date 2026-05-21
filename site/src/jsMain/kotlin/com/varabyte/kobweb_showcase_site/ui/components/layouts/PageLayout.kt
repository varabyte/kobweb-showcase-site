package com.varabyte.kobweb_showcase_site.ui.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb_showcase_site.ui.components.sections.Footer
import com.varabyte.kobweb_showcase_site.ui.components.sections.NavHeader
import com.varabyte.kobweb_showcase_site.ui.components.widgets.CanvasBackground
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val PageContentStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(leftRight = 1.5.cssRem, topBottom = 2.cssRem)
    }
    Breakpoint.MD {
        Modifier.maxWidth(72.cssRem)
    }
    Breakpoint.LG {
        Modifier.maxWidth(90.cssRem)
    }
}

class PageLayoutData(val title: String)

@Composable
@Layout
fun PageLayout(ctx: PageContext, content: @Composable () -> Unit) {
    val data = ctx.data.getValue<PageLayoutData>()
    LaunchedEffect(data.title) {
        document.title = "Kobweb Showcase — ${data.title}"
    }

    Box(
        Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .gridTemplateRows { size(1.fr); size(minContent) },
        contentAlignment = Alignment.Center
    ) {
        // Canvas is now a sibling to the main content, rendering underneath due to z-index mapping
        CanvasBackground()

        // Z-Index 1 ensures the main content (Nav + Cards) sits physically above the Canvas background
        Column(
            Modifier
                .fillMaxSize()
                .gridRow(1)
                .position(Position.Relative)
                .zIndex(1),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NavHeader()
            Div(PageContentStyle.toAttrs()) {
                content()
            }
        }
        Footer(
            Modifier
                .fillMaxWidth()
                .gridRow(2)
                .position(Position.Relative)
                .zIndex(1)
        )
    }
}
