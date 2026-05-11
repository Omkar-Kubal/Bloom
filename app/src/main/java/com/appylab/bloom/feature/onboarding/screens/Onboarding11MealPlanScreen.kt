package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.appylab.bloom.feature.onboarding.OnboardingViewModel

@Composable
fun Onboarding11MealPlanScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    FrequencyScreen(
        title = "How often do you plan meals?",
        subtitle = "Bloom can meet you where you are.",
        currentStep = 11,
        selected = state.mealFrequency,
        onSelect = viewModel::updateMealFrequency,
        onNext = onNext
    )
}
