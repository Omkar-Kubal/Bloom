package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.appylab.bloom.feature.onboarding.OnboardingViewModel
import com.appylab.bloom.feature.onboarding.components.InputField
import com.appylab.bloom.feature.onboarding.components.OnboardingScaffold
import com.appylab.bloom.feature.onboarding.components.RadioOptionCard

@Composable
fun Onboarding7AboutYouScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val canContinue = state.gender.isNotBlank() && state.age > 0 && state.country.isNotBlank()

    fun update(
        gender: String = state.gender,
        age: Int = state.age,
        country: String = state.country,
        zipCode: String = state.zipCode
    ) = viewModel.updateAboutYou(gender, age, country, zipCode)

    OnboardingScaffold(
        currentStep = 7,
        onContinue = onNext,
        continueEnabled = canContinue
    ) {
        Text(
            "A little about you",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Your answers tune calorie and progress targets.",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RadioOptionCard(
                label = "Male",
                selected = state.gender == "Male",
                onClick = { update(gender = "Male") },
                modifier = Modifier.weight(1f)
            )
            RadioOptionCard(
                label = "Female",
                selected = state.gender == "Female",
                onClick = { update(gender = "Female") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(16.dp))
        InputField(
            value = if (state.age == 0) "" else state.age.toString(),
            onValueChange = { update(age = it.toIntOrNull() ?: 0) },
            placeholder = "Age",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(12.dp))
        InputField(
            value = state.country,
            onValueChange = { update(country = it) },
            placeholder = "Country"
        )
        Spacer(Modifier.height(12.dp))
        InputField(
            value = state.zipCode,
            onValueChange = { update(zipCode = it) },
            placeholder = "ZIP code"
        )
    }
}
