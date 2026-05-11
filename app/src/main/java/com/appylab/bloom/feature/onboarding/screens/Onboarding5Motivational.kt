package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.runtime.Composable
import com.appylab.bloom.feature.onboarding.components.MotivationalStep

@Composable
fun Onboarding5Motivational(onNext: () -> Unit) {
    MotivationalStep(
        currentStep = 5,
        title = "Not perfect.",
        subtitle = "Just consistent.",
        onNext = onNext
    )
}
