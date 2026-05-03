package app.fitmess.ui.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Whatshot
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.fitmess.ui.theme.BloomCoralFlame
import app.fitmess.ui.theme.DeepWineGlow
import app.fitmess.ui.theme.FitMessTheme
import app.fitmess.ui.theme.FreshCoralHighlight
import app.fitmess.ui.theme.MidnightPlumCanvas
import app.fitmess.ui.theme.MutedRoseGray
import app.fitmess.ui.theme.PressedEmberRed
import app.fitmess.ui.theme.PrimaryInk
import app.fitmess.ui.theme.SoftAshText
import app.fitmess.ui.theme.SoftPetalCoral
import app.fitmess.ui.theme.WarmMilkCanvas

@Composable
fun BloomSplashScreen(modifier: Modifier = Modifier) {
    val darkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val colors = if (darkTheme) SplashColors.dark() else SplashColors.light()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .splashBackdrop(colors, darkTheme)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        val logoSize = (maxWidth * 0.36f).coerceAtMost(148.dp)
        val topGap = if (maxHeight < 760.dp) 88.dp else maxHeight * 0.15f
        val bottomGap = if (maxHeight < 760.dp) 44.dp else 68.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(topGap))
            BloomOrbitLogo(
                colors = colors,
                modifier = Modifier.size(logoSize)
            )
            Spacer(Modifier.height(10.dp))
            BloomWordmark(colors = colors)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Let's keep growing.",
                color = colors.subtitle,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(28.dp))
            WellnessIconRow(colors = colors)
            Spacer(Modifier.weight(1f))
            LoadingBlock(colors = colors)
            Spacer(Modifier.height(bottomGap))
        }
    }
}

@Composable
private fun BloomWordmark(colors: SplashColors) {
    Text(
        text = "Bloom",
        color = colors.wordmarkFallback,
        fontSize = 60.sp,
        lineHeight = 66.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.sp,
        style = TextStyle(
            brush = Brush.verticalGradient(colors.wordmarkGradient)
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BloomOrbitLogo(
    colors: SplashColors,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val center = this.center
        val radius = size.minDimension * 0.25f
        val orbitStroke = size.minDimension * 0.0065f

        drawCircle(
            color = colors.logoHalo,
            radius = radius * 0.98f,
            center = center,
            style = Stroke(width = size.minDimension * 0.011f)
        )
        drawCircle(
            color = colors.outerOrbit,
            radius = size.minDimension * 0.36f,
            center = center,
            style = Stroke(width = orbitStroke)
        )
        drawCircle(
            color = colors.outerOrbit.copy(alpha = colors.outerOrbit.alpha * 0.55f),
            radius = size.minDimension * 0.48f,
            center = center,
            style = Stroke(width = orbitStroke * 0.72f)
        )
        drawArc(
            color = colors.logoHalo,
            startAngle = -30f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2f, radius * 2f),
            style = Stroke(width = size.minDimension * 0.006f, cap = StrokeCap.Round)
        )
        drawBloomLogo(colors)
        drawOrbitSparkles(colors)
    }
}

private fun DrawScope.drawBloomLogo(colors: SplashColors) {
    val center = this.center
    val petalWidth = size.minDimension * 0.19f
    val petalHeight = size.minDimension * 0.28f
    val strokeWidth = size.minDimension * 0.035f
    val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
    val logoBrush = Brush.verticalGradient(
        colors = colors.logoGradient,
        startY = center.y - petalHeight,
        endY = center.y + petalHeight
    )

    listOf(-50f, 0f, 50f, -108f, 108f).forEachIndexed { index, angle ->
        rotate(degrees = angle, pivot = center) {
            val petal = Path().apply {
                moveTo(center.x, center.y + petalHeight * 0.48f)
                cubicTo(
                    center.x - petalWidth * 0.64f,
                    center.y + petalHeight * 0.14f,
                    center.x - petalWidth * 0.48f,
                    center.y - petalHeight * 0.38f,
                    center.x,
                    center.y - petalHeight * 0.58f
                )
                cubicTo(
                    center.x + petalWidth * 0.48f,
                    center.y - petalHeight * 0.38f,
                    center.x + petalWidth * 0.64f,
                    center.y + petalHeight * 0.14f,
                    center.x,
                    center.y + petalHeight * 0.48f
                )
            }
            drawPath(
                path = petal,
                brush = logoBrush,
                style = stroke,
                alpha = if (index == 1) 1f else 0.96f
            )
        }
    }

    val heart = Path().apply {
        moveTo(center.x - petalWidth * 0.52f, center.y - petalHeight * 0.20f)
        cubicTo(
            center.x - petalWidth * 0.24f,
            center.y - petalHeight * 0.46f,
            center.x,
            center.y - petalHeight * 0.30f,
            center.x,
            center.y - petalHeight * 0.03f
        )
        cubicTo(
            center.x,
            center.y - petalHeight * 0.30f,
            center.x + petalWidth * 0.24f,
            center.y - petalHeight * 0.46f,
            center.x + petalWidth * 0.52f,
            center.y - petalHeight * 0.20f
        )
    }
    drawPath(path = heart, brush = logoBrush, style = stroke)
}

private fun DrawScope.drawOrbitSparkles(colors: SplashColors) {
    val center = this.center
    val orbitRadius = size.minDimension * 0.43f
    val diamond = size.minDimension * 0.014f
    val sparkleAngles = listOf(-130f, -68f, -18f, 30f, 96f, 154f)

    sparkleAngles.forEachIndexed { index, degrees ->
        val radians = Math.toRadians(degrees.toDouble())
        val sparkleCenter = Offset(
            x = center.x + kotlin.math.cos(radians).toFloat() * orbitRadius,
            y = center.y + kotlin.math.sin(radians).toFloat() * orbitRadius
        )
        rotate(degrees = 45f, pivot = sparkleCenter) {
            drawRect(
                color = colors.sparkle.copy(alpha = if (index % 2 == 0) 1f else 0.55f),
                topLeft = Offset(sparkleCenter.x - diamond, sparkleCenter.y - diamond),
                size = Size(diamond * 2f, diamond * 2f),
                style = if (index == 0 || index == 2) {
                    Stroke(width = diamond * 0.45f, join = StrokeJoin.Round)
                } else {
                    Stroke(width = diamond * 0.2f, join = StrokeJoin.Round)
                }
            )
        }
    }

    drawArc(
        color = colors.sparkle,
        startAngle = -50f,
        sweepAngle = 245f,
        useCenter = false,
        topLeft = Offset(
            center.x + orbitRadius * 0.88f,
            center.y - orbitRadius * 0.78f
        ),
        size = Size(28.dp.toPx(), 28.dp.toPx()),
        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
    )
}

@Composable
private fun WellnessIconRow(colors: SplashColors) {
    val items = listOf(
        WellnessIcon(Icons.AutoMirrored.Rounded.DirectionsRun, false),
        WellnessIcon(Icons.Rounded.FitnessCenter, false),
        WellnessIcon(Icons.Rounded.FavoriteBorder, true),
        WellnessIcon(Icons.Rounded.Whatshot, false),
        WellnessIcon(Icons.Rounded.Eco, false)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = if (item.active) colors.activeIcon else colors.inactiveIcon,
                    modifier = Modifier.size(34.dp)
                )
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(3.dp)
                        .background(
                            color = if (item.active) colors.activeIcon else Color.Transparent,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                )
            }
        }
    }
}

@Composable
private fun LoadingBlock(colors: SplashColors) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = colors.activeIcon,
            strokeWidth = 3.dp,
            trackColor = Color.Transparent
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Loading your best self...",
            color = colors.loadingText,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

private fun Modifier.splashBackdrop(colors: SplashColors, darkTheme: Boolean): Modifier {
    return drawBehind {
        drawCircle(
            brush = Brush.radialGradient(
                colors = colors.primaryGlow,
                center = Offset(size.width * 0.16f, 0f),
                radius = size.maxDimension * if (darkTheme) 0.66f else 0.46f
            ),
            radius = size.maxDimension * if (darkTheme) 0.66f else 0.46f,
            center = Offset(size.width * 0.16f, 0f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = colors.bottomGlow,
                center = Offset(size.width * 0.96f, size.height * 0.98f),
                radius = size.maxDimension * if (darkTheme) 0.48f else 0.36f
            ),
            radius = size.maxDimension * if (darkTheme) 0.48f else 0.36f,
            center = Offset(size.width * 0.96f, size.height * 0.98f)
        )
    }
}

@Immutable
private data class WellnessIcon(
    val icon: ImageVector,
    val active: Boolean
)

@Immutable
private data class SplashColors(
    val background: Color,
    val subtitle: Color,
    val loadingText: Color,
    val wordmarkFallback: Color,
    val wordmarkGradient: List<Color>,
    val logoGradient: List<Color>,
    val logoHalo: Color,
    val outerOrbit: Color,
    val sparkle: Color,
    val activeIcon: Color,
    val inactiveIcon: Color,
    val primaryGlow: List<Color>,
    val bottomGlow: List<Color>
) {
    companion object {
        fun light() = SplashColors(
            background = WarmMilkCanvas,
            subtitle = PrimaryInk.copy(alpha = 0.86f),
            loadingText = PrimaryInk.copy(alpha = 0.88f),
            wordmarkFallback = PressedEmberRed,
            wordmarkGradient = listOf(PressedEmberRed, BloomCoralFlame, FreshCoralHighlight),
            logoGradient = listOf(SoftPetalCoral, FreshCoralHighlight, BloomCoralFlame),
            logoHalo = SoftPetalCoral.copy(alpha = 0.78f),
            outerOrbit = SoftPetalCoral.copy(alpha = 0.28f),
            sparkle = FreshCoralHighlight,
            activeIcon = BloomCoralFlame,
            inactiveIcon = SoftPetalCoral.copy(alpha = 0.74f),
            primaryGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.42f),
                SoftPetalCoral.copy(alpha = 0.12f),
                Color.Transparent
            ),
            bottomGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.26f),
                SoftPetalCoral.copy(alpha = 0.08f),
                Color.Transparent
            )
        )

        fun dark() = SplashColors(
            background = MidnightPlumCanvas,
            subtitle = SoftAshText,
            loadingText = SoftAshText,
            wordmarkFallback = SoftPetalCoral,
            wordmarkGradient = listOf(Color.White, SoftPetalCoral, BloomCoralFlame),
            logoGradient = listOf(SoftPetalCoral, FreshCoralHighlight, BloomCoralFlame),
            logoHalo = FreshCoralHighlight.copy(alpha = 0.9f),
            outerOrbit = FreshCoralHighlight.copy(alpha = 0.16f),
            sparkle = FreshCoralHighlight,
            activeIcon = FreshCoralHighlight,
            inactiveIcon = MutedRoseGray.copy(alpha = 0.48f),
            primaryGlow = listOf(
                DeepWineGlow.copy(alpha = 0.96f),
                BloomCoralFlame.copy(alpha = 0.23f),
                Color.Transparent
            ),
            bottomGlow = listOf(
                DeepWineGlow.copy(alpha = 0.72f),
                Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomSplashScreenLightPreview() {
    FitMessTheme(darkTheme = false) {
        BloomSplashScreen()
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomSplashScreenDarkPreview() {
    FitMessTheme(darkTheme = true) {
        BloomSplashScreen()
    }
}
