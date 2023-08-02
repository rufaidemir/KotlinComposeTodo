package com.rufaidemir.examplecompose.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.maxkeppeler.sheets.color.models.MultipleColors

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val templateColors = MultipleColors.ColorsInt(
    Color.Red.copy(alpha = 0.1f).toArgb(),
    Color.Red.copy(alpha = 0.3f).toArgb(),
    Color.Red.copy(alpha = 0.5f).toArgb(),
    Color.Red.toArgb(),
    Color.Green.copy(alpha = 0.1f).toArgb(),
    Color.Green.copy(alpha = 0.3f).toArgb(),
    Color.Green.copy(alpha = 0.5f).toArgb(),
    Color.Green.toArgb(),
    Color.Yellow.toArgb(),
    Color.Cyan.toArgb(),
    Color.LightGray.toArgb(),
    Color.Gray.toArgb(),
    Color.DarkGray.toArgb(),
    Color.Magenta.toArgb(),
)