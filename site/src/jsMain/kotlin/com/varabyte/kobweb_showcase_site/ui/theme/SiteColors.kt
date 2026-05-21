package com.varabyte.kobweb_showcase_site.ui.theme

import com.varabyte.kobweb.compose.ui.graphics.Color

object SiteColors {
    // Kobweb brand
    val KobwebBlue = Color.rgb(0x3C83EF)
    val KobwebGold = Color.rgb(0xF3DB5B)

    // Final Fantasy / Mako theme — dark
    val MakoGreen       = Color.rgb(0x00FF87)   // primary accent (dark mode)
    val MakoDeep        = Color.rgb(0x00CC6A)   // primary hover (dark mode)
    val MakoSubtle      = Color.rgb(0x003D28)   // very dark green tint for surfaces

    // Dark mode surfaces
    val NightBlue       = Color.rgb(0x06080B)   // deepest bg
    val SlateBlue       = Color.rgb(0x0D1117)   // nav / footer bg
    val SlateBlueLight  = Color.rgb(0x13171F)   // card surface
    val SlateBlueHover  = Color.rgb(0x1C2230)   // card hover surface
    val BorderDark      = Color.rgb(0x21262D)   // subtle borders

    // Light mode surfaces
    val CloudWhite      = Color.rgb(0xF0F4FF)   // page bg
    val NeutralLight    = Color.rgb(0xF4F6FA)   // nav / footer bg
    val SurfaceLight    = Color.rgb(0xFFFFFF)   // card bg
    val BorderLight     = Color.rgb(0xD0D7DE)   // subtle borders

    // Primary accent (light mode uses a darker shade for contrast)
    val MakoGreenDark   = Color.rgb(0x007A50)   // primary (light mode)
    val MakoDeepDark    = Color.rgb(0x005C3A)   // hover (light mode)

    // Tag colors
    val TagBgDark       = Color.rgb(0x1A3A2A)
    val TagTextDark     = Color.rgb(0x00FF87)
    val TagBgLight      = Color.rgb(0xE0F7EC)
    val TagTextLight    = Color.rgb(0x005C3A)

    // Skeleton shimmer
    val SkeletonBase    = Color.rgb(0x161B22)
    val SkeletonShimmer = Color.rgb(0x21262D)
}
