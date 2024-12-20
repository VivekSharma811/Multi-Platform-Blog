package com.hypheno.blog.models

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba

enum class Theme(
    val hex: String,
    val rgb: CSSColorValue
) {
    Primary(
        hex = "#00A2FF",
        rgb = rgb(0, 162, 255)
    ),
    Secondary(
        hex = "#001019",
        rgb = rgb(0, 16, 25)
    ),
    Tertiary(
        hex = "#001925",
        rgb = rgb(r = 0, g = 25, b = 37)
    ),
    LightGray(
        hex = "#FAFAFA",
        rgb = rgb(250, 250, 250)
    ),
    HalfWhite(
        hex = "#FFFFFF",
        rgb = rgba(255, 255, 255, a = 0.5)
    ),
    HalfBlack(
        hex = "#000000",
        rgb = rgba(0, 0, 0, a = 0.5)
    ),
    White(
        hex = "#FFFFFF",
        rgb = rgb(255, 255, 255)
    ),
    Green(
        hex = "#00FF94",
        rgb = rgb(0, 255, 148)
    ),
    Yellow(
        hex = "#FFEC45",
        rgb = rgb(255, 236, 69)
    ),
    Red(
        hex = "#FF6359",
        rgb = rgb(255, 99, 89)
    ),
    Purple(
        hex = "#8B6DFF",
        rgb = rgb(139, 109, 255)
    ),
    Gray(
        hex = "#E9E9E9",
        rgb = rgb(233, 233, 233)
    ),
    DarkGray(
        hex = "#646464",
        rgb = rgb(100, 100, 100)
    ),
    Sponsored(
        hex = "#3300FF",
        rgb = rgb(r = 51, g = 0, b = 255)
    )
}
