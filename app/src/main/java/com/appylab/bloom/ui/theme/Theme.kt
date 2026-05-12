package com.appylab.bloom.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

private val BloomColorScheme = lightColorScheme(
    primary                = BloomPrimary,
    onPrimary              = BloomOnPrimary,
    primaryContainer       = BloomPrimaryContainer,
    onPrimaryContainer     = BloomOnPrimaryContainer,

    secondary              = BloomSecondary,
    onSecondary            = BloomOnSecondary,
    secondaryContainer     = BloomSecondaryContainer,
    onSecondaryContainer   = BloomOnSecondaryContainer,

    tertiary               = BloomTertiary,
    onTertiary             = BloomOnTertiary,
    tertiaryContainer      = BloomTertiaryContainer,
    onTertiaryContainer    = BloomOnTertiaryContainer,

    background             = BloomBackground,
    onBackground           = BloomOnBackground,

    surface                = BloomSurface,
    onSurface              = BloomOnSurface,
    surfaceVariant         = BloomSurfaceVariant,
    onSurfaceVariant       = BloomOnSurfaceVariant,

    outline                = BloomOutline,
    outlineVariant         = BloomOutlineVariant,

    error                  = BloomError,
    onError                = BloomOnError,
    errorContainer         = BloomErrorContainer,
    onErrorContainer       = BloomOnErrorContainer,
)

private val BloomShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small      = RoundedCornerShape(8.dp),
    medium     = RoundedCornerShape(16.dp),
    large      = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun BloomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BloomColorScheme,
        typography  = Typography,
        shapes      = BloomShapes,
        content     = content
    )
}
