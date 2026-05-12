package com.appylab.bloom.core.ui

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Blue palette — light theme
// #F0F3FA Cloud White  | #D5DEEF Periwinkle Mist | #B1C9EF Sky Steel
// #8AAEE0 Cornflower   | #638ECB Steel Blue       | #395886 Deep Navy

/** Cloud White — primary app background and card surface */
val AbyssNavy = Color(0xFFF0F3FA)
/** Cloud White — app base */
val DeepInk = Color(0xFFF0F3FA)
/** Cloud White — main card fill */
val PanelNavy = Color(0xFFF0F3FA)
/** Periwinkle Mist — secondary surface / layered card */
val RaisedMidnight = Color(0xFFD5DEEF)
/** Sky Steel — inactive bg, inactive tracks */
val MutedSteel = Color(0xFFB1C9EF)
/** Sky Steel — card borders, dividers */
val SlateBorder = Color(0xFFB1C9EF)
/** Cornflower Blue — positive emphasis, selected states, ring caps */
val BloomPeach = Color(0xFF8AAEE0)
/** Steel Blue — secondary accent, gradient stops */
val SoftPeach = Color(0xFF638ECB)
/** Deep Navy — primary action, progress rings, chart lines */
val ActivityRed = Color(0xFF395886)
/** Deep Navy — active accents, selected labels */
val HotRed = Color(0xFF395886)
/** Periwinkle Mist — soft shadow / depth layer */
val WineShadow = Color(0xFFD5DEEF)
/** Periwinkle Mist — header gradient start */
val HeaderBurgundy = Color(0xFFD5DEEF)
/** Near-black navy — primary text on light surfaces */
val TextWhite = Color(0xFF1A2A45)
/** Deep Navy — secondary readable text */
val MistText = Color(0xFF395886)
/** Steel Blue — muted captions, inactive labels */
val DimText = Color(0xFF638ECB)

fun screenBrush(): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.00f to Color(0xFFD5DEEF),
        0.18f to Color(0xFFB1C9EF),
        0.40f to Color(0xFFC8D8F0),
        0.65f to Color(0xFFE8EEF8),
        1.00f to Color(0xFFF0F3FA)
    )
)
