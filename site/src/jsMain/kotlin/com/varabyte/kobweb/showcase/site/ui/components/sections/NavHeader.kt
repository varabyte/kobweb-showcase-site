package com.varabyte.kobweb.showcase.site.ui.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget.Companion.PreviousSibling
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextShadow
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.showcase.site.ui.components.widgets.IconButton
import com.varabyte.kobweb.showcase.site.ui.locales.AppStrings
import com.varabyte.kobweb.showcase.site.ui.styles.NavHeaderStyle
import com.varabyte.kobweb.showcase.site.ui.theme.toSitePalette
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.overlay.OverlayVars
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.w3c.dom.events.Event

val SiteNameTextStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontWeight(FontWeight.Bold)
        .fontSize(1.1.cssRem)
        .color(palette.primary)
        .textShadow(
            TextShadow.of(0.px, 0.px, 10.px, palette.primary.toRgb().copyf(alpha = 0.55f)),
            TextShadow.of(0.px, 0.px, 20.px, palette.primary.toRgb().copyf(alpha = 0.25f))
        )
}

@Composable
private fun NavLink(path: String, text: String) {
    Link(path, text, variant = UndecoratedLinkVariant.then(UncoloredLinkVariant))
}

@Composable
private fun SiteName() {
    Link(
        path = "/",
        variant = UndecoratedLinkVariant.then(UncoloredLinkVariant)
    ) {
        SpanText(
            AppStrings.APP_TITLE,
            modifier = SiteNameTextStyle.toModifier()
        )
    }
}

@Composable
private fun GitHubSourceLink() {
    Link(
        AppStrings.SITE_SOURCE_URL,
        modifier = Modifier
            .color(ColorMode.current.toSitePalette().textMuted)
            .fontSize(1.25.cssRem)
            .lineHeight(1.0)
            .title(AppStrings.SITE_SOURCE_TOOLTIP)
            .display(DisplayStyle.Flex)
    ) {
        // GitHub SVG icon
        Span(attrs = Modifier.toAttrs()) {
            Svg(
                attrs = {
                    attr("width", "20")
                    attr("height", "20")
                    attr("viewBox", "0 0 16 16")
                    attr("fill", "currentColor")
                    attr("aria-hidden", "true")
                }
            ) {
                Path(
                    attrs = {
                        attr(
                            "d",
                            "M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 " +
                                    "0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13" +
                                    "-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66" +
                                    ".07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15" +
                                    "-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 " +
                                    "1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 " +
                                    "1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 " +
                                    "1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"
                        )
                    }
                )
            }
        }
    }
    Tooltip(PreviousSibling, AppStrings.SITE_SOURCE_TOOLTIP, placement = PopupPlacement.BottomRight)
}

@Composable
private fun MenuItems() {
    NavLink(
        "/",
        AppStrings.APP_TITLE
    )
    NavLink(
        "/submit",
        AppStrings.SUBMIT_DEMO_BTN
    )
}

@Composable
private fun ColorModeButton() {
    var colorMode by ColorMode.currentState
    IconButton(onClick = {
        colorMode = colorMode.opposite
    }) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(PreviousSibling, AppStrings.TOGGLE_THEME_TOOLTIP, placement = PopupPlacement.BottomRight)
}

@Composable
private fun HamburgerButton(onClick: () -> Unit) {
    IconButton(onClick) { HamburgerIcon() }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    IconButton(onClick) { CloseIcon() }
}

val SideMenuSlideInAnim = Keyframes {
    from { Modifier.translateX(100.percent) }
    to { Modifier }
}

enum class SideMenuState {
    CLOSED, OPEN, CLOSING;

    fun close() = when (this) {
        CLOSED -> CLOSED
        OPEN -> CLOSING
        CLOSING -> CLOSING
    }
}

@Composable
fun NavHeader() {
    Row(NavHeaderStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {

        // ── Desktop layout ─────────────────────────────────────────────────────
        Row(
            Modifier.gap(1.25.cssRem).displayIfAtLeast(Breakpoint.MD),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SiteName()
            Spacer()
            GitHubSourceLink()
            NavLink(
                "/submit",
                AppStrings.SUBMIT_DEMO_BTN
            )
            ColorModeButton()
        }

        Spacer()

        // ── Mobile layout ──────────────────────────────────────────────────────
        Row(
            Modifier.fontSize(1.5.cssRem).gap(1.cssRem).displayUntil(Breakpoint.MD),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SiteName()
            Spacer()
            var menuState by remember { mutableStateOf(SideMenuState.CLOSED) }

            ColorModeButton()
            HamburgerButton(onClick = {
                menuState =
                    if (menuState == SideMenuState.CLOSED) SideMenuState.OPEN else menuState.close()
            })

            if (menuState != SideMenuState.CLOSED) {
                SideMenu(
                    menuState,
                    close = { menuState = menuState.close() },
                    onAnimationEnd = {
                        if (menuState == SideMenuState.CLOSING) menuState =
                            SideMenuState.CLOSED
                    }
                )
            }
        }
    }
}

@Composable
private fun SideMenu(menuState: SideMenuState, close: () -> Unit, onAnimationEnd: () -> Unit) {
    DisposableEffect(Unit) {
        val onResize = { _: Event ->
            if (window.innerWidth >= 768 && menuState != SideMenuState.CLOSED) close()
        }
        window.addEventListener("resize", onResize)
        onDispose { window.removeEventListener("resize", onResize) }
    }

    Overlay(
        Modifier
            .setVariable(OverlayVars.BackgroundColor, Colors.Black.copyf(alpha = 0.6f))
            .zIndex(200)
            .onClick { close() }
    ) {
        key(menuState) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .width(clamp(12.cssRem, 40.percent, 16.cssRem))
                    .align(Alignment.CenterEnd)
                    .padding(top = 1.cssRem, leftRight = 1.cssRem)
                    .gap(1.5.cssRem)
                    .backgroundColor(ColorMode.current.toSitePalette().nearBackground)
                    .fontFamily("Inter", "system-ui", "-apple-system", "sans-serif")
                    .borderLeft(1.px, LineStyle.Solid, ColorMode.current.toSitePalette().primary)
                    .boxShadow(
                        (-4).px,
                        0.px,
                        24.px,
                        color = ColorMode.current.toSitePalette().primary.toRgb().copyf(alpha = 0.2f)
                    )
                    .animation(
                        SideMenuSlideInAnim.toAnimation(
                            duration = 200.ms,
                            timingFunction = if (menuState == SideMenuState.OPEN) AnimationTimingFunction.EaseOut else AnimationTimingFunction.EaseIn,
                            direction = if (menuState == SideMenuState.OPEN) AnimationDirection.Normal else AnimationDirection.Reverse,
                            fillMode = AnimationFillMode.Forwards
                        )
                    )
                    .borderRadius(topLeft = 1.5.cssRem, bottomLeft = 1.5.cssRem)
                    .onClick { it.stopPropagation() }
                    .onAnimationEnd { onAnimationEnd() },
                horizontalAlignment = Alignment.End
            ) {
                Column(
                    Modifier.padding(right = 0.75.cssRem).gap(1.5.cssRem).fontSize(1.4.cssRem),
                    horizontalAlignment = Alignment.End
                ) {
                    CloseButton(onClick = { close() })
                    MenuItems()
                    GitHubSourceLink()
                }
            }
        }
    }
}
