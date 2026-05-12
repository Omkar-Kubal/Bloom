package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appylab.bloom.feature.onboarding.components.OnboardingScaffold
import com.appylab.bloom.feature.onboarding.components.RadioOptionCard

@Composable
internal fun FrequencyScreen(
    title: String,
    subtitle: String,
    currentStep: Int,
    selected: String,
    onSelect: (String) -> Unit,
    onNext: () -> Unit
) {
    val options = listOf("Never", "Rarely", "Occasionally", "Frequently", "Always")
    OnboardingScaffold(
        currentStep = currentStep,
        onContinue = onNext,
        continueEnabled = selected.isNotBlank()
    ) {
        Text(
            title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            subtitle,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(28.dp))
        options.forEach { option ->
            RadioOptionCard(
                label = option,
                selected = selected == option.uppercase(),
                onClick = { onSelect(option.uppercase()) }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}
