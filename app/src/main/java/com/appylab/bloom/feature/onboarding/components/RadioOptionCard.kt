package com.appylab.bloom.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioOptionCard(
    label: String,
    description: String? = null,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val bgColor     = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(bgColor, MaterialTheme.shapes.small)
            .border(1.dp, borderColor, MaterialTheme.shapes.small)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            if (description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    if (selected) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent,
                    CircleShape
                )
                .border(2.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                )
            }
        }
    }
}
