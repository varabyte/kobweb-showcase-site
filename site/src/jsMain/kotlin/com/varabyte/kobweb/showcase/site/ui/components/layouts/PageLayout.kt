package com.varabyte.kobweb.showcase.site.ui.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.showcase.site.ui.components.sections.Footer
import com.varabyte.kobweb.showcase.site.ui.components.sections.NavHeader
import com.varabyte.kobweb.showcase.site.ui.components.widgets.CanvasBackground
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.vh
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

    CanvasBackground()

    Column(
        Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .position(Position.Relative)
            .zIndex(1),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavHeader()
        Column(
            Modifier.fillMaxWidth().flexGrow(1),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Div(PageContentStyle.toAttrs()) {
                content()
            }
        }
        Footer(Modifier.fillMaxWidth())
    }
}
