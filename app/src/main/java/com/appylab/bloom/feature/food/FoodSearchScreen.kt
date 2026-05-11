package com.appylab.bloom.feature.food

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.domain.model.FoodItem
import com.appylab.bloom.core.ui.BloomPeach
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.screenBrush
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSearchScreen(
    viewModel: FoodViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onFoodSelected: (FoodItem) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    var selectedFood by remember { mutableStateOf<FoodItem?>(null) }

    // Debounce search
    LaunchedEffect(query) {
        delay(400)
        viewModel.searchFood(query)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search foods...", color = MistText) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    cursorColor = BloomPeach,
                    focusedIndicatorColor = BloomPeach,
                    unfocusedIndicatorColor = Color(0xFF2A233B),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MistText) }
            )
        }

        if (isSearching) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = BloomPeach)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(searchResults) { item ->
                    FoodResultItem(item, onClick = { selectedFood = item })
                }
            }
        }
    }

    selectedFood?.let { food ->
        FoodDetailSheet(
            foodItem = food,
            onDismiss = { selectedFood = null },
            onLog = { grams, mealType ->
                viewModel.logFood(food, grams, mealType)
                selectedFood = null
                onBack()
            }
        )
    }
}

@Composable
private fun FoodResultItem(item: FoodItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(item.brand.ifEmpty { "Generic" }, color = MistText, fontSize = 12.sp)
        }
        Text("${item.caloriesPer100g.toInt()} kcal", color = BloomPeach, fontSize = 14.sp)
    }
}
