package com.appylab.bloom.feature.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.domain.model.Exercise
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.screenBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseSearchScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onExerciseSelected: (Exercise) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val exercises by viewModel.exercises.collectAsState()

    LaunchedEffect(query) {
        viewModel.searchExercises(query)
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
                placeholder = { Text("Search exercises...", color = MistText) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(exercises) { exercise ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExerciseSelected(exercise) }
                        .padding(16.dp)
                ) {
                    Text(exercise.name, color = TextWhite)
                    Text(exercise.muscleGroup, color = MistText)
                }
            }
        }
    }
}
