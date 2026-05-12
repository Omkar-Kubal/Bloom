package com.appylab.bloom.feature.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.core.ui.AppBottomNav
import com.appylab.bloom.core.ui.GlassCard
import com.appylab.bloom.core.ui.screenBrush
import com.appylab.bloom.navigation.AppDestination
import com.appylab.bloom.ui.theme.BloomTheme

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onDestinationSelected: (AppDestination) -> Unit,
    onNavigateToProfile: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
    ) {
        val scale = (maxWidth / 393.dp).coerceAtMost(maxHeight / 852.dp)
        val hPad = 13.dp * scale
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = hPad)
                .padding(top = 18.dp * scale, bottom = 15.dp * scale),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header(uiState, scale, onNavigateToProfile)
            DateStrip(uiState.dates, scale)
            PlanCard(uiState.workout, scale)
            MetricGrid(uiState, scale)
            ReportCard(uiState.weightTrend, uiState.netCalorieNote, scale)
            AppBottomNav(AppDestination.Dashboard, scale, onDestinationSelected)
        }
    }
}

@Composable
private fun Header(uiState: DashboardUiState, scale: Float, onNavigateToProfile: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarButton(scale, onNavigateToProfile)
        Spacer(Modifier.width(16.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                uiState.greetingLine,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 15.sp * scale
            )
            Text(
                uiState.motivationalLine,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp * scale,
                fontWeight = FontWeight.SemiBold
            )
        }
        BellButton(scale)
    }
}

@Composable
private fun AvatarButton(scale: Float, onNavigateToProfile: () -> Unit) {
    val outline = MaterialTheme.colorScheme.outline
    val surface = MaterialTheme.colorScheme.surface
    val secondary = MaterialTheme.colorScheme.secondary
    val tertiary = MaterialTheme.colorScheme.tertiary
    Box(
        modifier = Modifier
            .size(46.dp * scale)
            .shadow(2.dp, CircleShape, ambientColor = outline.copy(.25f), spotColor = outline.copy(.15f))
            .clip(CircleShape)
            .background(surface)
            .border(0.5.dp, outline.copy(.35f), CircleShape)
            .clickable { onNavigateToProfile() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(28.dp * scale)) {
            drawCircle(secondary, radius = size.minDimension * .22f, center = Offset(size.width / 2, size.height * .34f))
            drawArc(
                color = tertiary,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(size.width * .18f, size.height * .52f),
                size = Size(size.width * .64f, size.height * .3f)
            )
        }
    }
}

@Composable
private fun BellButton(scale: Float) {
    val outline = MaterialTheme.colorScheme.outline
    val surface = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface
    val secondary = MaterialTheme.colorScheme.secondary
    Box(
        modifier = Modifier
            .size(40.dp * scale)
            .shadow(2.dp, CircleShape, ambientColor = outline.copy(.25f), spotColor = outline.copy(.15f))
            .clip(CircleShape)
            .background(surface)
            .border(0.5.dp, outline.copy(.35f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(22.dp * scale)) {
            val stroke = Stroke(2.dp.toPx(), cap = StrokeCap.Round)
            drawArc(
                color = onSurface,
                startAngle = 194f,
                sweepAngle = 152f,
                useCenter = false,
                topLeft = Offset(size.width * .2f, size.height * .08f),
                size = Size(size.width * .6f, size.height * .8f),
                style = stroke
            )
            drawLine(onSurface, Offset(size.width * .22f, size.height * .74f), Offset(size.width * .78f, size.height * .74f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
            drawLine(onSurface, Offset(size.width * .38f, size.height * .88f), Offset(size.width * .62f, size.height * .88f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 2.dp * scale, y = 1.dp * scale)
                .size(8.dp * scale)
                .clip(CircleShape)
                .background(secondary)
        )
    }
}

@Composable
private fun DateStrip(dates: List<DateChipUi>, scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp * scale),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dates.forEach { date ->
            DateChip(date.number, date.weekday, date.isSelected, scale)
        }
    }
}

@Composable
private fun DateChip(num: String, day: String, selected: Boolean, scale: Float) {
    val secondary = MaterialTheme.colorScheme.secondary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val surface = MaterialTheme.colorScheme.surface
    val background = MaterialTheme.colorScheme.background
    val outline = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val bg = if (selected)
        Brush.verticalGradient(listOf(tertiary, secondary))
    else
        Brush.verticalGradient(listOf(surface, background))

    Column(
        modifier = Modifier
            .width(38.dp * scale)
            .height(51.dp * scale)
            .clip(RoundedCornerShape(23.dp * scale))
            .background(bg)
            .border(
                0.5.dp,
                if (selected) tertiary.copy(.4f) else outline.copy(.4f),
                RoundedCornerShape(23.dp * scale)
            )
            .padding(top = 8.dp * scale),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(25.dp * scale)
                .clip(CircleShape)
                .background(if (selected) Color.White.copy(.25f) else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                num,
                color = if (selected) Color.White else onSurface,
                fontSize = 13.sp * scale,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            day,
            color = if (selected) Color.White.copy(.85f) else onSurfaceVariant,
            fontSize = 7.5.sp * scale,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun PlanCard(workout: TodayWorkoutSummary?, scale: Float) {
    val title = workout?.title ?: "Log Workout"
    val duration = workout?.durationMinutes ?: 0
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp * scale),
        scale = scale
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp * scale, vertical = 16.dp * scale),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CalendarIcon(18.dp * scale)
                Spacer(Modifier.width(8.dp * scale))
                Text(
                    workout?.completedAtLabel ?: "Today's Plan",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp * scale
                )
            }
            Text(
                title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp * scale,
                lineHeight = 29.sp * scale,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                ClockIcon(14.dp * scale)
                Spacer(Modifier.width(8.dp * scale))
                Text(
                    if (duration > 0) "%02d:%02d min".format(duration / 60, duration % 60) else "Tap to start",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp * scale,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun MetricGrid(uiState: DashboardUiState, scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(209.dp * scale),
        horizontalArrangement = Arrangement.spacedBy(10.dp * scale)
    ) {
        CalorieCard(uiState.calories, uiState.macros, Modifier.weight(1f), scale)
        StepCard(uiState.steps, Modifier.weight(1f), scale)
    }
}

@Composable
private fun CalorieCard(calories: CalorieSummary, macros: MacroSummary, modifier: Modifier, scale: Float) {
    GlassCard(modifier = modifier.fillMaxHeight(), scale = scale) {
        Column(Modifier.fillMaxSize().padding(19.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FlameIcon(21.dp * scale)
                Spacer(Modifier.width(9.dp * scale))
                Text("Calories", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp * scale)
            }
            Box(Modifier.fillMaxWidth().height(122.dp * scale), contentAlignment = Alignment.Center) {
                Ring(
                    progress = calories.progress,
                    color = MaterialTheme.colorScheme.primary,
                    scale = scale,
                    modifier = Modifier.size(122.dp * scale)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        formatInt(calories.consumed),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 22.sp * scale,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text("kcal eaten", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp * scale)
                    Spacer(Modifier.height(6.dp * scale))
                    Text(
                        "${formatInt(calories.remaining)} left",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp * scale,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            MacroMiniBars(macros, scale)
        }
    }
}

@Composable
private fun MacroMiniBars(macros: MacroSummary, scale: Float) {
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val error = MaterialTheme.colorScheme.error
    val tertiary = MaterialTheme.colorScheme.tertiary
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp * scale)
    ) {
        macros.items.forEach { macro ->
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    macro.label.take(1),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 7.sp * scale,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp * scale)
                        .clip(RoundedCornerShape(99.dp))
                        .background(surfaceVariant)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(macro.progress.coerceIn(0f, 1f))
                            .fillMaxHeight()
                            .background(if (macro.isOverGoal) error else tertiary)
                    )
                }
            }
        }
    }
}

@Composable
private fun StepCard(steps: StepSummary, modifier: Modifier, scale: Float) {
    GlassCard(modifier = modifier.fillMaxHeight(), scale = scale) {
        Column(Modifier.fillMaxSize().padding(19.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ShoeIcon(22.dp * scale)
                Spacer(Modifier.width(9.dp * scale))
                Text("Steps", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp * scale)
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ArcRing(progress = steps.progress, scale = scale, modifier = Modifier.size(134.dp * scale))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.offset(y = 8.dp * scale)) {
                    Text(
                        formatInt(steps.steps),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 21.sp * scale,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text("steps", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp * scale)
                    Spacer(Modifier.height(9.dp * scale))
                    Text(
                        "${"%.2f".format(steps.distanceKm)} km · ${steps.caloriesBurnt} kcal",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp * scale,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun ReportCard(weightTrend: WeightTrendSummary, netCalorieNote: String, scale: Float) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(181.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = 19.dp * scale, vertical = 14.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TrendIcon(21.dp * scale)
                Spacer(Modifier.width(10.dp * scale))
                Text("Weight Trend", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp * scale)
            }
            val primary = MaterialTheme.colorScheme.primary
            val tertiary = MaterialTheme.colorScheme.tertiary
            val outline = MaterialTheme.colorScheme.outline
            val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
            val surface = MaterialTheme.colorScheme.surface
            Chart(
                values = weightTrend.values,
                lineColor = primary,
                trackColor = surfaceVariant,
                dotFillColor = surface,
                targetDotColor = tertiary,
                gridColor = outline.copy(.4f),
                modifier = Modifier.fillMaxWidth().height(82.dp * scale).padding(top = 6.dp * scale),
                scale = scale
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                WeightStat("Start", "${weightTrend.startWeightKg} kg", MaterialTheme.colorScheme.onSurfaceVariant, scale)
                DividerStat(scale)
                WeightStat("Current", "${weightTrend.currentWeightKg} kg", MaterialTheme.colorScheme.primary, scale)
                DividerStat(scale)
                WeightStat("Target", "${weightTrend.targetWeightKg} kg", MaterialTheme.colorScheme.tertiary, scale)
            }
            Text(
                netCalorieNote,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 8.sp * scale,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Chart(
    values: List<Float>,
    lineColor: Color,
    trackColor: Color,
    dotFillColor: Color,
    targetDotColor: Color,
    gridColor: Color,
    modifier: Modifier,
    scale: Float
) {
    // Convert to android.graphics.Color ints for native canvas text
    val labelArgb  = android.graphics.Color.rgb(99, 142, 203)   // SteelBlue
    val targetArgb = android.graphics.Color.rgb(57, 88, 134)    // DeepNavy

    Canvas(modifier = modifier) {
        val left   = 49.dp.toPx()
        val right  = size.width - 50.dp.toPx()
        val top    = 6.dp.toPx()
        val bottom = size.height - 12.dp.toPx()
        fun y(v: Float) = bottom - (v / 100f) * (bottom - top)

        listOf(0f, .25f, .5f, .75f, 1f).forEach {
            val yy = bottom - it * (bottom - top)
            drawLine(gridColor, Offset(left, yy), Offset(right, yy), strokeWidth = .7.dp.toPx())
        }
        drawLine(
            trackColor.copy(.8f),
            Offset(left, y(75f)),
            Offset(right, y(75f)),
            strokeWidth = .8.dp.toPx(),
            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(9.dp.toPx(), 7.dp.toPx()))
        )
        val step = (right - left) / (values.size - 1)
        val points = values.mapIndexed { i, v -> Offset(left + i * step, y(v)) }
        for (i in 0 until points.lastIndex) {
            drawLine(lineColor, points[i], points[i + 1], strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
        }
        points.forEach {
            drawCircle(lineColor, 4.5.dp.toPx(), it)
            drawCircle(dotFillColor, 2.1.dp.toPx(), it)
        }
        drawCircle(gridColor, 4.8.dp.toPx(), points.last())
        drawCircle(targetDotColor, 3.2.dp.toPx(), points.last())

        val labels = listOf("100", "75", "50", "25", "0")
        labels.forEachIndexed { i, label ->
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = labelArgb
                    textSize = 9.sp.toPx()
                    isAntiAlias = true
                }
                drawText(label, 0f, top + i * ((bottom - top) / 4f) + 4.dp.toPx(), paint)
            }
        }
        drawContext.canvas.nativeCanvas.apply {
            val paint = android.graphics.Paint().apply {
                color = targetArgb
                textSize = 10.sp.toPx()
                isAntiAlias = true
            }
            drawText("Target", right + 7.dp.toPx(), y(75f) - 8.dp.toPx(), paint)
            drawText("76 kg", right + 7.dp.toPx(), y(75f) + 8.dp.toPx(), paint)
        }
    }
}

@Composable
private fun WeightStat(label: String, value: String, color: Color, scale: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp * scale)
        Text(value, color = color, fontSize = 13.sp * scale, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun DividerStat(scale: Float) {
    Box(
        modifier = Modifier
            .height(22.dp * scale)
            .width(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}

@Composable
private fun Ring(progress: Float, color: Color, scale: Float, modifier: Modifier) {
    val track = MaterialTheme.colorScheme.surfaceVariant
    Canvas(modifier = modifier) {
        val stroke = 9.dp.toPx()
        val rect = Rect(stroke / 2, stroke / 2, size.width - stroke / 2, size.height - stroke / 2)
        drawArc(track, -90f, 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(color, -88f, progress.coerceIn(0f, 1f) * 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
    }
}

@Composable
private fun ArcRing(progress: Float, scale: Float, modifier: Modifier) {
    val track = MaterialTheme.colorScheme.surfaceVariant
    val fill  = MaterialTheme.colorScheme.tertiary
    Canvas(modifier = modifier) {
        val stroke = 9.dp.toPx()
        val rect = Rect(stroke / 2, stroke / 2, size.width - stroke / 2, size.height - stroke / 2)
        drawArc(track, 135f, 270f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(fill, 135f, progress.coerceIn(0f, 1f) * 270f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
    }
}

// ── Canvas icons — color read from MaterialTheme before DrawScope ─────────────

@Composable
private fun CalendarIcon(size: Dp) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(Modifier.size(size)) {
        val stroke = Stroke(1.8.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(color, Offset(size.toPx() * .12f, size.toPx() * .2f), Size(size.toPx() * .76f, size.toPx() * .68f), CornerRadius(2.dp.toPx()), style = stroke)
        drawLine(color, Offset(size.toPx() * .12f, size.toPx() * .4f), Offset(size.toPx() * .88f, size.toPx() * .4f), strokeWidth = 1.8.dp.toPx())
        drawLine(color, Offset(size.toPx() * .3f, 0f), Offset(size.toPx() * .3f, size.toPx() * .28f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
        drawLine(color, Offset(size.toPx() * .7f, 0f), Offset(size.toPx() * .7f, size.toPx() * .28f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    }
}

@Composable
private fun ClockIcon(size: Dp) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(Modifier.size(size)) {
        drawCircle(color, size.toPx() * .42f, Offset(size.toPx() / 2, size.toPx() / 2), style = Stroke(2.dp.toPx()))
        drawLine(color, Offset(size.toPx() / 2, size.toPx() * .24f), Offset(size.toPx() / 2, size.toPx() * .52f), strokeWidth = 1.6.dp.toPx(), cap = StrokeCap.Round)
        drawLine(color, Offset(size.toPx() / 2, size.toPx() / 2), Offset(size.toPx() * .68f, size.toPx() * .62f), strokeWidth = 1.6.dp.toPx(), cap = StrokeCap.Round)
    }
}

@Composable
private fun FlameIcon(size: Dp) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(Modifier.size(size)) {
        val p = Path().apply {
            moveTo(size.toPx() * .52f, size.toPx() * .05f)
            cubicTo(size.toPx() * .15f, size.toPx() * .34f, size.toPx() * .75f, size.toPx() * .45f, size.toPx() * .36f, size.toPx() * .82f)
            cubicTo(size.toPx() * .17f, size.toPx() * .66f, size.toPx() * .05f, size.toPx() * .45f, size.toPx() * .22f, size.toPx() * .22f)
            cubicTo(size.toPx() * .02f, size.toPx() * .38f, size.toPx() * .04f, size.toPx() * .9f, size.toPx() * .5f, size.toPx() * .92f)
            cubicTo(size.toPx() * .95f, size.toPx() * .89f, size.toPx() * .98f, size.toPx() * .43f, size.toPx() * .68f, size.toPx() * .2f)
            cubicTo(size.toPx() * .66f, size.toPx() * .32f, size.toPx() * .57f, size.toPx() * .32f, size.toPx() * .52f, size.toPx() * .05f)
        }
        drawPath(p, color, style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
private fun ShoeIcon(size: Dp) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(Modifier.size(size)) {
        rotate(-18f) {
            drawRoundRect(color, Offset(size.toPx() * .12f, size.toPx() * .43f), Size(size.toPx() * .72f, size.toPx() * .27f), CornerRadius(4.dp.toPx()), style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
            drawLine(color, Offset(size.toPx() * .36f, size.toPx() * .42f), Offset(size.toPx() * .47f, size.toPx() * .17f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
            drawLine(color, Offset(size.toPx() * .53f, size.toPx() * .43f), Offset(size.toPx() * .64f, size.toPx() * .18f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
        }
    }
}

@Composable
private fun TrendIcon(size: Dp) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(Modifier.size(size)) {
        val points = listOf(.1f to .75f, .28f to .47f, .43f to .62f, .6f to .22f, .8f to .35f, .93f to .1f)
        for (i in 0 until points.lastIndex) {
            drawLine(color, Offset(size.toPx() * points[i].first, size.toPx() * points[i].second), Offset(size.toPx() * points[i + 1].first, size.toPx() * points[i + 1].second), strokeWidth = 1.7.dp.toPx(), cap = StrokeCap.Round)
        }
        points.forEach { drawCircle(color, 2.2.dp.toPx(), Offset(size.toPx() * it.first, size.toPx() * it.second), style = Stroke(1.5.dp.toPx())) }
    }
}

private fun formatInt(value: Int): String = "%,d".format(value)

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun DashboardPreview() {
    BloomTheme {
        DashboardScreen(DashboardUiState.Preview, onDestinationSelected = {})
    }
}
