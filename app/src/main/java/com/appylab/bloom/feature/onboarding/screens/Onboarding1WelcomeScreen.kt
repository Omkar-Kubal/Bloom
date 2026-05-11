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
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "What should we call you?",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        InputField(
            value = state.username,
            onValueChange = { viewModel.updateUsername(it) },
            placeholder = "Enter your name"
        )
    }
}
