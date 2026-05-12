package com.appylab.bloom.feature.workout

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.data.db.entities.WorkoutSession
import com.appylab.bloom.core.ui.AppBottomNav
import com.appylab.bloom.core.ui.CircleIconButton
import com.appylab.bloom.core.ui.GlassCard
import com.appylab.bloom.core.ui.MetricText
import com.appylab.bloom.core.ui.NavIcon
import com.appylab.bloom.core.ui.SectionTitle
import com.appylab.bloom.core.ui.screenBrush
import com.appylab.bloom.navigation.AppDestination
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    onDestinationSelected: (AppDestination) -> Unit,
    onStartSession: () -> Unit,
    onViewHistory: () -> Unit
) {
    val sessionHistory by viewModel.sessionHistory.collectAsState()

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
            WorkoutHeader(scale)
            WeeklyStatsCard(scale, sessionHistory)
            TodayWorkoutCard(scale, onStartSession)
            SplitCard(scale)
            RecentWorkoutsCard(scale, sessionHistory, onViewHistory)
            MuscleFocusCard(scale)
        }
        AppBottomNav(
            active = AppDestination.Workout,
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
private fun WorkoutHeader(scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "Workout",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 23.sp * scale,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                "Track. Train. Transform.",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 13.sp * scale
            )
        }
        CircleIconButton(scale) {
            NavIcon(AppDestination.Dashboard, MaterialTheme.colorScheme.primary, 21.dp * scale)
        }
    }
}

@Composable
private fun WeeklyStatsCard(scale: Float, sessionHistory: List<WorkoutSession>) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(94.dp * scale),
        scale = scale
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp * scale),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val workoutsThisWeek = sessionHistory.size
            StatColumn(AppDestination.Workout, workoutsThisWeek.toString(), "This Week\nWorkouts", scale, Modifier.weight(1f))
            StatDivider(scale)
            StatColumn(AppDestination.Run, "12,540", "Total Volume\nkg", scale, Modifier.weight(1f))
            StatDivider(scale)
            StatColumn(AppDestination.Dashboard, "6h 23m", "Total Time\nmin", scale, Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatColumn(icon: AppDestination, value: String, label: String, scale: Float, modifier: Modifier = Modifier) {
    val primary = MaterialTheme.colorScheme.primary
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        NavIcon(icon, primary, 21.dp * scale)
        Text(label.substringBefore('\n'), color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp * scale, textAlign = TextAlign.Center)
        Text(value, color = MaterialTheme.colorScheme.onSurface, fontSize = 17.sp * scale, fontWeight = FontWeight.ExtraBold)
        Text(label.substringAfter('\n'), color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp * scale, textAlign = TextAlign.Center)
    }
}

@Composable
private fun StatDivider(scale: Float) {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(46.dp * scale)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}

@Composable
private fun TodayWorkoutCard(scale: Float, onStartSession: () -> Unit) {
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val outline = MaterialTheme.colorScheme.outline
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(14.dp * scale)) {
            SectionTitle(AppDestination.Dashboard, "Today's Workout", scale, action = "See All  >")
            Spacer(Modifier.height(10.dp * scale))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp * scale)
                    .clip(RoundedCornerShape(15.dp * scale))
                    .background(secondaryContainer)
                    .border(0.5.dp, outline.copy(.3f), RoundedCornerShape(15.dp * scale))
                    .padding(horizontal = 12.dp * scale),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Push Day", color = MaterialTheme.colorScheme.onSurface, fontSize = 15.sp * scale, fontWeight = FontWeight.ExtraBold)
                    Text("Chest • Shoulders • Triceps", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp * scale)
                    Spacer(Modifier.height(8.dp * scale))
                    Text("01:15:00     |     6 Exercises", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp * scale)
                }
                Box(
                    modifier = Modifier
                        .width(82.dp * scale)
                        .height(43.dp * scale)
                        .clip(RoundedCornerShape(13.dp * scale))
                        .background(Brush.verticalGradient(listOf(primary, secondary)))
                        .clickable { onStartSession() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Start\nWorkout  >", color = MaterialTheme.colorScheme.onPrimary, fontSize = 10.sp * scale, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun SplitCard(scale: Float) {
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outline = MaterialTheme.colorScheme.outline
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val onSurface = MaterialTheme.colorScheme.onSurface
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(14.dp * scale)) {
            SectionTitle(AppDestination.Workout, "Workout Split", scale, action = "Edit")
            Spacer(Modifier.height(12.dp * scale))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp * scale)) {
                listOf("PUSH\nTue", "PULL\nWed", "LEGS\nThu", "UPPER\nFri", "LOWER\nSat", "FULL BODY\nSun").forEachIndexed { index, label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(33.dp * scale)
                            .clip(RoundedCornerShape(13.dp * scale))
                            .background(
                                if (index == 0) Brush.verticalGradient(listOf(primary, secondary))
                                else Brush.verticalGradient(listOf(secondaryContainer, surfaceVariant))
                            )
                            .border(0.5.dp, if (index == 0) primary.copy(.4f) else outline.copy(.2f), RoundedCornerShape(13.dp * scale)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            label,
                            color = if (index == 0) onPrimary else onSurface,
                            fontSize = 8.sp * scale,
                            textAlign = TextAlign.Center,
                            lineHeight = 11.sp * scale
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentWorkoutsCard(scale: Float, sessionHistory: List<WorkoutSession>, onViewHistory: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(184.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(14.dp * scale), verticalArrangement = Arrangement.spacedBy(7.dp * scale)) {
            Row(modifier = Modifier.fillMaxWidth().clickable { onViewHistory() }) {
                SectionTitle(AppDestination.Run, "Recent Workouts", scale, action = "View History")
            }
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            sessionHistory.take(3).forEach { session ->
                val dateStr = dateFormat.format(Date(session.startTime))
                val duration = if (session.endTime != null) {
                    val diff = (session.endTime - session.startTime) / 1000
                    String.format("%02d:%02d:%02d", diff / 3600, (diff % 3600) / 60, diff % 60)
                } else "In Progress"
                WorkoutHistoryRow("Session ${session.id}", "${session.estimatedCalories ?: 0} kcal", "$dateStr\n$duration", scale)
            }
            if (sessionHistory.isEmpty()) {
                Text("No recent workouts.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp * scale)
            }
        }
    }
}

@Composable
private fun WorkoutHistoryRow(title: String, subtitle: String, date: String, scale: Float) {
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outline = MaterialTheme.colorScheme.outline
    val primary = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(43.dp * scale)
            .clip(RoundedCornerShape(13.dp * scale))
            .background(surfaceVariant)
            .padding(horizontal = 10.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp * scale)
                .clip(CircleShape)
                .border(0.5.dp, outline.copy(.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            NavIcon(AppDestination.Workout, primary, 18.dp * scale)
        }
        Spacer(Modifier.width(12.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp * scale)
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 8.5.sp * scale)
        }
        Text(date, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 9.sp * scale, textAlign = TextAlign.End, lineHeight = 12.sp * scale)
        Spacer(Modifier.width(8.dp * scale))
        Text(">", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 16.sp * scale)
    }
}

@Composable
private fun MuscleFocusCard(scale: Float) {
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val primary = MaterialTheme.colorScheme.primary
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(14.dp * scale)) {
            SectionTitle(AppDestination.Run, "Muscle Group Focus", scale, action = "This Week⌄")
            Spacer(Modifier.height(13.dp * scale))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp * scale)) {
                listOf("Chest\n85%", "Back\n75%", "Shoulders\n70%", "Legs\n90%", "Arms\n60%", "Core\n65%").forEach {
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .height(54.dp * scale)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp * scale))
                                .background(secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("●", color = primary.copy(.8f), fontSize = 26.sp * scale)
                        }
                        Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 8.sp * scale, textAlign = TextAlign.Center, lineHeight = 11.sp * scale)
                    }
                }
            }
        }
    }
}
