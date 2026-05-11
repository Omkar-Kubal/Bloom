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
fun Onboarding6ActivityScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val options = listOf(
        Triple("NOT", "Not active", "Mostly a desk job"),
        Triple("LIGHT", "Light", "Exercise 1-3 days a week"),
        Triple("ACTIVE", "Active", "Exercise 3-5 days a week"),
        Triple("VERY_ACTIVE", "Very active", "Exercise 6-7 days a week")
    )

    OnboardingScaffold(
        currentStep = 6,
        onContinue = onNext,
        continueEnabled = state.activityLevel.isNotBlank()
    ) {
        Text("How active are you?", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("This helps Bloom estimate your daily energy needs.", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
        Spacer(Modifier.height(28.dp))
        options.forEach { (value, label, description) ->
            RadioOptionCard(
                label = label,
                description = description,
                selected = state.activityLevel == value,
                onClick = { viewModel.updateActivityLevel(value) }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}
