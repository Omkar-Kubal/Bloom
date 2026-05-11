package com.appylab.bloom.feature.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.feature.onboarding.OnboardingViewModel
import com.appylab.bloom.feature.onboarding.components.InputField
import com.appylab.bloom.feature.onboarding.components.OnboardingScaffold
import com.appylab.bloom.feature.onboarding.components.RadioOptionCard

@Composable
fun Onboarding8BodyStatsScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val canContinue = state.heightCm > 0f && state.weightKg > 0f && state.goalWeightKg > 0f

    fun update(
        height: Float = state.heightCm,
        weight: Float = state.weightKg,
        goalWeight: Float = state.goalWeightKg,
        heightUnit: String = state.heightUnit,
        weightUnit: String = state.weightUnit
    ) = viewModel.updateBodyStats(height, weight, goalWeight, heightUnit, weightUnit)

    OnboardingScaffold(
        currentStep = 8,
        onContinue = {
            viewModel.calculateAndSetTdee()
            onNext()
        },
        continueEnabled = canContinue
    ) {
        Text("Body stats", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Use the units you prefer. Bloom stores normalized values.", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RadioOptionCard(
                label = "cm",
                selected = state.heightUnit == "cm",
                onClick = { update(heightUnit = "cm") },
                modifier = Modifier.weight(1f)
            )
            RadioOptionCard(
                label = "ft/in",
                selected = state.heightUnit == "ftin",
                onClick = { update(heightUnit = "ftin") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(12.dp))
        InputField(
            value = if (state.heightCm == 0f) "" else displayHeight(state.heightCm, state.heightUnit).clean(),
            onValueChange = { update(height = parseHeight(it, state.heightUnit)) },
            placeholder = if (state.heightUnit == "cm") "Height (cm)" else "Height (inches)",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RadioOptionCard(
                label = "kg",
                selected = state.weightUnit == "kg",
                onClick = { update(weightUnit = "kg") },
                modifier = Modifier.weight(1f)
            )
            RadioOptionCard(
                label = "lbs",
                selected = state.weightUnit == "lbs",
                onClick = { update(weightUnit = "lbs") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(12.dp))
        InputField(
            value = if (state.weightKg == 0f) "" else displayWeight(state.weightKg, state.weightUnit).clean(),
            onValueChange = { update(weight = parseWeight(it, state.weightUnit)) },
            placeholder = "Current weight (${state.weightUnit})",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(Modifier.height(12.dp))
        InputField(
            value = if (state.goalWeightKg == 0f) "" else displayWeight(state.goalWeightKg, state.weightUnit).clean(),
            onValueChange = { update(goalWeight = parseWeight(it, state.weightUnit)) },
            placeholder = "Goal weight (${state.weightUnit})",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
    }
}

private fun Float.clean(): String = if (this % 1f == 0f) toInt().toString() else toString()
private fun displayHeight(heightCm: Float, unit: String): Float = if (unit == "ftin") heightCm / 2.54f else heightCm
private fun parseHeight(input: String, unit: String): Float {
    val value = input.toFloatOrNull() ?: return 0f
    return if (unit == "ftin") value * 2.54f else value
}

private fun displayWeight(weightKg: Float, unit: String): Float = if (unit == "lbs") weightKg * 2.20462f else weightKg
private fun parseWeight(input: String, unit: String): Float {
    val value = input.toFloatOrNull() ?: return 0f
    return if (unit == "lbs") value / 2.20462f else value
}
