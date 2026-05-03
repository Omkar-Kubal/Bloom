package app.fitmess.ui.move

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import app.fitmess.ui.theme.SoftCharcoal
import app.fitmess.ui.theme.SoftPetalCoral
import app.fitmess.ui.theme.WarmMilkCanvas

@Composable
fun BloomMoveDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val darkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val colors = if (darkTheme) MoveDetailColors.dark() else MoveDetailColors.light()

    val today = remember { java.time.LocalDate.now() }
    val todayDayOfWeek = remember { today.dayOfWeek.value - 1 } // 0=Mon, 6=Sun
    val todayFormatted = remember {
        "Today, ${today.dayOfMonth} ${
            today.month.getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.getDefault())
        } ${today.year}"
    }

    // Mock data — replace with ViewModel state
    val activeKcal = 51
    val moveGoal = 200
    val progress = activeKcal.toFloat() / moveGoal
    val totalKcal = 1272
    val weekProgress = remember {
        List(7) { i -> if (i == todayDayOfWeek) progress else 0f }
    }
    val hourlyKcal = remember {
        List(24) { hour ->
            when (hour) {
                11 -> 9f; 12 -> 18f; 13 -> 13f; 14 -> 7f
                20 -> 30f; 21 -> 18f; 22 -> 12f; else -> 0f
            }
        }
    }
    val peakKcal = hourlyKcal.max().toInt()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 84.dp)
        ) {
            // ── Top bar ──────────────────────────────────────────────
            MoveTopBar(
                title = todayFormatted,
                colors = colors,
                onBackClick = onBackClick
            )
            Spacer(Modifier.height(16.dp))

            // ── Weekly ring strip ─────────────────────────────────────
            WeeklyRingStrip(
                colors = colors,
                weekProgress = weekProgress,
                todayIndex = todayDayOfWeek,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            )
            Spacer(Modifier.height(16.dp))

            // ── Main move card ────────────────────────────────────────
            MoveMainCard(
                colors = colors,
                activeKcal = activeKcal,
                moveGoal = moveGoal,
                progress = progress,
                hourlyKcal = hourlyKcal,
                peakKcal = peakKcal,
                totalKcal = totalKcal,
                modifier = Modifier.padding(horizontal = 22.dp)
            )
            Spacer(Modifier.height(12.dp))

            // ── Step stat cards ───────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StepStatCard(
                    title = "Step Count",
                    value = "2,591",
                    unit = null,
                    colors = colors,
                    modifier = Modifier.weight(1f)
                )
                StepStatCard(
                    title = "Step Distance",
                    value = "1.65",
                    unit = "KM",
                    colors = colors,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(12.dp))
        }

        // ── Sticky Edit Summary button ────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(colors.background)
                .navigationBarsPadding()
                .padding(horizontal = 22.dp, vertical = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.editButtonBg)
                    .border(1.dp, colors.accent, RoundedCornerShape(16.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit Summary",
                    color = colors.accent,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.sp
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Top bar
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MoveTopBar(
    title: String,
    colors: MoveDetailColors,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        // Back button — left
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
                .clip(CircleShape)
                .background(colors.card)
                .border(1.dp, colors.border, CircleShape)
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.ChevronLeft,
                contentDescription = "Back",
                tint = colors.primaryText,
                modifier = Modifier.size(22.dp)
            )
        }

        // Title — center
        Text(
            text = title,
            color = colors.primaryText,
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )

        // Right icons — calendar + share
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = "Calendar",
                tint = colors.secondaryText,
                modifier = Modifier
                    .size(40.dp)
                    .padding(9.dp)
            )
            Icon(
                imageVector = Icons.Rounded.IosShare,
                contentDescription = "Share",
                tint = colors.secondaryText,
                modifier = Modifier
                    .size(40.dp)
                    .padding(9.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Weekly ring strip
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun WeeklyRingStrip(
    colors: MoveDetailColors,
    weekProgress: List<Float>,
    todayIndex: Int,
    modifier: Modifier = Modifier
) {
    val dayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dayLabels.forEachIndexed { index, label ->
            val isToday = index == todayIndex
            val fillFraction = weekProgress.getOrElse(index) { 0f }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Day label circle (coral filled for today, plain for others)
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (isToday) colors.accent else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isToday) Color.White else colors.tertiaryText,
                        fontSize = 11.sp,
                        lineHeight = 14.sp,
                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                        letterSpacing = 0.sp
                    )
                }
                // Mini ring
                Canvas(modifier = Modifier.size(40.dp)) {
                    val strokeWidth = 3.5.dp.toPx()
                    val inset = strokeWidth / 2f
                    // Track
                    drawArc(
                        color = colors.track,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = Offset(inset, inset),
                        size = Size(size.width - strokeWidth, size.height - strokeWidth),
                        style = Stroke(strokeWidth, cap = StrokeCap.Round)
                    )
                    // Fill
                    if (fillFraction > 0f) {
                        drawArc(
                            color = colors.accent,
                            startAngle = -90f,
                            sweepAngle = (fillFraction * 360f).coerceAtMost(360f),
                            useCenter = false,
                            topLeft = Offset(inset, inset),
                            size = Size(size.width - strokeWidth, size.height - strokeWidth),
                            style = Stroke(strokeWidth, cap = StrokeCap.Round)
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Main move card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MoveMainCard(
    colors: MoveDetailColors,
    activeKcal: Int,
    moveGoal: Int,
    progress: Float,
    hourlyKcal: List<Float>,
    peakKcal: Int,
    totalKcal: Int,
    modifier: Modifier = Modifier
) {
    val pct = (progress * 100).toInt()
    MoveDetailCard(colors = colors, modifier = modifier.fillMaxWidth()) {
        // Header row: "Move" + arrow circle button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Move",
                    color = colors.secondaryText,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.sp
                )
                Text(
                    text = "$activeKcal/$moveGoal KCAL",
                    color = colors.primaryText,
                    fontSize = 26.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.sp
                )
                Text(
                    text = "$pct% of your goal",
                    color = colors.secondaryText,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colors.innerSurface)
                    .border(1.dp, colors.border, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = colors.accent,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Large ring
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            LargeActivityRing(
                progress = progress,
                colors = colors,
                modifier = Modifier.size(200.dp)
            )
            // Center text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$activeKcal",
                    color = colors.accent,
                    fontSize = 48.sp,
                    lineHeight = 52.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.sp
                )
                Text(
                    text = "KCAL",
                    color = colors.tertiaryText,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
                Text(
                    text = "/$moveGoal KCAL",
                    color = colors.tertiaryText,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Sparkline with peak label
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            // Peak label
            Column(
                modifier = Modifier.width(44.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$peakKcal KCAL",
                    color = colors.tertiaryText,
                    fontSize = 10.sp,
                    lineHeight = 13.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.End
                )
            }
            Spacer(Modifier.width(6.dp))
            LargeSparkline(
                hourlyKcal = hourlyKcal,
                peakKcal = peakKcal.toFloat(),
                colors = colors,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            )
        }

        Spacer(Modifier.height(4.dp))

        // X-axis labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("12:00 AM", "6:00 AM", "12:00 PM", "6:00 PM").forEach { label ->
                Text(
                    text = label,
                    color = colors.tertiaryText,
                    fontSize = 9.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "TOTAL ${formatKcal(totalKcal)} KCAL",
            color = colors.secondaryText,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun LargeActivityRing(
    progress: Float,
    colors: MoveDetailColors,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.14f
        val inset = strokeWidth / 2f
        val ringRect = Rect(inset, inset, size.width - inset, size.height - inset)

        // Track
        drawArc(
            color = colors.track,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = ringRect.topLeft,
            size = ringRect.size,
            style = Stroke(strokeWidth, cap = StrokeCap.Round)
        )
        // Fill arc
        val sweep = (progress * 360f).coerceAtMost(359.9f)
        drawArc(
            brush = Brush.sweepGradient(
                listOf(
                    colors.accent.copy(alpha = 0.3f),
                    colors.accent,
                    colors.accentHighlight,
                    colors.accent.copy(alpha = 0.3f)
                ),
                center = center
            ),
            startAngle = -90f,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = ringRect.topLeft,
            size = ringRect.size,
            style = Stroke(strokeWidth, cap = StrokeCap.Round)
        )
        // Arrow indicator at arc tip
        drawArrowTip(sweep, ringRect, strokeWidth, colors)
    }
}

private fun DrawScope.drawArrowTip(
    sweepAngle: Float,
    ringRect: Rect,
    strokeWidth: Float,
    colors: MoveDetailColors
) {
    val endAngleDeg = -90f + sweepAngle
    val endAngleRad = Math.toRadians(endAngleDeg.toDouble())
    val ringRadius = (ringRect.width / 2f)
    val tipX = center.x + (ringRadius * kotlin.math.cos(endAngleRad)).toFloat()
    val tipY = center.y + (ringRadius * kotlin.math.sin(endAngleRad)).toFloat()
    val circleRadius = strokeWidth * 0.62f
    drawCircle(
        brush = Brush.radialGradient(
            listOf(colors.accentHighlight, colors.accent),
            center = Offset(tipX, tipY),
            radius = circleRadius
        ),
        radius = circleRadius,
        center = Offset(tipX, tipY)
    )
    // Arrow lines pointing outward along tangent
    val tangentAngleRad = endAngleRad + Math.PI / 2
    val arrowLen = circleRadius * 0.7f
    val ax = kotlin.math.cos(tangentAngleRad).toFloat()
    val ay = kotlin.math.sin(tangentAngleRad).toFloat()
    val arrowPath = Path().apply {
        moveTo(tipX - ax * arrowLen, tipY - ay * arrowLen)
        lineTo(tipX + ax * arrowLen * 0.5f, tipY + ay * arrowLen * 0.5f)
    }
    drawPath(
        path = arrowPath,
        color = Color.White,
        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}

@Composable
private fun LargeSparkline(
    hourlyKcal: List<Float>,
    peakKcal: Float,
    colors: MoveDetailColors,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val buckets = hourlyKcal.size
        val colWidth = size.width / buckets
        val baseY = size.height * 0.72f
        val maxBarHeight = size.height * 0.65f
        val dotRadius = 1.2.dp.toPx()

        // Peak reference line
        if (peakKcal > 0f) {
            val peakY = baseY - maxBarHeight
            repeat(20) { i ->
                val x = size.width * i / 19f
                drawCircle(
                    color = colors.tertiaryText.copy(alpha = 0.5f),
                    radius = 1.dp.toPx(),
                    center = Offset(x, peakY)
                )
            }
        }

        // Baseline dots
        repeat(buckets) { i ->
            val x = i * colWidth + colWidth / 2f
            drawCircle(
                color = colors.track,
                radius = dotRadius,
                center = Offset(x, baseY)
            )
        }

        // Bars
        hourlyKcal.forEachIndexed { i, kcal ->
            if (kcal > 0f && peakKcal > 0f) {
                val x = i * colWidth + colWidth / 2f
                val barH = (kcal / peakKcal) * maxBarHeight
                drawLine(
                    brush = Brush.verticalGradient(
                        listOf(colors.accentHighlight, colors.accent),
                        startY = baseY - barH,
                        endY = baseY
                    ),
                    start = Offset(x, baseY - barH),
                    end = Offset(x, baseY),
                    strokeWidth = (colWidth * 0.42f).coerceAtLeast(2.dp.toPx()),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Step stat cards
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun StepStatCard(
    title: String,
    value: String,
    unit: String?,
    colors: MoveDetailColors,
    modifier: Modifier = Modifier
) {
    // Tiny sparkline data — mock
    val mockBars = remember { listOf(3f, 0f, 0f, 2f, 5f, 0f, 4f, 8f, 6f, 0f, 3f, 2f) }

    MoveDetailCard(
        colors = colors,
        modifier = modifier,
        contentPadding = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = colors.primaryText,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = colors.tertiaryText,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = "Today",
            color = colors.secondaryText,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp
        )
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                color = colors.accent,
                fontSize = 26.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.sp
            )
            if (unit != null) {
                Spacer(Modifier.width(4.dp))
                Text(
                    text = unit,
                    color = colors.primaryText,
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        // Mini sparkline
        Canvas(modifier = Modifier.fillMaxWidth().height(36.dp)) {
            val buckets = mockBars.size
            val colWidth = size.width / buckets
            val baseY = size.height * 0.78f
            val maxH = size.height * 0.72f
            val peak = mockBars.max()
            mockBars.forEachIndexed { i, v ->
                val x = i * colWidth + colWidth / 2f
                if (v > 0f) {
                    val barH = (v / peak) * maxH
                    drawLine(
                        color = colors.accent,
                        start = Offset(x, baseY - barH),
                        end = Offset(x, baseY),
                        strokeWidth = (colWidth * 0.44f).coerceAtLeast(2.dp.toPx()),
                        cap = StrokeCap.Round
                    )
                } else {
                    drawCircle(
                        color = colors.track,
                        radius = 1.dp.toPx(),
                        center = Offset(x, baseY)
                    )
                }
            }
        }
        // X-axis labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("12 AM", "6 PM", "12 AM").forEach { label ->
                Text(
                    text = label,
                    color = colors.tertiaryText,
                    fontSize = 8.sp,
                    lineHeight = 11.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Shared card container
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MoveDetailCard(
    colors: MoveDetailColors,
    modifier: Modifier = Modifier,
    contentPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = colors.shadow,
                spotColor = colors.shadow
            )
            .clip(RoundedCornerShape(18.dp))
            .background(colors.card)
            .border(1.dp, colors.border, RoundedCornerShape(18.dp))
            .padding(contentPadding),
        content = content
    )
}

private fun formatKcal(v: Int): String =
    if (v >= 1000) "${v / 1000},${(v % 1000).toString().padStart(3, '0')}" else "$v"

// Need this import for Dp usage in MoveDetailCard
private typealias Dp = androidx.compose.ui.unit.Dp

// ─────────────────────────────────────────────────────────────────────────────
// Colors
// ─────────────────────────────────────────────────────────────────────────────

@Immutable
private data class MoveDetailColors(
    val background: Color,
    val card: Color,
    val innerSurface: Color,
    val border: Color,
    val shadow: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val tertiaryText: Color,
    val accent: Color,
    val accentHighlight: Color,
    val track: Color,
    val editButtonBg: Color
) {
    companion object {
        fun light() = MoveDetailColors(
            background = WarmMilkCanvas,
            card = PearlCardSurface,
            innerSurface = Color.White.copy(alpha = 0.7f),
            border = PeachHairlineBorder,
            shadow = SoftPetalCoral.copy(alpha = 0.14f),
            primaryText = PrimaryInk,
            secondaryText = SoftCharcoal,
            tertiaryText = MutedWarmGray,
            accent = BloomCoralFlame,
            accentHighlight = FreshCoralHighlight,
            track = Color(0xFFF1E7E5),
            editButtonBg = SoftPetalCoral.copy(alpha = 0.12f)
        )

        fun dark() = MoveDetailColors(
            background = MidnightPlumCanvas,
            card = SmokedCardSurface,
            innerSurface = Color.Black.copy(alpha = 0.1f),
            border = RosewoodBorder,
            shadow = BloomCoralFlame.copy(alpha = 0.10f),
            primaryText = Color.White,
            secondaryText = SoftAshText,
            tertiaryText = MutedRoseGray,
            accent = FreshCoralHighlight,
            accentHighlight = SoftPetalCoral,
            track = Color(0xFF1E1A1D),
            editButtonBg = BloomCoralFlame.copy(alpha = 0.10f)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Previews
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomMoveDetailLightPreview() {
    FitMessTheme(darkTheme = false) {
        BloomMoveDetailScreen()
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomMoveDetailDarkPreview() {
    FitMessTheme(darkTheme = true) {
        BloomMoveDetailScreen()
    }
}
