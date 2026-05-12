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
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.RaisedMidnight
import com.appylab.bloom.core.ui.SlateBorder
import com.appylab.bloom.core.ui.TextWhite
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
        Spacer(Modifier.height(48.dp))

        Text(
            "Bloom Pro",
            color = TextWhite,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Everything you need to reach your goals.",
            color = MistText,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(.7f))
                        .border(1.dp, SlateBorder.copy(.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(HotRed)
                    )
                    Text(
                        text = feature,
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, SlateBorder.copy(.5f), RoundedCornerShape(16.dp))
                .background(RaisedMidnight.copy(.6f))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            PlanPill(
                title = "Monthly",
                price = "$9.99 / mo",
                selected = selectedPlan == Plan.Monthly,
                onClick = { viewModel.selectPlan(Plan.Monthly) },
                modifier = Modifier.weight(1f)
            )
            PlanPill(
                title = "Yearly",
                price = "$59.99 / yr",
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
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HotRed),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Start 7-Day Free Trial", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(10.dp))
        Text(
            "Cancel anytime. No charge during trial.",
            color = MistText,
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
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .background(if (selected) HotRed else Color.Transparent)
            .padding(vertical = 14.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, color = if (selected) Color.White else TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(price, color = if (selected) Color.White.copy(.85f) else MistText, fontSize = 13.sp)
            if (badge != null) {
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) Color.White.copy(.2f) else SlateBorder.copy(.3f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(badge, color = if (selected) Color.White else MistText, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
