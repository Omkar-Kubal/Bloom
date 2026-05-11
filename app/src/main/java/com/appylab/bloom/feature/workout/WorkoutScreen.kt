package com.appylab.bloom.feature.workout

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.core.ui.AppBottomNav
import com.appylab.bloom.core.ui.AbyssNavy
import com.appylab.bloom.core.ui.BloomPeach
import com.appylab.bloom.core.ui.CircleIconButton
import com.appylab.bloom.core.ui.DimText
import com.appylab.bloom.core.ui.GlassCard
import com.appylab.bloom.core.ui.HotRed
import com.appylab.bloom.core.ui.MascotArt
import com.appylab.bloom.core.ui.MetricText
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.MutedSteel
import com.appylab.bloom.core.ui.NavIcon
import com.appylab.bloom.core.ui.SectionTitle
import com.appylab.bloom.core.ui.StatusBar
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.WineShadow
import com.appylab.bloom.core.ui.screenBrush
import com.appylab.bloom.core.data.db.entities.WorkoutSession
import com.appylab.bloom.navigation.AppDestination

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.hilt.navigation.compose.hiltViewModel
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
            StatusBar(scale)
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp * scale)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(top = 23.dp * scale)
        ) {
            Text("Workout", color = TextWhite, fontSize = 23.sp * scale, fontWeight = FontWeight.ExtraBold)
            Text("Track. Train. Transform.", color = BloomPeach, fontSize = 14.sp * scale)
        }
        MascotArt(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-62).dp * scale, y = (-9).dp * scale)
                .size(132.dp * scale)
        )
        CircleIconButton(scale, modifier = Modifier.align(Alignment.CenterEnd)) {
            NavIcon(AppDestination.Dashboard, HotRed, 21.dp * scale)
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
            val workoutsThisWeek = sessionHistory.size // Naive counting for MVP
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
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        NavIcon(icon, HotRed, 21.dp * scale)
        Text(label.substringBefore('\n'), color = MistText, fontSize = 10.sp * scale, textAlign = TextAlign.Center)
        Text(value, color = TextWhite, fontSize = 17.sp * scale, fontWeight = FontWeight.ExtraBold)
        Text(label.substringAfter('\n'), color = MistText, fontSize = 10.sp * scale, textAlign = TextAlign.Center)
    }
}

@Composable
private fun StatDivider(scale: Float) {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(46.dp * scale)
            .background(MutedSteel)
    )
}

@Composable
private fun TodayWorkoutCard(scale: Float, onStartSession: () -> Unit) {
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
                    .background(Color(0xFF17243A))
                    .border(1.dp, Color.White.copy(.05f), RoundedCornerShape(15.dp * scale))
                    .padding(horizontal = 12.dp * scale),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Push Day", color = TextWhite, fontSize = 15.sp * scale, fontWeight = FontWeight.ExtraBold)
                    Text("Chest • Shoulders • Triceps", color = MistText, fontSize = 10.sp * scale)
                    Spacer(Modifier.height(8.dp * scale))
                    Text("01:15:00     |     6 Exercises", color = MistText, fontSize = 10.sp * scale)
                }
                Box(
                    modifier = Modifier
                        .width(82.dp * scale)
                        .height(43.dp * scale)
                        .clip(RoundedCornerShape(13.dp * scale))
                        .background(Brush.verticalGradient(listOf(HotRed, WineShadow)))
                        .border(1.dp, HotRed.copy(.5f), RoundedCornerShape(13.dp * scale))
                        .clickable { onStartSession() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Start\nWorkout   >", color = TextWhite, fontSize = 10.sp * scale, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SplitCard(scale: Float) {
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
                            .background(if (index == 0) Brush.verticalGradient(listOf(HotRed, WineShadow)) else Brush.verticalGradient(listOf(Color(0xFF1A263C), Color(0xFF142136))))
                            .border(1.dp, if (index == 0) BloomPeach else Color.White.copy(.1f), RoundedCornerShape(13.dp * scale)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(label, color = TextWhite, fontSize = 8.sp * scale, textAlign = TextAlign.Center, lineHeight = 11.sp * scale)
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
                Text("No recent workouts.", color = MistText, fontSize = 12.sp * scale)
            }
        }
    }
}

@Composable
private fun WorkoutHistoryRow(title: String, subtitle: String, date: String, scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(43.dp * scale)
            .clip(RoundedCornerShape(13.dp * scale))
            .background(Color(0xFF17243A))
            .padding(horizontal = 10.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp * scale)
                .clip(CircleShape)
                .border(1.dp, Color.White.copy(.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            NavIcon(AppDestination.Workout, HotRed, 18.dp * scale)
        }
        Spacer(Modifier.width(12.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextWhite, fontSize = 12.sp * scale)
            Text(subtitle, color = MistText, fontSize = 8.5.sp * scale)
        }
        Text(date, color = MistText, fontSize = 9.sp * scale, textAlign = TextAlign.End, lineHeight = 12.sp * scale)
        Spacer(Modifier.width(8.dp * scale))
        Text(">", color = MistText, fontSize = 16.sp * scale)
    }
}

@Composable
private fun MuscleFocusCard(scale: Float) {
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
                                .background(Color(0xFF18253A)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("●", color = HotRed.copy(.85f), fontSize = 26.sp * scale)
                        }
                        Text(it, color = MistText, fontSize = 8.sp * scale, textAlign = TextAlign.Center, lineHeight = 11.sp * scale)
                    }
                }
            }
        }
    }
}
