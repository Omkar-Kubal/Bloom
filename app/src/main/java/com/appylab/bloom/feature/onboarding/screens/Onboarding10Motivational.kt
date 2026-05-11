package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.runtime.Composable
import com.appylab.bloom.feature.onboarding.components.MotivationalStep

@Composable
fun Onboarding10Motivational(onNext: () -> Unit) {
    MotivationalStep(
        currentStep = 10,
        title = "You are capable",
        subtitle = "of amazing things.",
        onNext = onNext
    )
}
