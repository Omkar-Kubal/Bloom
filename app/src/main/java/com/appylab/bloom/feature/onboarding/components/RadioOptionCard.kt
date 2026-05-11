package com.appylab.bloom.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.core.ui.*

@Composable
fun RadioOptionCard(
    label: String,
    description: String? = null,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (selected) HotRed else SlateBorder
    val bgColor = if (selected) HotRed.copy(alpha = 0.1f) else Color(0xFF1E1E2E)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            if (description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, color = Color.Gray, fontSize = 14.sp)
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(if (selected) HotRed else Color.Transparent, CircleShape)
                .border(2.dp, if (selected) HotRed else SlateBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(modifier = Modifier.size(10.dp).background(Color.White, CircleShape))
            }
        }
    }
}
