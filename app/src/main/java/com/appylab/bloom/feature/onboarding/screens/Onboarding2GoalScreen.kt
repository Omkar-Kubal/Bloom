package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.core.ui.HotRed
import com.appylab.bloom.core.ui.SlateBorder
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
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select all that apply to personalize your experience.",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 16.sp
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
    val borderColor = if (selected) HotRed else SlateBorder
    val bgColor = if (selected) HotRed.copy(alpha = 0.1f) else Color(0xFF1E1E2E)

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() }
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}
