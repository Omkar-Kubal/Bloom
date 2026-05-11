package com.appylab.bloom.feature.food

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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.MutedSteel
import com.appylab.bloom.core.ui.NavIcon
import com.appylab.bloom.core.ui.SectionTitle
import com.appylab.bloom.core.ui.StatusBar
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.WineShadow
import com.appylab.bloom.core.ui.screenBrush
import com.appylab.bloom.core.data.db.entities.FoodEntry
import com.appylab.bloom.navigation.AppDestination

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FoodScreen(
    viewModel: FoodViewModel = hiltViewModel(),
    onDestinationSelected: (AppDestination) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToBarcode: () -> Unit,
    onNavigateToManual: () -> Unit
) {
    val dayLog by viewModel.dayLog.collectAsState()
    val calorieGoal by viewModel.dailyCalorieGoal.collectAsState()

    val totalCalories = dayLog.sumOf { it.calories }
    val totalCarbs = dayLog.sumOf { it.carbs.toDouble() }.toFloat()
    val totalProtein = dayLog.sumOf { it.protein.toDouble() }.toFloat()
    val totalFat = dayLog.sumOf { it.fat.toDouble() }.toFloat()

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
                .padding(horizontal = 18.dp * scale)
                .padding(top = 18.dp * scale, bottom = (61 + 24).dp * scale),
            verticalArrangement = Arrangement.spacedBy(12.dp * scale)
        ) {
            StatusBar(scale)
            FoodHeader(scale)
            CaloriesCard(scale, totalCalories, calorieGoal)
            MacrosCard(scale, totalCarbs, totalProtein, totalFat)
            MealsCard(scale, dayLog)
            QuickActionsCard(scale, onNavigateToSearch, onNavigateToBarcode, onNavigateToManual)
        }
        AppBottomNav(
            active = AppDestination.Food,
            scale = scale,
            onDestinationSelected = onDestinationSelected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 18.dp * scale)
                .padding(bottom = 16.dp * scale)
        )
    }
}

@Composable
private fun FoodHeader(scale: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp * scale)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(top = 10.dp * scale)
        ) {
            Text("Food", color = TextWhite, fontSize = 24.sp * scale, fontWeight = FontWeight.ExtraBold)
            Text("Eat smart. Fuel better.", color = BloomPeach, fontSize = 15.sp * scale)
        }
        MascotArt(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-52).dp * scale, y = (-12).dp * scale)
                .size(140.dp * scale)
        )
    }
}

@Composable
private fun CaloriesCard(scale: Float, consumed: Int, goal: Int) {
    val remaining = (goal - consumed).coerceAtLeast(0)
    val progress = if (goal > 0) (consumed.toFloat() / goal).coerceIn(0f, 1f) else 0f

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(174.dp * scale),
        scale = scale
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(17.dp * scale),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                SectionTitle(AppDestination.Run, "Calories", scale)
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CalorieRing(progress, Modifier.size(122.dp * scale))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("%,d".format(consumed), color = TextWhite, fontSize = 22.sp * scale, fontWeight = FontWeight.ExtraBold)
                        Text("kcal consumed", color = MistText, fontSize = 12.sp * scale)
                        Spacer(Modifier.height(10.dp * scale))
                        Text("%,d kcal\nyour target".format(goal), color = MistText, fontSize = 11.sp * scale, lineHeight = 15.sp * scale, textAlign = TextAlign.Center)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(.76f)
                    .background(MutedSteel)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp * scale),
                verticalArrangement = Arrangement.spacedBy(12.dp * scale)
            ) {
                FoodStatRow("Remaining", "%,d kcal".format(remaining), if (remaining == 0) HotRed else TextWhite, scale)
                FoodStatRow("Goal", "%,d kcal".format(goal), TextWhite, scale)
                FoodStatRow("Consumed", "%,d kcal".format(consumed), HotRed, scale)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp * scale)
                        .clip(RoundedCornerShape(14.dp * scale))
                        .background(Brush.linearGradient(listOf(Color(0xFF273047), WineShadow.copy(.55f))))
                        .padding(horizontal = 11.dp * scale),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Log Food", color = TextWhite, fontSize = 12.sp * scale, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(29.dp * scale)
                            .clip(RoundedCornerShape(10.dp * scale))
                            .background(Brush.verticalGradient(listOf(HotRed, Color(0xFFC31B2D)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", color = TextWhite, fontSize = 25.sp * scale)
                    }
                }
            }
        }
    }
}

@Composable
private fun FoodStatRow(label: String, value: String, valueColor: Color, scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.dp, Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MistText, fontSize = 10.sp * scale)
        Text(value, color = valueColor, fontSize = 10.sp * scale, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun MacrosCard(scale: Float, carbs: Float, protein: Float, fat: Float) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(169.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(16.dp * scale)) {
            SectionTitle(null, "Macros", scale, action = "Details  >")
            Spacer(Modifier.height(12.dp * scale))
            val cProgress = (carbs / 230f).coerceIn(0f, 1f)
            val pProgress = (protein / 150f).coerceIn(0f, 1f)
            val fProgress = (fat / 70f).coerceIn(0f, 1f)
            MacroRow("Carbohydrates", "${carbs.toInt()} / 230 g", "${(cProgress * 100).toInt()}%", cProgress, scale)
            MacroRow("Protein", "${protein.toInt()} / 150 g", "${(pProgress * 100).toInt()}%", pProgress, scale)
            MacroRow("Fat", "${fat.toInt()} / 70 g", "${(fProgress * 100).toInt()}%", fProgress, scale)
        }
    }
}

@Composable
private fun MacroRow(label: String, amount: String, percent: String, progress: Float, scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp * scale)
                .clip(RoundedCornerShape(10.dp * scale))
                .background(Color(0xFF242B47)),
            contentAlignment = Alignment.Center
        ) {
            Text(label.take(1), color = HotRed, fontSize = 16.sp * scale, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(9.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, color = TextWhite, fontSize = 12.sp * scale)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp * scale)
                    .clip(RoundedCornerShape(99.dp))
                    .background(MutedSteel)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(Brush.horizontalGradient(listOf(BloomPeach, HotRed)))
                )
            }
        }
        Spacer(Modifier.width(10.dp * scale))
        Column(horizontalAlignment = Alignment.End) {
            Text(amount, color = MistText, fontSize = 10.sp * scale)
            Text(percent, color = HotRed, fontSize = 10.sp * scale)
        }
    }
}

@Composable
private fun MealsCard(scale: Float, dayLog: List<FoodEntry>) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(214.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(15.dp * scale), verticalArrangement = Arrangement.spacedBy(8.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SectionTitle(AppDestination.Food, "Today's Meals", scale, action = "${dayLog.sumOf { it.calories }} kcal")
            }
            dayLog.take(4).forEach { entry ->
                MealRow(entry.mealType, entry.foodName, "${entry.calories} kcal", scale)
            }
            if (dayLog.isEmpty()) {
                Text("No meals logged yet.", color = MistText, fontSize = 12.sp * scale)
            }
        }
    }
}

@Composable
private fun MealRow(title: String, subtitle: String, kcal: String, scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp * scale)
            .clip(RoundedCornerShape(13.dp * scale))
            .background(Color(0xFF17243A))
            .padding(horizontal = 11.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp * scale)
                .clip(CircleShape)
                .background(AbyssNavy),
            contentAlignment = Alignment.Center
        ) {
            Text(title.take(1), color = HotRed, fontSize = 12.sp * scale, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(10.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextWhite, fontSize = 12.sp * scale)
            Text(subtitle, color = MistText, fontSize = 8.5.sp * scale, maxLines = 1)
        }
        Text(kcal, color = MistText, fontSize = 10.sp * scale)
        Spacer(Modifier.width(7.dp * scale))
        Text(">", color = MistText, fontSize = 15.sp * scale)
    }
}

@Composable
private fun QuickActionsCard(scale: Float, onSearch: () -> Unit, onBarcode: () -> Unit, onManual: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(102.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(15.dp * scale)) {
            Text("Quick Actions", color = TextWhite, fontSize = 13.sp * scale, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp * scale))
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp * scale)) {
                val actions = listOf(
                    "Search Food" to onSearch,
                    "Scan Barcode" to onBarcode,
                    "Manual Entry" to onManual,
                    "Recent Foods" to {}
                )
                actions.forEach { (label, onClick) ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp * scale)
                            .clip(RoundedCornerShape(12.dp * scale))
                            .background(Brush.verticalGradient(listOf(WineShadow.copy(.95f), Color(0xFF2A233B))))
                            .border(1.dp, HotRed.copy(.5f), RoundedCornerShape(12.dp * scale))
                            .clickable { onClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(label, color = TextWhite, fontSize = 8.sp * scale, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
private fun CalorieRing(progress: Float, modifier: Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 9.dp.toPx()
        val rect = Rect(stroke / 2, stroke / 2, size.width - stroke / 2, size.height - stroke / 2)
        drawArc(WineShadow.copy(.68f), -90f, 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(HotRed, -88f, progress * 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
    }
}
