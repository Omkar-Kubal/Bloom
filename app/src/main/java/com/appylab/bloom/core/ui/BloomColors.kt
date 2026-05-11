package com.appylab.bloom.core.ui

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val AbyssNavy = Color(0xFF171C2F)
val DeepInk = Color(0xFF001630)
val PanelNavy = Color(0xFF0E1828)
val RaisedMidnight = Color(0xFF142338)
val MutedSteel = Color(0xFF232F49)
val SlateBorder = Color(0xFF384358)
val BloomPeach = Color(0xFFFFA586)
val SoftPeach = Color(0xFFF49E85)
val ActivityRed = Color(0xFFE51A2B)
val HotRed = Color(0xFFFF3A3D)
val WineShadow = Color(0xFF561A22)
val HeaderBurgundy = Color(0xFF881C2C)
val TextWhite = Color(0xFFFCFBFB)
val MistText = Color(0xFFDDE3EE)
val DimText = Color(0xFF9AA4B6)

fun screenBrush(): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.00f to Color(0xFF4B0F24),
        0.12f to HeaderBurgundy,
        0.22f to ActivityRed,
        0.35f to BloomPeach,
        0.44f to DeepInk,
        1.00f to AbyssNavy
    )
)
