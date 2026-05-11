package com.appylab.bloom.feature.paywall

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.ui.HotRed
import com.appylab.bloom.core.ui.MascotArt
import com.appylab.bloom.core.ui.SlateBorder
import com.appylab.bloom.core.ui.screenBrush

@Composable
fun PaywallScreen(
    onTrialStarted: () -> Unit,
    viewModel: PaywallViewModel = hiltViewModel()
) {
    BackHandler(enabled = true) {}

    val selectedPlan by viewModel.selectedPlan.collectAsState()
    val features = listOf(
        "AI Workout Summary",
        "Barcode Scanner",
        "Advanced Charts",
        "Unlimited Food Logging"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text("Unlock Bloom Pro", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(12.dp))
        MascotArt(Modifier.size(148.dp))
        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            features.forEach { feature ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✓", color = HotRed, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = feature,
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, SlateBorder, RoundedCornerShape(28.dp))
                .background(Color(0xFF1E1E2E))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            PlanPill(
                title = "Monthly",
                price = "\$9.99",
                selected = selectedPlan == Plan.Monthly,
                onClick = { viewModel.selectPlan(Plan.Monthly) },
                modifier = Modifier.weight(1f)
            )
            PlanPill(
                title = "Yearly",
                price = "\$59.99",
                badge = "Save 50%",
                selected = selectedPlan == Plan.Yearly,
                onClick = { viewModel.selectPlan(Plan.Yearly) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = { viewModel.startTrial(onTrialStarted) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HotRed),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Start 7-Day Free Trial", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(10.dp))
        Text(
            "Cancel anytime. No charge during trial.",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(22.dp))
    }
}

@Composable
private fun PlanPill(
    title: String,
    price: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: String? = null
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .background(if (selected) HotRed else Color.Transparent)
            .padding(vertical = 12.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(price, color = Color.White.copy(alpha = 0.82f), fontSize = 13.sp)
            if (badge != null) {
                Text(badge, color = Color.White.copy(alpha = 0.86f), fontSize = 11.sp)
            }
        }
    }
}
