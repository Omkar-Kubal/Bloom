package com.appylab.bloom.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.core.ui.screenBrush

@Composable
fun GetStartedScreen(
    onGetStarted: () -> Unit,
    onLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))

            Text(
                text = "bloom",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Your health, simplified.",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    "Get Started",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(20.dp))

            val onBg = MaterialTheme.colorScheme.onBackground
            val primary = MaterialTheme.colorScheme.primary
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = onBg.copy(alpha = 0.6f), fontSize = 14.sp)) {
                        append("Already have an account? ")
                    }
                    withStyle(SpanStyle(color = primary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)) {
                        append("Log in")
                    }
                },
                modifier = Modifier.clickable { onLogin() },
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(36.dp))
        }
    }
}
