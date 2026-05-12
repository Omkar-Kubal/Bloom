package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.appylab.bloom.feature.onboarding.OnboardingViewModel
import com.appylab.bloom.feature.onboarding.components.OnboardingScaffold

@Composable
fun Onboarding2GoalScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val options = listOf(
        "Lose Weight", "Maintain Weight", "Gain Weight", "Gain Muscle",
        "Plan Meal", "Track Meals", "Track Cardio", "Track Runs"
    )

    OnboardingScaffold(
        currentStep = 2,
        onContinue = onNext,
        continueEnabled = state.goals.isNotEmpty()
    ) {
        Text(
            text = "What are your goals?",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select all that apply to personalize your experience.",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(options) { option ->
                val selected = state.goals.contains(option)
                GoalChipCard(
                    label = option,
                    selected = selected,
                    onClick = { viewModel.toggleGoal(option) }
                )
            }
        }
    }
}

@Composable
fun GoalChipCard(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val bgColor     = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() }
            .background(bgColor, MaterialTheme.shapes.small)
            .border(1.dp, borderColor, MaterialTheme.shapes.small)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}
