package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.appylab.bloom.feature.onboarding.OnboardingViewModel

@Composable
fun Onboarding12RunScreen(
    viewModel: OnboardingViewModel,
    onFinish: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    FrequencyScreen(
        title = "How often do you run?",
        subtitle = "We'll tune your cardio and step experience.",
        currentStep = 12,
        selected = state.runFrequency,
        onSelect = viewModel::updateRunFrequency,
        onNext = { viewModel.finishOnboarding(onFinish) }
    )
}
