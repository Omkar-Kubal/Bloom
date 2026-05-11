package com.appylab.bloom.feature.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.screenBrush

@Composable
fun AiWorkoutSummaryScreen(
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text("AI Workout Summary", color = TextWhite, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Text("Generating summary from Gemini...", color = TextWhite)
        }
        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
            Text("Done")
        }
    }
}
