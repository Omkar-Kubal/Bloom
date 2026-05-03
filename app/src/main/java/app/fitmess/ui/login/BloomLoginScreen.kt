package app.fitmess.ui.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.LocalPhone
import androidx.compose.material.icons.rounded.MonitorHeart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
import app.fitmess.ui.theme.MutedWarmGray
import app.fitmess.ui.theme.PeachHairlineBorder
import app.fitmess.ui.theme.PearlCardSurface
import app.fitmess.ui.theme.PressedEmberRed
import app.fitmess.ui.theme.PrimaryInk
import app.fitmess.ui.theme.RosewoodBorder
import app.fitmess.ui.theme.SmokedCardSurface
import app.fitmess.ui.theme.SoftAshText
import app.fitmess.ui.theme.SoftPetalCoral
import app.fitmess.ui.theme.WarmMilkCanvas

@Composable
fun BloomLoginScreen(
    modifier: Modifier = Modifier,
    isGoogleContinuePending: Boolean = false,
    onGoogleContinueClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {}
) {
    val darkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val colors = if (darkTheme) LoginColors.dark() else LoginColors.light()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .loginBackdrop(colors, darkTheme)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val compactHeight = maxHeight < 780.dp
        val horizontalPadding = if (maxWidth < 370.dp) 24.dp else 28.dp
        val headlineSize = if (maxWidth < 370.dp) 46.sp else 52.sp
        val headlineLineHeight = if (maxWidth < 370.dp) 52.sp else 58.sp

        FitnessOrbitArt(
            colors = colors,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 70.dp, y = if (compactHeight) 40.dp else 62.dp)
                .width(300.dp)
                .height(if (compactHeight) 440.dp else 520.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = horizontalPadding),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(if (compactHeight) 72.dp else 104.dp))
            BrandLockup(colors = colors)
            Spacer(Modifier.height(if (compactHeight) 20.dp else 32.dp))
            Text(
                text = buildAnnotatedString {
                    append("Stronger\nEvery\n")
                    withStyle(SpanStyle(color = colors.headlineAccent)) {
                        append("Day.")
                    }
                },
                color = colors.headline,
                fontSize = headlineSize,
                lineHeight = headlineLineHeight,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.sp
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Move More,\nLive Better.",
                color = colors.subtitle,
                fontSize = 17.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp
            )
            Spacer(Modifier.height(if (compactHeight) 44.dp else 72.dp))
            GoogleAuthButton(
                colors = colors,
                isPending = isGoogleContinuePending,
                onClick = onGoogleContinueClick
            )
            Spacer(Modifier.height(24.dp))
            DividerLabel(colors = colors)
            Spacer(Modifier.height(24.dp))
            OutlinedAuthButton(
                text = "Continue with Email",
                icon = Icons.Rounded.Email,
                colors = colors
            )
            Spacer(Modifier.height(16.dp))
            OutlinedAuthButton(
                text = "Continue with Phone",
                icon = Icons.Rounded.LocalPhone,
                colors = colors
            )
            Spacer(Modifier.height(32.dp))
            CreateAccountRow(
                colors = colors,
                onCreateAccountClick = onCreateAccountClick
            )
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun BrandLockup(colors: LoginColors) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BloomBadge(colors = colors, modifier = Modifier.size(44.dp))
        Text(
            text = "Bloom",
            color = colors.wordmarkFallback,
            fontSize = 26.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.sp,
            style = TextStyle(
                brush = Brush.verticalGradient(colors.wordmarkGradient)
            )
        )
    }
}

@Composable
private fun BloomBadge(colors: LoginColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = colors.badgeBorder,
            radius = size.minDimension * 0.48f,
            center = center,
            style = Stroke(width = 1.2.dp.toPx())
        )
        drawBloomLogo(colors = colors, scale = 0.92f)
    }
}

@Composable
private fun GoogleAuthButton(
    colors: LoginColors,
    isPending: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(colors.googleButton)
            .border(1.dp, colors.googleButtonBorder, RoundedCornerShape(18.dp))
            .clickable(enabled = !isPending, onClick = onClick)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (isPending) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = colors.actionIcon,
                strokeWidth = 2.5.dp,
                trackColor = Color.Transparent
            )
        } else {
            GoogleGlyph()
        }
        Spacer(Modifier.width(14.dp))
        Text(
            text = if (isPending) "Connecting..." else "Continue with Google",
            color = PrimaryInk,
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun GoogleGlyph() {
    Text(
        text = "G",
        style = TextStyle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color(0xFF4285F4),
                    Color(0xFF34A853),
                    Color(0xFFFBBC05),
                    Color(0xFFEA4335),
                    Color(0xFF4285F4)
                )
            )
        ),
        fontSize = 26.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.sp
    )
}

@Composable
private fun DividerLabel(colors: LoginColors) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(colors.divider)
        )
        Text(
            text = "or",
            color = colors.orText,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(48.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(colors.divider)
        )
    }
}

@Composable
private fun OutlinedAuthButton(
    text: String,
    icon: ImageVector,
    colors: LoginColors
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(colors.outlineButton)
            .border(1.dp, colors.outlineBorder, RoundedCornerShape(18.dp))
            .clickable { }
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.actionIcon,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = text,
            color = colors.buttonText,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = colors.chevron,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun CreateAccountRow(
    colors: LoginColors,
    onCreateAccountClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCreateAccountClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "New here?",
            color = colors.createMuted,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = "Create account",
            color = colors.actionIcon,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
        )
        Spacer(Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = colors.actionIcon,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun BoxScope.FitnessOrbitArt(
    colors: LoginColors,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawOrbitBackground(colors)
        }
        IconBubble(
            icon = Icons.Rounded.FitnessCenter,
            colors = colors,
            size = 148.dp,
            iconSize = 82.dp,
            modifier = Modifier.offset(x = 54.dp, y = 62.dp)
        )
        IconBubble(
            icon = Icons.AutoMirrored.Rounded.DirectionsRun,
            colors = colors,
            size = 86.dp,
            iconSize = 48.dp,
            modifier = Modifier.offset(x = 116.dp, y = 250.dp)
        )
        IconBubble(
            icon = Icons.Rounded.MonitorHeart,
            colors = colors,
            size = 72.dp,
            iconSize = 42.dp,
            modifier = Modifier.offset(x = 74.dp, y = 346.dp)
        )
        IconBubble(
            icon = Icons.Rounded.FitnessCenter,
            colors = colors,
            size = 134.dp,
            iconSize = 78.dp,
            modifier = Modifier.offset(x = 100.dp, y = 430.dp)
        )
    }
}

@Composable
private fun IconBubble(
    icon: ImageVector,
    colors: LoginColors,
    size: Dp,
    iconSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = colors.bubbleGlow,
                        center = center,
                        radius = this.size.minDimension * 0.62f
                    ),
                    radius = this.size.minDimension * 0.62f
                )
            }
            .clip(CircleShape)
            .background(colors.bubbleFill)
            .border(1.dp, colors.bubbleBorder, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.actionIcon,
            modifier = Modifier.size(iconSize)
        )
    }
}

private fun Modifier.loginBackdrop(colors: LoginColors, darkTheme: Boolean): Modifier {
    return drawBehind {
        drawCircle(
            brush = Brush.radialGradient(
                colors = colors.topGlow,
                center = Offset(size.width * 0.08f, 0f),
                radius = size.maxDimension * if (darkTheme) 0.58f else 0.34f
            ),
            radius = size.maxDimension * if (darkTheme) 0.58f else 0.34f,
            center = Offset(size.width * 0.08f, 0f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = colors.bottomGlow,
                center = Offset(size.width * 0.16f, size.height * 0.72f),
                radius = size.maxDimension * if (darkTheme) 0.44f else 0.28f
            ),
            radius = size.maxDimension * if (darkTheme) 0.44f else 0.28f,
            center = Offset(size.width * 0.16f, size.height * 0.72f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = colors.cornerGlow,
                center = Offset(size.width * 1.08f, size.height * 0.98f),
                radius = size.maxDimension * 0.22f
            ),
            radius = size.maxDimension * 0.22f,
            center = Offset(size.width * 1.08f, size.height * 0.98f)
        )
    }
}

private fun DrawScope.drawOrbitBackground(colors: LoginColors) {
    drawCircle(
        color = colors.largeOrb,
        radius = size.maxDimension * 0.46f,
        center = Offset(size.width * 0.78f, size.height * 0.12f)
    )
    drawCircle(
        color = colors.orbitLine,
        radius = size.minDimension * 0.36f,
        center = Offset(size.width * 0.45f, size.height * 0.22f),
        style = Stroke(width = 1.2.dp.toPx())
    )
    drawCircle(
        color = colors.orbitLine.copy(alpha = colors.orbitLine.alpha * 0.72f),
        radius = size.minDimension * 0.50f,
        center = Offset(size.width * 0.45f, size.height * 0.22f),
        style = Stroke(width = 1.dp.toPx())
    )
    drawCircle(
        color = colors.orbitLine,
        radius = size.minDimension * 0.45f,
        center = Offset(size.width * 0.58f, size.height * 0.84f),
        style = Stroke(width = 1.1.dp.toPx())
    )
    drawCircle(
        color = colors.orbitLine.copy(alpha = colors.orbitLine.alpha * 0.62f),
        radius = size.minDimension * 0.60f,
        center = Offset(size.width * 0.58f, size.height * 0.84f),
        style = Stroke(width = 0.8.dp.toPx())
    )

    val sparklePoints = listOf(
        Offset(size.width * 0.02f, size.height * 0.19f),
        Offset(size.width * 0.66f, size.height * 0.02f),
        Offset(size.width * 0.95f, size.height * 0.28f),
        Offset(size.width * 0.78f, size.height * 0.45f),
        Offset(size.width * 0.24f, size.height * 0.62f),
        Offset(size.width * 0.08f, size.height * 0.84f),
        Offset(size.width * 0.92f, size.height * 0.93f)
    )
    sparklePoints.forEachIndexed { index, point ->
        drawSparkle(
            center = point,
            color = colors.sparkle,
            radius = if (index == 0 || index == sparklePoints.lastIndex) 7.dp.toPx() else 3.dp.toPx(),
            outlined = index == 0 || index == sparklePoints.lastIndex
        )
    }

    drawArc(
        color = colors.sparkle,
        startAngle = 35f,
        sweepAngle = 300f,
        useCenter = false,
        topLeft = Offset(size.width * 0.82f, size.height * 0.08f),
        size = Size(24.dp.toPx(), 24.dp.toPx()),
        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
    )
    drawArc(
        color = colors.sparkle,
        startAngle = -36f,
        sweepAngle = 260f,
        useCenter = false,
        topLeft = Offset(size.width * 0.74f, size.height * 0.62f),
        size = Size(22.dp.toPx(), 22.dp.toPx()),
        style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawSparkle(
    center: Offset,
    color: Color,
    radius: Float,
    outlined: Boolean
) {
    rotate(degrees = 45f, pivot = center) {
        drawRect(
            color = color,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2f, radius * 2f),
            style = if (outlined) {
                Stroke(width = 2.dp.toPx(), join = StrokeJoin.Round)
            } else {
                Stroke(width = 1.dp.toPx(), join = StrokeJoin.Round)
            }
        )
    }
}

private fun DrawScope.drawBloomLogo(colors: LoginColors, scale: Float) {
    val center = this.center
    val petalWidth = size.minDimension * 0.19f * scale
    val petalHeight = size.minDimension * 0.27f * scale
    val strokeWidth = size.minDimension * 0.045f * scale
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
}

@Immutable
private data class LoginColors(
    val background: Color,
    val headline: Color,
    val headlineAccent: Color,
    val subtitle: Color,
    val wordmarkFallback: Color,
    val wordmarkGradient: List<Color>,
    val logoGradient: List<Color>,
    val badgeBorder: Color,
    val topGlow: List<Color>,
    val bottomGlow: List<Color>,
    val cornerGlow: List<Color>,
    val largeOrb: Color,
    val orbitLine: Color,
    val sparkle: Color,
    val bubbleFill: Color,
    val bubbleBorder: Color,
    val bubbleGlow: List<Color>,
    val googleButton: Color,
    val googleButtonBorder: Color,
    val outlineButton: Color,
    val outlineBorder: Color,
    val divider: Color,
    val orText: Color,
    val buttonText: Color,
    val actionIcon: Color,
    val chevron: Color,
    val createMuted: Color
) {
    companion object {
        fun light() = LoginColors(
            background = WarmMilkCanvas,
            headline = PrimaryInk,
            headlineAccent = BloomCoralFlame,
            subtitle = MutedWarmGray,
            wordmarkFallback = BloomCoralFlame,
            wordmarkGradient = listOf(BloomCoralFlame, FreshCoralHighlight),
            logoGradient = listOf(FreshCoralHighlight, BloomCoralFlame, PressedEmberRed),
            badgeBorder = BloomCoralFlame.copy(alpha = 0.56f),
            topGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.32f),
                SoftPetalCoral.copy(alpha = 0.10f),
                Color.Transparent
            ),
            bottomGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.25f),
                SoftPetalCoral.copy(alpha = 0.08f),
                Color.Transparent
            ),
            cornerGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.28f),
                Color.Transparent
            ),
            largeOrb = SoftPetalCoral.copy(alpha = 0.06f),
            orbitLine = SoftPetalCoral.copy(alpha = 0.38f),
            sparkle = FreshCoralHighlight,
            bubbleFill = Color.White.copy(alpha = 0.56f),
            bubbleBorder = SoftPetalCoral.copy(alpha = 0.46f),
            bubbleGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.38f),
                SoftPetalCoral.copy(alpha = 0.12f),
                Color.Transparent
            ),
            googleButton = Color.White.copy(alpha = 0.92f),
            googleButtonBorder = Color.White.copy(alpha = 0.72f),
            outlineButton = Color.White.copy(alpha = 0.46f),
            outlineBorder = PeachHairlineBorder.copy(alpha = 0.86f),
            divider = PeachHairlineBorder.copy(alpha = 0.9f),
            orText = MutedWarmGray,
            buttonText = PrimaryInk,
            actionIcon = FreshCoralHighlight,
            chevron = MutedWarmGray.copy(alpha = 0.72f),
            createMuted = MutedWarmGray
        )

        fun dark() = LoginColors(
            background = MidnightPlumCanvas,
            headline = Color.White,
            headlineAccent = FreshCoralHighlight,
            subtitle = SoftAshText,
            wordmarkFallback = SoftPetalCoral,
            wordmarkGradient = listOf(SoftPetalCoral, FreshCoralHighlight),
            logoGradient = listOf(SoftPetalCoral, FreshCoralHighlight),
            badgeBorder = BloomCoralFlame.copy(alpha = 0.72f),
            topGlow = listOf(
                DeepWineGlow.copy(alpha = 0.98f),
                BloomCoralFlame.copy(alpha = 0.24f),
                Color.Transparent
            ),
            bottomGlow = listOf(
                DeepWineGlow.copy(alpha = 0.72f),
                BloomCoralFlame.copy(alpha = 0.12f),
                Color.Transparent
            ),
            cornerGlow = listOf(
                DeepWineGlow.copy(alpha = 0.54f),
                Color.Transparent
            ),
            largeOrb = BloomCoralFlame.copy(alpha = 0.12f),
            orbitLine = BloomCoralFlame.copy(alpha = 0.28f),
            sparkle = FreshCoralHighlight,
            bubbleFill = SmokedCardSurface.copy(alpha = 0.54f),
            bubbleBorder = BloomCoralFlame.copy(alpha = 0.72f),
            bubbleGlow = listOf(
                BloomCoralFlame.copy(alpha = 0.36f),
                DeepWineGlow.copy(alpha = 0.24f),
                Color.Transparent
            ),
            googleButton = PearlCardSurface.copy(alpha = 0.96f),
            googleButtonBorder = Color.White.copy(alpha = 0.56f),
            outlineButton = Color.Black.copy(alpha = 0.08f),
            outlineBorder = RosewoodBorder.copy(alpha = 0.84f),
            divider = RosewoodBorder.copy(alpha = 0.76f),
            orText = MutedRoseGray,
            buttonText = Color.White,
            actionIcon = FreshCoralHighlight,
            chevron = SoftAshText.copy(alpha = 0.78f),
            createMuted = SoftAshText
        )
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomLoginScreenLightPreview() {
    FitMessTheme(darkTheme = false) {
        BloomLoginScreen()
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomLoginScreenDarkPreview() {
    FitMessTheme(darkTheme = true) {
        BloomLoginScreen()
    }
}
