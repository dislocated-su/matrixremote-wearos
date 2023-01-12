package com.example.matrixcontroller.presentation.theme

import androidx.compose.ui.graphics.Color


val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

internal val wearColorPalette: androidx.wear.compose.material.Colors = androidx.wear.compose.material.Colors(
        primary = Color(0xFFD0BCFF),
        primaryVariant = Purple700,
        secondary = Teal200,
        secondaryVariant = Teal200,
        error = Red400,
        onPrimary = Color(0xFF381E72),
        onSecondary = Color.Black,
        onError = Color.Black,
        surface = Color(0xFF202124),
        onSurface = Color(0xFFFFFFFF)
)

internal val tileColorPallete: androidx.wear.tiles.material.Colors = androidx.wear.tiles.material.Colors(
        /*primary=*/ 0xFFD0BCFF.toInt(), /*onPrimary=*/ 0xFF381E72.toInt(),
        /*surface=*/ 0xFF202124.toInt(), /*onSurface=*/ 0xFFFFFFFF.toInt()
)