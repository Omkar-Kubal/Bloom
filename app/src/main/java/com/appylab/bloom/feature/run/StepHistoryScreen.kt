package com.appylab.bloom.feature.run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.screenBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepHistoryScreen(
    onBack: () -> Unit
) {
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
            Text("Step History", color = TextWhite, style = MaterialTheme.typography.titleLarge)
        }

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Vico bar chart history coming soon!", color = TextWhite)
        }
    }
}
