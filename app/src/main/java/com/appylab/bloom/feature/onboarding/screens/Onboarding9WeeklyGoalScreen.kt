package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.feature.onboarding.OnboardingViewModel
import com.appylab.bloom.feature.onboarding.components.OnboardingScaffold
import com.appylab.bloom.feature.onboarding.components.RadioOptionCard

@Composable
fun Onboarding9WeeklyGoalScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    OnboardingScaffold(
        currentStep = 9,
        onContinue = onNext,
        continueEnabled = state.weeklyGoal.isNotBlank()
    ) {
        Text("Weekly goal", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Pick the pace that feels sustainable.", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
        Spacer(Modifier.height(28.dp))
        RadioOptionCard(
            label = "Gain 0.5kg",
            description = "A steady lean-gain target",
            selected = state.weeklyGoal == "Gain 0.5kg",
            onClick = { viewModel.updateWeeklyGoal("Gain 0.5kg") }
        )
        Spacer(Modifier.height(12.dp))
        RadioOptionCard(
            label = "Lose 0.5kg",
            description = "A moderate fat-loss target",
            selected = state.weeklyGoal == "Lose 0.5kg",
            onClick = { viewModel.updateWeeklyGoal("Lose 0.5kg") }
        )
    }
}
