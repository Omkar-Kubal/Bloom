package com.appylab.bloom.feature.food

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.data.db.entities.FoodEntry
import com.appylab.bloom.core.ui.BloomPeach
import com.appylab.bloom.core.ui.HotRed
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.MutedSteel
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.screenBrush

private val MEAL_TYPES = listOf("Breakfast", "Lunch", "Dinner", "Snacks")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualFoodEntryScreen(
    viewModel: FoodViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var grams by remember { mutableStateOf("") }
    var selectedMealType by remember { mutableStateOf("Breakfast") }

    val fieldColors = TextFieldDefaults.colors(
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite,
        cursorColor = BloomPeach,
        focusedIndicatorColor = BloomPeach,
        unfocusedIndicatorColor = MutedSteel,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            Text("Manual Entry", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("Food Name *", color = MistText) },
            modifier = Modifier.fillMaxWidth(),
            colors = fieldColors
        )

        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = calories, onValueChange = { calories = it },
                label = { Text("Calories (kcal) *", color = MistText) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = fieldColors
            )
            OutlinedTextField(
                value = grams, onValueChange = { grams = it },
                label = { Text("Amount (g)", color = MistText) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = fieldColors
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = carbs, onValueChange = { carbs = it },
                label = { Text("Carbs (g)", color = MistText) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = fieldColors
            )
            OutlinedTextField(
                value = protein, onValueChange = { protein = it },
                label = { Text("Protein (g)", color = MistText) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = fieldColors
            )
            OutlinedTextField(
                value = fat, onValueChange = { fat = it },
                label = { Text("Fat (g)", color = MistText) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = fieldColors
            )
        }

        Spacer(Modifier.height(20.dp))

        Text("Meal", color = MistText, fontSize = 13.sp)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MEAL_TYPES.forEach { type ->
                val selected = type == selectedMealType
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (selected) Brush.horizontalGradient(listOf(BloomPeach, HotRed))
                            else Brush.horizontalGradient(listOf(Color(0xFF1A263C), Color(0xFF1A263C)))
                        )
                        .border(1.dp, if (selected) Color.Transparent else MutedSteel, RoundedCornerShape(10.dp))
                        .clickable { selectedMealType = type },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        type,
                        color = TextWhite,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    if (name.isNotBlank() && calories.isNotBlank())
                        Brush.horizontalGradient(listOf(BloomPeach, HotRed))
                    else
                        Brush.horizontalGradient(listOf(MutedSteel, MutedSteel))
                )
                .clickable {
                    val cal = calories.toIntOrNull() ?: 0
                    if (name.isNotBlank() && cal > 0) {
                        val entry = FoodEntry(
                            userId = "",
                            date = "",
                            mealType = selectedMealType,
                            foodName = name,
                            brand = null,
                            grams = grams.toFloatOrNull(),
                            quantity = if (grams.isBlank()) 1f else null,
                            calories = cal,
                            carbs = carbs.toFloatOrNull() ?: 0f,
                            protein = protein.toFloatOrNull() ?: 0f,
                            fat = fat.toFloatOrNull() ?: 0f,
                            barcode = null,
                            source = "Manual",
                            loggedAt = 0L
                        )
                        viewModel.logManualFood(entry)
                        onBack()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Log to $selectedMealType", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))
        Text("* Required fields", color = MistText, fontSize = 11.sp)
    }
}
