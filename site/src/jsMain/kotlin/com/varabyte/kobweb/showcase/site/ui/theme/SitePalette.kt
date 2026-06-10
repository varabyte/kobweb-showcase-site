package com.varabyte.kobweb.showcase.site.ui.theme

import com.varabyte.kobweb.compose.ui.graphics.Color

data class SitePalette(
    // Surfaces
    val background: Color,
    val nearBackground: Color,
    val surface: Color,
    val surfaceHover: Color,
    val border: Color,
    // Brand
    val primary: Color,
    val primaryHover: Color,
    val accent: Color,
    // Content
    val textPrimary: Color,
    val textMuted: Color,
    // Tags
    val tagBackground: Color,
    val tagText: Color,
    // Decorative (kept for any legacy cobweb SVG references)
    val cobweb: Color,
)

object SitePalettes {
    val light = SitePalette(
        background = SiteColors.CloudWhite,
        nearBackground = SiteColors.NeutralLight,
        surface = SiteColors.SurfaceLight,
        surfaceHover = SiteColors.NeutralLight,
        border = SiteColors.BorderLight,
        primary = SiteColors.MakoGreenDark,
        primaryHover = SiteColors.MakoDeepDark,
        accent = SiteColors.KobwebGold,
        textPrimary = Color.rgb(0x0D1117),
        textMuted = Color.rgb(0x57606A),
        tagBackground = SiteColors.TagBgLight,
        tagText = SiteColors.TagTextLight,
        cobweb = SiteColors.BorderLight,
    )
    val dark = SitePalette(
        background = SiteColors.NightBlue,
        nearBackground = SiteColors.SlateBlue,
        surface = SiteColors.SlateBlueLight,
        surfaceHover = SiteColors.SlateBlueHover,
        border = SiteColors.BorderDark,
        primary = SiteColors.MakoGreen,
        primaryHover = SiteColors.MakoDeep,
        accent = SiteColors.KobwebGold,
        textPrimary = Color.rgb(0xE6EDF3),
        textMuted = Color.rgb(0x8B949E),
        tagBackground = SiteColors.TagBgDark,
        tagText = SiteColors.TagTextDark,
        cobweb = SiteColors.BorderDark,
    )
}
