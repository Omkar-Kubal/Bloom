package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appylab.bloom.feature.onboarding.OnboardingViewModel
import com.appylab.bloom.feature.onboarding.components.InputField
import com.appylab.bloom.feature.onboarding.components.OnboardingScaffold

@Composable
fun Onboarding1WelcomeScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    OnboardingScaffold(
        currentStep = 1,
        onContinue = onNext,
        continueEnabled = state.username.isNotBlank()
    ) {
        Text(
            text = "Welcome to Bloom!",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "What should we call you?",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        InputField(
            value = state.username,
            onValueChange = { viewModel.updateUsername(it) },
            placeholder = "Enter your name"
        )
    }
}
