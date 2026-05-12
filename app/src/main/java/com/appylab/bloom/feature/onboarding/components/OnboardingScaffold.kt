package com.appylab.bloom.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appylab.bloom.core.ui.screenBrush

@Composable
fun OnboardingScaffold(
    currentStep: Int,
    totalSteps: Int = 12,
    onContinue: () -> Unit,
    onSkip: (() -> Unit)? = null,
    continueEnabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        // Progress bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..totalSteps) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .padding(horizontal = 2.dp)
                        .background(
                            if (i <= currentStep) primary else surfaceVariant,
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            content = content
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onContinue,
            enabled = continueEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                "Continue",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (onSkip != null) {
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(
                onClick = onSkip,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Skip for now",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
