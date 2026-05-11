package com.appylab.bloom.feature.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.ui.BloomPeach
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.screenBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSessionScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    onAddExercise: () -> Unit,
    onFinishSession: () -> Unit,
    onCancel: () -> Unit
) {
    val restTimer by viewModel.restTimerSeconds.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onCancel) {
                Icon(Icons.Default.Close, "Cancel", tint = TextWhite)
            }
            Text("Active Session", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = {
                viewModel.finishSession { onFinishSession() }
            }) {
                Icon(Icons.Default.Check, "Finish", tint = BloomPeach)
            }
        }

        if (restTimer > 0) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp).background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text("Resting: ${restTimer}s", color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        FloatingActionButton(
            onClick = onAddExercise,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(32.dp),
            containerColor = BloomPeach
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Exercise", tint = TextWhite)
        }
    }
}
