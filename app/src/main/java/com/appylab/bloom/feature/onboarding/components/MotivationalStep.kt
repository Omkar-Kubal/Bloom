package com.appylab.bloom.feature.onboarding.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MotivationalStep(
    currentStep: Int,
    title: String,
    subtitle: String,
    onNext: () -> Unit
) {
    OnboardingScaffold(
        currentStep = currentStep,
        onContinue = onNext
    ) {
        Spacer(Modifier.height(120.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = subtitle,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}
