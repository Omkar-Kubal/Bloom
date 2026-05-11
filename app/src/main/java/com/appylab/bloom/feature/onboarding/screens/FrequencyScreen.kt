package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Text(title, color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
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
