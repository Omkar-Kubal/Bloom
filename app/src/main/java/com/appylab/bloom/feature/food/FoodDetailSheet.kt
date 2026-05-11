package com.appylab.bloom.feature.food

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.core.domain.model.FoodItem
import com.appylab.bloom.core.ui.BloomPeach
import com.appylab.bloom.core.ui.HotRed
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.MutedSteel
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.WineShadow

private val MEAL_TYPES = listOf("Breakfast", "Lunch", "Dinner", "Snacks")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailSheet(
    foodItem: FoodItem,
    onDismiss: () -> Unit,
    onLog: (grams: Float, mealType: String) -> Unit
) {
    var grams by remember { mutableStateOf("100") }
    var selectedMealType by remember { mutableStateOf("Breakfast") }
    var mealDropdownExpanded by remember { mutableStateOf(false) }

    val gramsFloat = grams.toFloatOrNull() ?: 100f
    val ratio = gramsFloat / 100f
    val previewCal = (foodItem.caloriesPer100g * ratio).toInt()
    val previewCarbs = foodItem.carbsPer100g * ratio
    val previewProtein = foodItem.proteinPer100g * ratio
    val previewFat = foodItem.fatPer100g * ratio

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF141C2E)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(foodItem.name, color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            if (!foodItem.brand.isNullOrBlank()) {
                Text(foodItem.brand, color = MistText, fontSize = 13.sp)
            }

            Spacer(Modifier.height(20.dp))

            // Nutrition preview row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NutrientChip("$previewCal kcal", "Calories", Modifier.weight(1f))
                NutrientChip("${"%.1f".format(previewCarbs)}g", "Carbs", Modifier.weight(1f))
                NutrientChip("${"%.1f".format(previewProtein)}g", "Protein", Modifier.weight(1f))
                NutrientChip("${"%.1f".format(previewFat)}g", "Fat", Modifier.weight(1f))
            }

            Spacer(Modifier.height(20.dp))

            // Grams input
            OutlinedTextField(
                value = grams,
                onValueChange = { grams = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Amount (grams)", color = MistText) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    cursorColor = BloomPeach,
                    focusedIndicatorColor = BloomPeach,
                    unfocusedIndicatorColor = MutedSteel,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(12.dp))

            // Meal type selector
            Text("Meal", color = MistText, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MEAL_TYPES.forEach { type ->
                    val selected = type == selectedMealType
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (selected) Brush.horizontalGradient(listOf(BloomPeach, HotRed))
                                else Brush.horizontalGradient(listOf(Color(0xFF1A263C), Color(0xFF1A263C)))
                            )
                            .border(1.dp, if (selected) Color.Transparent else MutedSteel, RoundedCornerShape(10.dp))
                            .clickable { selectedMealType = type },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(type, color = TextWhite, fontSize = 10.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Log button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.horizontalGradient(listOf(BloomPeach, HotRed)))
                    .clickable {
                        onLog(gramsFloat, selectedMealType)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Log $previewCal kcal to $selectedMealType", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun NutrientChip(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF1A263C))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Text(label, color = MistText, fontSize = 10.sp)
    }
}
