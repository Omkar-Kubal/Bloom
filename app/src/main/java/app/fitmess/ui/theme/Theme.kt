package app.fitmess.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BloomCoralFlame,
    secondary = FreshCoralHighlight,
    tertiary = SoftPetalCoral,
    background = MidnightPlumCanvas,
    surface = SmokedCardSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = MidnightPlumCanvas,
    onBackground = Color.White,
    onSurface = SoftAshText,
    outline = RosewoodBorder
)

private val LightColorScheme = lightColorScheme(
    primary = BloomCoralFlame,
    secondary = FreshCoralHighlight,
    tertiary = SoftPetalCoral,
    background = WarmMilkCanvas,
    surface = PearlCardSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = PrimaryInk,
    onBackground = PrimaryInk,
    onSurface = SoftCharcoal,
    outline = PeachHairlineBorder
)

@Composable
fun FitMessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
