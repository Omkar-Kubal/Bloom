package com.appylab.bloom.feature.run

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.data.db.entities.DailySteps
import com.appylab.bloom.core.ui.AppBottomNav
import com.appylab.bloom.core.ui.CircleIconButton
import com.appylab.bloom.core.ui.GlassCard
import com.appylab.bloom.core.ui.MetricText
import com.appylab.bloom.core.ui.SectionTitle
import com.appylab.bloom.core.ui.screenBrush
import com.appylab.bloom.navigation.AppDestination

@Composable
fun RunScreen(
    viewModel: RunViewModel = hiltViewModel(),
    onDestinationSelected: (AppDestination) -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val todaySteps by viewModel.todaySteps.collectAsState()
    val context = LocalContext.current

    var hasActivityPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED
            else true
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasActivityPermission = isGranted
        if (isGranted) {
            ContextCompat.startForegroundService(context, Intent(context, StepCounterService::class.java))
        }
    }

    LaunchedEffect(Unit) {
        if (!hasActivityPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
    ) {
        val scale = (maxWidth / 393.dp).coerceAtMost(maxHeight / 852.dp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp * scale)
                .padding(top = 18.dp * scale, bottom = (61 + 24).dp * scale),
            verticalArrangement = Arrangement.spacedBy(12.dp * scale)
        ) {
            RunHeader(scale)
            RunTrackerCard(scale, todaySteps)
            RunStatsCard(scale, todaySteps)
            RoutesCard(scale)
            WeeklyRunCard(scale)
            GoalCard(scale)
        }
        AppBottomNav(
            active = AppDestination.Run,
            scale = scale,
            onDestinationSelected = onDestinationSelected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp * scale)
                .padding(bottom = 16.dp * scale)
        )
    }
}

@Composable
private fun RunHeader(scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Run", color = MaterialTheme.colorScheme.onBackground, fontSize = 24.sp * scale, fontWeight = FontWeight.ExtraBold)
            Text("Every step forward, makes you stronger.", color = MaterialTheme.colorScheme.tertiary, fontSize = 13.sp * scale)
        }
        CircleIconButton(scale) {
            Text("⚙", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp * scale)
        }
    }
}

@Composable
private fun RunTrackerCard(scale: Float, todaySteps: DailySteps?) {
    val primary    = MaterialTheme.colorScheme.primary
    val secondary  = MaterialTheme.colorScheme.secondary
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val outline    = MaterialTheme.colorScheme.outline
    val onSurface  = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val onPrimary  = MaterialTheme.colorScheme.onPrimary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val track      = MaterialTheme.colorScheme.surfaceVariant.copy(.5f)

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(15.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                com.appylab.bloom.core.ui.NavIcon(AppDestination.Run, primary, 20.dp * scale)
                Spacer(Modifier.width(9.dp * scale))
                Text("Outdoor Run⌄", color = onSurface, fontSize = 12.sp * scale, modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp * scale))
                        .border(0.5.dp, outline, RoundedCornerShape(16.dp * scale))
                        .padding(horizontal = 10.dp * scale, vertical = 4.dp * scale)
                ) {
                    Text("GPS  • Strong", color = onSurface, fontSize = 9.sp * scale)
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                RunArc(track, primary, Modifier.size(200.dp * scale))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.offset(y = (-22).dp * scale)) {
                    Text(
                        String.format(java.util.Locale.US, "%.2f", todaySteps?.distanceKm ?: 0f),
                        color = onSurface,
                        fontSize = 42.sp * scale,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text("Kilometers", color = onSurfaceVariant, fontSize = 11.sp * scale)
                }
                Box(
                    modifier = Modifier
                        .size(74.dp * scale)
                        .clip(CircleShape)
                        .background(Brush.radialGradient(listOf(primary, secondary)))
                        .border(0.5.dp, primary, CircleShape)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Text("GO", color = onPrimary, fontSize = 20.sp * scale, fontWeight = FontWeight.ExtraBold)
                }
                Column(modifier = Modifier.align(Alignment.CenterStart).offset(y = 25.dp * scale)) {
                    Text("Pace", color = onSurfaceVariant, fontSize = 10.sp * scale)
                    Text("--:--", color = onSurface, fontSize = 14.sp * scale, fontWeight = FontWeight.Bold)
                    Text("min/km", color = onSurfaceVariant, fontSize = 9.sp * scale)
                }
                Column(modifier = Modifier.align(Alignment.CenterEnd).offset(y = 25.dp * scale), horizontalAlignment = Alignment.End) {
                    Text("Duration", color = onSurfaceVariant, fontSize = 10.sp * scale)
                    Text("00:00:00", color = onSurface, fontSize = 14.sp * scale, fontWeight = FontWeight.Bold)
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 2.dp * scale),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("♫  No music selected", color = onSurfaceVariant, fontSize = 9.sp * scale, modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp * scale))
                            .background(secondaryContainer)
                            .border(0.5.dp, outline.copy(.5f), RoundedCornerShape(14.dp * scale))
                            .padding(horizontal = 11.dp * scale, vertical = 5.dp * scale)
                    ) {
                        Text("♫  Choose Music", color = onSurface, fontSize = 9.sp * scale)
                    }
                }
            }
        }
    }
}

@Composable
private fun RunStatsCard(scale: Float, todaySteps: DailySteps?) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp * scale),
        scale = scale
    ) {
        Row(Modifier.fillMaxSize().padding(horizontal = 14.dp * scale), verticalAlignment = Alignment.CenterVertically) {
            MetricText(String.format(java.util.Locale.US, "%.2f", todaySteps?.distanceKm ?: 0f), "Distance\nkm", scale, Modifier.weight(1f))
            StatDivider(scale)
            MetricText("${todaySteps?.stepCount ?: 0}", "Steps\ntoday", scale, Modifier.weight(1f))
            StatDivider(scale)
            MetricText("--:--", "Avg Pace\nmin/km", scale, Modifier.weight(1f))
            StatDivider(scale)
            MetricText("${todaySteps?.calorieBurn ?: 0}", "Calories\nkcal", scale, Modifier.weight(1f))
        }
    }
}

@Composable
private fun RoutesCard(scale: Float) {
    val primary    = MaterialTheme.colorScheme.primary
    val secondary  = MaterialTheme.colorScheme.secondary
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val onSurface  = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val onPrimary  = MaterialTheme.colorScheme.onPrimary
    val surface    = MaterialTheme.colorScheme.surface
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(14.dp * scale)) {
            SectionTitle(AppDestination.Run, "Popular Routes", scale, action = "See All  >")
            Spacer(Modifier.height(10.dp * scale))
            Row(Modifier.fillMaxWidth().weight(1f)) {
                Box(
                    modifier = Modifier
                        .weight(1.9f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp * scale))
                        .background(secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    RouteLine(primary, surface, Modifier.fillMaxSize().padding(16.dp * scale))
                }
                Spacer(Modifier.width(14.dp * scale))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Sunset Loop", color = onSurface, fontSize = 11.sp * scale, fontWeight = FontWeight.Bold)
                    Text("5.23 km", color = primary, fontSize = 10.sp * scale)
                    Spacer(Modifier.height(10.dp * scale))
                    Text("Avg Pace\n6:25 min/km", color = onSurfaceVariant, fontSize = 8.5.sp * scale, lineHeight = 12.sp * scale)
                    Spacer(Modifier.height(5.dp * scale))
                    Text("Elevation\n128 m", color = onSurfaceVariant, fontSize = 8.5.sp * scale, lineHeight = 12.sp * scale)
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp * scale)
                            .clip(RoundedCornerShape(10.dp * scale))
                            .background(Brush.verticalGradient(listOf(primary, secondary))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Use Route", color = onPrimary, fontSize = 10.sp * scale, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyRunCard(scale: Float) {
    val primary   = MaterialTheme.colorScheme.primary
    val tertiary  = MaterialTheme.colorScheme.tertiary
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(14.dp * scale)) {
            SectionTitle(null, "This Week", scale, action = "View More  >")
            Spacer(Modifier.height(12.dp * scale))
            Row(verticalAlignment = Alignment.Bottom) {
                MetricText("3", "Runs", scale, Modifier.weight(.8f))
                StatDivider(scale)
                MetricText("15.62", "km", scale, Modifier.weight(1f))
                StatDivider(scale)
                MetricText("1:42:38", "Time", scale, Modifier.weight(1.2f))
                StatDivider(scale)
                MetricText("886", "kcal", scale, Modifier.weight(1f))
                Row(modifier = Modifier.weight(1.4f), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.Bottom) {
                    listOf(20, 34, 24, 32, 24, 52).forEachIndexed { index, h ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .width(8.dp * scale)
                                    .height(h.dp * scale)
                                    .clip(RoundedCornerShape(99.dp))
                                    .background(Brush.verticalGradient(listOf(primary, tertiary)))
                            )
                            Text(listOf("M", "T", "W", "T", "F", "S")[index], color = onSurfaceVariant, fontSize = 7.sp * scale)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GoalCard(scale: Float) {
    val primary    = MaterialTheme.colorScheme.primary
    val secondary  = MaterialTheme.colorScheme.secondary
    val tertiary   = MaterialTheme.colorScheme.tertiary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface  = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val onPrimary  = MaterialTheme.colorScheme.onPrimary
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp * scale),
        scale = scale
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp * scale),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp * scale)
                    .clip(RoundedCornerShape(14.dp * scale))
                    .background(Brush.verticalGradient(listOf(primary, secondary))),
                contentAlignment = Alignment.Center
            ) {
                Text("★", color = onPrimary, fontSize = 18.sp * scale)
            }
            Spacer(Modifier.width(16.dp * scale))
            Column(modifier = Modifier.weight(1f)) {
                Text("Keep it up!", color = onSurface, fontSize = 13.sp * scale, fontWeight = FontWeight.Bold)
                Text("You're 2 runs away from\nyour weekly goal.", color = onSurfaceVariant, fontSize = 10.sp * scale, lineHeight = 14.sp * scale)
            }
            ProgressBadge(primary, tertiary, surfaceVariant, onPrimary, scale)
            Spacer(Modifier.width(12.dp * scale))
            Text(">", color = onSurfaceVariant, fontSize = 17.sp * scale)
        }
    }
}

@Composable
private fun ProgressBadge(primary: Color, tertiary: Color, track: Color, onPrimary: Color, scale: Float) {
    Box(modifier = Modifier.size(55.dp * scale), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val stroke = 5.dp.toPx()
            val rect = Rect(stroke / 2, stroke / 2, size.width - stroke / 2, size.height - stroke / 2)
            drawArc(track, -90f, 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
            drawArc(Brush.sweepGradient(listOf(primary, tertiary, primary)), -90f, 216f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        }
        Text("3/5\nRuns", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp * scale, textAlign = TextAlign.Center, lineHeight = 13.sp * scale, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun RunArc(track: Color, fill: Color, modifier: Modifier) {
    Canvas(modifier) {
        val stroke = 2.dp.toPx()
        val rect = Rect(10.dp.toPx(), 10.dp.toPx(), size.width - 10.dp.toPx(), size.height - 10.dp.toPx())
        drawArc(track, 190f, 160f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(fill.copy(.9f), 215f, 95f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
    }
}

@Composable
private fun RouteLine(lineColor: Color, dotColor: Color, modifier: Modifier) {
    Canvas(modifier) {
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(size.width * .06f, size.height * .76f)
            cubicTo(size.width * .22f, size.height * .2f, size.width * .38f, size.height * .95f, size.width * .55f, size.height * .55f)
            cubicTo(size.width * .65f, size.height * .32f, size.width * .75f, size.height * .62f, size.width * .86f, size.height * .25f)
            cubicTo(size.width * .95f, size.height * .08f, size.width * .98f, size.height * .58f, size.width * .92f, size.height * .74f)
        }
        // Keep the green start dot as a brand accent for route start/end
        drawPath(path, Brush.horizontalGradient(listOf(Color(0xFF23D58B), lineColor)), style = Stroke(6.dp.toPx(), cap = StrokeCap.Round))
        drawCircle(Color(0xFF23D58B), 9.dp.toPx(), Offset(size.width * .06f, size.height * .76f), style = Stroke(3.dp.toPx()))
        drawCircle(dotColor, 6.dp.toPx(), Offset(size.width * .92f, size.height * .74f), style = Stroke(3.dp.toPx()))
    }
}

@Composable
private fun StatDivider(scale: Float) {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(42.dp * scale)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}
