package com.rufaidemir.examplecompose.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


fun hexToComposeColor(hexColor: String): Color {
    return Color(android.graphics.Color.parseColor( hexColor))
}

fun colorToHex(color: Color): String {
    return String.format("#%06X", (color.toArgb() and 0xFFFFFF))
}