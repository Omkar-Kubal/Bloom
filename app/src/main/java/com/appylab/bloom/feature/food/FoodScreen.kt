package com.appylab.bloom.feature.food

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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.data.db.entities.FoodEntry
import com.appylab.bloom.core.ui.AppBottomNav
import com.appylab.bloom.core.ui.GlassCard
import com.appylab.bloom.core.ui.SectionTitle
import com.appylab.bloom.core.ui.screenBrush
import com.appylab.bloom.navigation.AppDestination

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
    val totalCarbs    = dayLog.sumOf { it.carbs.toDouble() }.toFloat()
    val totalProtein  = dayLog.sumOf { it.protein.toDouble() }.toFloat()
    val totalFat      = dayLog.sumOf { it.fat.toDouble() }.toFloat()

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
                .padding(horizontal = 18.dp * scale)
                .padding(top = 18.dp * scale, bottom = (61 + 24).dp * scale),
            verticalArrangement = Arrangement.spacedBy(12.dp * scale)
        ) {
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp * scale, bottom = 4.dp * scale)
    ) {
        Text("Food", color = MaterialTheme.colorScheme.onBackground, fontSize = 24.sp * scale, fontWeight = FontWeight.ExtraBold)
        Text("Eat smart. Fuel better.", color = MaterialTheme.colorScheme.tertiary, fontSize = 15.sp * scale)
    }
}

@Composable
private fun CaloriesCard(scale: Float, consumed: Int, goal: Int) {
    val remaining = (goal - consumed).coerceAtLeast(0)
    val progress  = if (goal > 0) (consumed.toFloat() / goal).coerceIn(0f, 1f) else 0f

    val primary          = MaterialTheme.colorScheme.primary
    val secondary        = MaterialTheme.colorScheme.secondary
    val surfaceVariant   = MaterialTheme.colorScheme.surfaceVariant
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer

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
                    CalorieRing(progress, primary, surfaceVariant, Modifier.size(122.dp * scale))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("%,d".format(consumed), color = MaterialTheme.colorScheme.onSurface, fontSize = 22.sp * scale, fontWeight = FontWeight.ExtraBold)
                        Text("kcal consumed", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp * scale)
                        Spacer(Modifier.height(10.dp * scale))
                        Text("%,d kcal\nyour target".format(goal), color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp * scale, lineHeight = 15.sp * scale, textAlign = TextAlign.Center)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(.76f)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp * scale),
                verticalArrangement = Arrangement.spacedBy(12.dp * scale)
            ) {
                FoodStatRow("Remaining", "%,d kcal".format(remaining), if (remaining == 0) primary else MaterialTheme.colorScheme.onSurface, scale)
                FoodStatRow("Goal", "%,d kcal".format(goal), MaterialTheme.colorScheme.onSurface, scale)
                FoodStatRow("Consumed", "%,d kcal".format(consumed), primary, scale)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp * scale)
                        .clip(RoundedCornerShape(14.dp * scale))
                        .background(secondaryContainer)
                        .padding(horizontal = 11.dp * scale),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Log Food", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp * scale, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(29.dp * scale)
                            .clip(RoundedCornerShape(10.dp * scale))
                            .background(Brush.verticalGradient(listOf(primary, secondary))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", color = MaterialTheme.colorScheme.onPrimary, fontSize = 25.sp * scale)
                    }
                }
            }
        }
    }
}

@Composable
private fun FoodStatRow(label: String, value: String, valueColor: Color, scale: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp * scale)
        Text(value, color = valueColor, fontSize = 10.sp * scale, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun MacrosCard(scale: Float, carbs: Float, protein: Float, fat: Float) {
    val primary   = MaterialTheme.colorScheme.primary
    val tertiary  = MaterialTheme.colorScheme.tertiary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer

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
            MacroRow("Carbohydrates", "${carbs.toInt()} / 230 g", "${(cProgress * 100).toInt()}%", cProgress, primary, tertiary, surfaceVariant, secondaryContainer, scale)
            MacroRow("Protein", "${protein.toInt()} / 150 g", "${(pProgress * 100).toInt()}%", pProgress, primary, tertiary, surfaceVariant, secondaryContainer, scale)
            MacroRow("Fat", "${fat.toInt()} / 70 g", "${(fProgress * 100).toInt()}%", fProgress, primary, tertiary, surfaceVariant, secondaryContainer, scale)
        }
    }
}

@Composable
private fun MacroRow(
    label: String,
    amount: String,
    percent: String,
    progress: Float,
    primary: Color,
    tertiary: Color,
    surfaceVariant: Color,
    secondaryContainer: Color,
    scale: Float
) {
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
                .background(secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(label.take(1), color = primary, fontSize = 16.sp * scale, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(9.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp * scale)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp * scale)
                    .clip(RoundedCornerShape(99.dp))
                    .background(surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(Brush.horizontalGradient(listOf(tertiary, primary)))
                )
            }
        }
        Spacer(Modifier.width(10.dp * scale))
        Column(horizontalAlignment = Alignment.End) {
            Text(amount, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp * scale)
            Text(percent, color = primary, fontSize = 10.sp * scale)
        }
    }
}

@Composable
private fun MealsCard(scale: Float, dayLog: List<FoodEntry>) {
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val primary = MaterialTheme.colorScheme.primary
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
                MealRow(entry.mealType, entry.foodName, "${entry.calories} kcal", secondaryContainer, primary, scale)
            }
            if (dayLog.isEmpty()) {
                Text("No meals logged yet.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp * scale)
            }
        }
    }
}

@Composable
private fun MealRow(title: String, subtitle: String, kcal: String, bgColor: Color, accentColor: Color, scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp * scale)
            .clip(RoundedCornerShape(13.dp * scale))
            .background(bgColor)
            .padding(horizontal = 11.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp * scale)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(title.take(1), color = accentColor, fontSize = 12.sp * scale, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(10.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp * scale)
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 8.5.sp * scale, maxLines = 1)
        }
        Text(kcal, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp * scale)
        Spacer(Modifier.width(7.dp * scale))
        Text(">", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 15.sp * scale)
    }
}

@Composable
private fun QuickActionsCard(scale: Float, onSearch: () -> Unit, onBarcode: () -> Unit, onManual: () -> Unit) {
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val outline = MaterialTheme.colorScheme.outline
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(102.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(15.dp * scale)) {
            Text("Quick Actions", color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp * scale, fontWeight = FontWeight.Bold)
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
                            .background(secondaryContainer)
                            .border(0.5.dp, outline.copy(.4f), RoundedCornerShape(12.dp * scale))
                            .clickable { onClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(label, color = MaterialTheme.colorScheme.onSurface, fontSize = 8.sp * scale, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
private fun CalorieRing(progress: Float, fillColor: Color, trackColor: Color, modifier: Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 9.dp.toPx()
        val rect = Rect(stroke / 2, stroke / 2, size.width - stroke / 2, size.height - stroke / 2)
        drawArc(trackColor, -90f, 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(fillColor, -88f, progress * 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
    }
}
