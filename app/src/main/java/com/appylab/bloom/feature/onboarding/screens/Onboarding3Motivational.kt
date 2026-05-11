package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.runtime.Composable
import com.appylab.bloom.feature.onboarding.components.MotivationalStep

@Composable
fun Onboarding3Motivational(
    onNext: () -> Unit
) {
    MotivationalStep(
        currentStep = 3,
        title = "You are your only limit.",
        subtitle = "Keep going.",
        onNext = onNext
    )
}
