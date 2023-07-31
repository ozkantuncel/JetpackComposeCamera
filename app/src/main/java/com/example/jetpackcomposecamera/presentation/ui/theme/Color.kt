package com.example.jetpackcomposecamera.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)


val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val ColorWhiteGray = Color(0xFFFAFAFA)
val AppBackgroundDarkColor = Color.Black


val ColorApp60 = Color(0xFF3AA247)
val ColorApp30 = Color(0xFF59A847)
val ColorApp10 = Color(0xFF819D3A)
val Color2App10 = Color(0xFFE6FFD6)

val ColorDark60 = Color(0xFF1E1E1E)
val ColorDark30 = Color(0xFF3AA247)
val ColorDark10 = Color(0xFF819D3A)
val Color2Dark10 = Color(0xFFC0C8BC)

val Colors.appScreenBackgroundColor
    @Composable
    get() = if (isLight) ColorWhiteGray else AppBackgroundDarkColor