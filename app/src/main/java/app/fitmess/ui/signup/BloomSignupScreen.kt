package app.fitmess.ui.signup

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MonitorHeart
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun BloomSignupScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val darkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val colors = if (darkTheme) SignupColors.dark() else SignupColors.light()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .signupBackdrop(colors, darkTheme)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val compactHeight = maxHeight < 780.dp
        val horizontalPadding = if (maxWidth < 370.dp) 20.dp else 22.dp
        val headlineSize = if (maxWidth < 370.dp) 52.sp else 58.sp
        val headlineLineHeight = if (maxWidth < 370.dp) 58.sp else 66.sp

        FitnessOrbitArt(
            colors = colors,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 58.dp, y = if (compactHeight) 32.dp else 48.dp)
                .width(306.dp)
                .height(if (compactHeight) 410.dp else 500.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = horizontalPadding),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(if (compactHeight) 24.dp else 36.dp))
            BackButton(colors = colors, onBackClick = onBackClick)
            Spacer(Modifier.height(if (compactHeight) 76.dp else 92.dp))
            BrandLockup(colors = colors)
            Spacer(Modifier.height(if (compactHeight) 36.dp else 52.dp))
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
            Spacer(Modifier.height(22.dp))
            Text(
                text = "Move More,\nLive Better.",
                color = colors.subtitle,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp
            )
            Spacer(Modifier.height(if (compactHeight) 58.dp else 92.dp))
            SignupCard(colors = colors) {
                Text(
                    text = "Create your account",
                    color = colors.cardTitle,
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Join Bloom and start your fitness journey",
                    color = colors.cardSubtitle,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
                Spacer(Modifier.height(22.dp))
                SignupField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = "Enter your full name",
                    icon = Icons.Rounded.PersonOutline,
                    colors = colors
                )
                Spacer(Modifier.height(17.dp))
                SignupField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter your email",
                    icon = Icons.Rounded.Email,
                    colors = colors,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(17.dp))
                SignupField(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Create a password",
                    icon = Icons.Rounded.Lock,
                    colors = colors,
                    keyboardType = KeyboardType.Password,
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
                )
                Spacer(Modifier.height(17.dp))
                SignupField(
                    label = "Confirm Password",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Confirm your password",
                    icon = Icons.Rounded.Lock,
                    colors = colors,
                    keyboardType = KeyboardType.Password,
                    passwordVisible = confirmPasswordVisible,
                    onPasswordVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
                )
                Spacer(Modifier.height(28.dp))
                PrimarySignupButton(colors = colors)
                Spacer(Modifier.height(24.dp))
                DividerLabel(colors = colors)
                Spacer(Modifier.height(20.dp))
                GoogleAuthButton(colors = colors)
                Spacer(Modifier.height(26.dp))
                LoginRow(colors = colors, onLoginClick = onLoginClick)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BackButton(
    colors: SignupColors,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .shadow(
                elevation = 14.dp,
                shape = CircleShape,
                ambientColor = colors.backShadow,
                spotColor = colors.backShadow
            )
            .clip(CircleShape)
            .background(colors.backButton)
            .border(1.dp, colors.backBorder, CircleShape)
            .clickable(onClick = onBackClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.ChevronLeft,
            contentDescription = "Back",
            tint = colors.actionIcon,
            modifier = Modifier.size(34.dp)
        )
    }
}

@Composable
private fun BrandLockup(colors: SignupColors) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        BloomBadge(colors = colors, modifier = Modifier.size(62.dp))
        Text(
            text = "Bloom",
            color = colors.wordmarkFallback,
            fontSize = 34.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.sp,
            style = TextStyle(
                brush = Brush.verticalGradient(colors.wordmarkGradient)
            )
        )
    }
}

@Composable
private fun BloomBadge(colors: SignupColors, modifier: Modifier = Modifier) {
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
private fun SignupCard(
    colors: SignupColors,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(26.dp),
                ambientColor = colors.cardShadow,
                spotColor = colors.cardShadow
            )
            .clip(RoundedCornerShape(26.dp))
            .background(colors.card)
            .border(1.dp, colors.cardBorder, RoundedCornerShape(26.dp))
            .padding(horizontal = 18.dp, vertical = 22.dp),
        content = content
    )
}

@Composable
private fun SignupField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    colors: SignupColors,
    keyboardType: KeyboardType = KeyboardType.Text,
    passwordVisible: Boolean = true,
    onPasswordVisibilityChange: (() -> Unit)? = null
) {
    Column {
        Text(
            text = label,
            color = colors.fieldLabel,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.field)
                .border(1.dp, colors.fieldBorder, RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.fieldIcon,
                modifier = Modifier.size(27.dp)
            )
            Spacer(Modifier.width(16.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = colors.inputText,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = colors.placeholder,
                                fontSize = 18.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 0.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (onPasswordVisibilityChange != null) {
                Spacer(Modifier.width(10.dp))
                Icon(
                    imageVector = if (passwordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = colors.fieldIcon,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(onClick = onPasswordVisibilityChange)
                )
            }
        }
    }
}

@Composable
private fun PrimarySignupButton(colors: SignupColors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.horizontalGradient(colors.primaryButtonGradient))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Create Account",
            color = Color.White,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun DividerLabel(colors: SignupColors) {
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
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(72.dp)
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
private fun GoogleAuthButton(colors: SignupColors) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(17.dp))
            .background(colors.googleButton)
            .border(1.dp, colors.googleButtonBorder, RoundedCornerShape(17.dp))
            .clickable { }
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        GoogleGlyph()
        Spacer(Modifier.width(18.dp))
        Text(
            text = "Continue with Google",
            color = colors.googleText,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Bold,
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
        fontSize = 30.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.sp
    )
}

@Composable
private fun LoginRow(
    colors: SignupColors,
    onLoginClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onLoginClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Already have an account?",
            color = colors.footerMuted,
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = "Log in",
            color = colors.actionIcon,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
        Spacer(Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = colors.actionIcon,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Composable
private fun BoxScope.FitnessOrbitArt(
    colors: SignupColors,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawOrbitBackground(colors)
        }
        IconBubble(
            icon = Icons.Rounded.FitnessCenter,
            colors = colors,
            size = 150.dp,
            iconSize = 82.dp,
            modifier = Modifier.offset(x = 64.dp, y = 54.dp)
        )
        IconBubble(
            icon = Icons.AutoMirrored.Rounded.DirectionsRun,
            colors = colors,
            size = 88.dp,
            iconSize = 48.dp,
            modifier = Modifier.offset(x = 126.dp, y = 228.dp)
        )
        IconBubble(
            icon = Icons.Rounded.MonitorHeart,
            colors = colors,
            size = 72.dp,
            iconSize = 42.dp,
            modifier = Modifier.offset(x = 86.dp, y = 324.dp)
        )
        IconBubble(
            icon = Icons.Rounded.FitnessCenter,
            colors = colors,
            size = 136.dp,
            iconSize = 78.dp,
            modifier = Modifier.offset(x = 112.dp, y = 410.dp)
        )
    }
}

@Composable
private fun IconBubble(
    icon: ImageVector,
    colors: SignupColors,
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

private fun Modifier.signupBackdrop(colors: SignupColors, darkTheme: Boolean): Modifier {
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
    }
}

private fun DrawScope.drawOrbitBackground(colors: SignupColors) {
    drawCircle(
        color = colors.largeOrb,
        radius = size.maxDimension * 0.44f,
        center = Offset(size.width * 0.82f, size.height * 0.14f)
    )
    drawCircle(
        color = colors.orbitLine,
        radius = size.minDimension * 0.36f,
        center = Offset(size.width * 0.52f, size.height * 0.22f),
        style = Stroke(width = 1.1.dp.toPx())
    )
    drawCircle(
        color = colors.orbitLine.copy(alpha = colors.orbitLine.alpha * 0.72f),
        radius = size.minDimension * 0.50f,
        center = Offset(size.width * 0.52f, size.height * 0.22f),
        style = Stroke(width = 0.9.dp.toPx())
    )
    drawCircle(
        color = colors.orbitLine,
        radius = size.minDimension * 0.45f,
        center = Offset(size.width * 0.64f, size.height * 0.84f),
        style = Stroke(width = 1.1.dp.toPx())
    )
    drawCircle(
        color = colors.orbitLine.copy(alpha = colors.orbitLine.alpha * 0.62f),
        radius = size.minDimension * 0.60f,
        center = Offset(size.width * 0.64f, size.height * 0.84f),
        style = Stroke(width = 0.8.dp.toPx())
    )

    val sparklePoints = listOf(
        Offset(size.width * 0.04f, size.height * 0.19f),
        Offset(size.width * 0.70f, size.height * 0.02f),
        Offset(size.width * 0.95f, size.height * 0.28f),
        Offset(size.width * 0.80f, size.height * 0.45f),
        Offset(size.width * 0.26f, size.height * 0.62f),
        Offset(size.width * 0.10f, size.height * 0.84f),
        Offset(size.width * 0.92f, size.height * 0.93f)
    )
    sparklePoints.forEachIndexed { index, point ->
        drawSparkle(
            center = point,
            color = colors.sparkle,
            radius = if (index == 0 || index == sparklePoints.lastIndex) 6.dp.toPx() else 3.dp.toPx(),
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

private fun DrawScope.drawBloomLogo(colors: SignupColors, scale: Float) {
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
private data class SignupColors(
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
    val largeOrb: Color,
    val orbitLine: Color,
    val sparkle: Color,
    val bubbleFill: Color,
    val bubbleBorder: Color,
    val bubbleGlow: List<Color>,
    val backButton: Color,
    val backBorder: Color,
    val backShadow: Color,
    val card: Color,
    val cardBorder: Color,
    val cardShadow: Color,
    val cardTitle: Color,
    val cardSubtitle: Color,
    val field: Color,
    val fieldBorder: Color,
    val fieldLabel: Color,
    val fieldIcon: Color,
    val inputText: Color,
    val placeholder: Color,
    val primaryButtonGradient: List<Color>,
    val divider: Color,
    val orText: Color,
    val googleButton: Color,
    val googleButtonBorder: Color,
    val googleText: Color,
    val actionIcon: Color,
    val footerMuted: Color
) {
    companion object {
        fun light() = SignupColors(
            background = WarmMilkCanvas,
            headline = PrimaryInk,
            headlineAccent = BloomCoralFlame,
            subtitle = MutedWarmGray,
            wordmarkFallback = BloomCoralFlame,
            wordmarkGradient = listOf(BloomCoralFlame, FreshCoralHighlight),
            logoGradient = listOf(FreshCoralHighlight, BloomCoralFlame, PressedEmberRed),
            badgeBorder = BloomCoralFlame.copy(alpha = 0.58f),
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
            backButton = Color.White.copy(alpha = 0.9f),
            backBorder = Color.White.copy(alpha = 0.7f),
            backShadow = SoftPetalCoral.copy(alpha = 0.22f),
            card = Color.White.copy(alpha = 0.9f),
            cardBorder = Color.White.copy(alpha = 0.65f),
            cardShadow = SoftPetalCoral.copy(alpha = 0.18f),
            cardTitle = PrimaryInk,
            cardSubtitle = MutedWarmGray,
            field = Color.White.copy(alpha = 0.5f),
            fieldBorder = PeachHairlineBorder.copy(alpha = 0.95f),
            fieldLabel = PrimaryInk,
            fieldIcon = MutedWarmGray.copy(alpha = 0.92f),
            inputText = PrimaryInk,
            placeholder = MutedWarmGray.copy(alpha = 0.82f),
            primaryButtonGradient = listOf(BloomCoralFlame, FreshCoralHighlight, BloomCoralFlame),
            divider = PeachHairlineBorder.copy(alpha = 0.9f),
            orText = MutedWarmGray,
            googleButton = Color.White.copy(alpha = 0.64f),
            googleButtonBorder = PeachHairlineBorder.copy(alpha = 0.74f),
            googleText = PrimaryInk,
            actionIcon = FreshCoralHighlight,
            footerMuted = MutedWarmGray
        )

        fun dark() = SignupColors(
            background = MidnightPlumCanvas,
            headline = Color.White,
            headlineAccent = FreshCoralHighlight,
            subtitle = SoftAshText,
            wordmarkFallback = SoftPetalCoral,
            wordmarkGradient = listOf(SoftPetalCoral, FreshCoralHighlight),
            logoGradient = listOf(SoftPetalCoral, FreshCoralHighlight),
            badgeBorder = BloomCoralFlame.copy(alpha = 0.74f),
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
            backButton = SmokedCardSurface.copy(alpha = 0.58f),
            backBorder = RosewoodBorder.copy(alpha = 0.92f),
            backShadow = BloomCoralFlame.copy(alpha = 0.2f),
            card = Color(0xF20A090D),
            cardBorder = Color(0xFF312B34),
            cardShadow = BloomCoralFlame.copy(alpha = 0.16f),
            cardTitle = Color.White,
            cardSubtitle = MutedRoseGray,
            field = Color.Black.copy(alpha = 0.1f),
            fieldBorder = RosewoodBorder.copy(alpha = 0.82f),
            fieldLabel = Color.White,
            fieldIcon = SoftAshText.copy(alpha = 0.78f),
            inputText = Color.White,
            placeholder = MutedRoseGray,
            primaryButtonGradient = listOf(FreshCoralHighlight, BloomCoralFlame, FreshCoralHighlight),
            divider = RosewoodBorder.copy(alpha = 0.76f),
            orText = MutedRoseGray,
            googleButton = Color.Black.copy(alpha = 0.08f),
            googleButtonBorder = RosewoodBorder.copy(alpha = 0.84f),
            googleText = Color.White,
            actionIcon = FreshCoralHighlight,
            footerMuted = SoftAshText.copy(alpha = 0.85f)
        )
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomSignupScreenLightPreview() {
    FitMessTheme(darkTheme = false) {
        BloomSignupScreen()
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomSignupScreenDarkPreview() {
    FitMessTheme(darkTheme = true) {
        BloomSignupScreen()
    }
}
