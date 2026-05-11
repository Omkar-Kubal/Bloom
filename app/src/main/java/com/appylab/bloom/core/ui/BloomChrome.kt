package com.appylab.bloom.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.R
import com.appylab.bloom.navigation.AppDestination

@Composable
fun StatusBar(scale: Float, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("9:30", color = TextWhite, fontSize = 12.sp * scale, fontWeight = FontWeight.Medium)
        Canvas(modifier = Modifier.size(50.dp * scale, 16.dp * scale)) {
            val w = size.width
            val h = size.height
            val wifi = Path().apply {
                moveTo(w * .07f, h * .27f)
                quadraticBezierTo(w * .22f, h * -.05f, w * .37f, h * .27f)
                lineTo(w * .22f, h * .86f)
                close()
            }
            drawPath(wifi, TextWhite)
            drawPath(Path().apply {
                moveTo(w * .48f, h * .78f)
                lineTo(w * .71f, h * .78f)
                lineTo(w * .71f, h * .12f)
                close()
            }, TextWhite)
            drawRoundRect(TextWhite, Offset(w * .82f, h * .12f), Size(w * .14f, h * .72f), CornerRadius(3.dp.toPx()))
            drawRoundRect(TextWhite, Offset(w * .86f, h * .02f), Size(w * .06f, h * .1f), CornerRadius(2.dp.toPx()))
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier,
    scale: Float,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp * scale))
            .background(Brush.linearGradient(listOf(PanelNavy, RaisedMidnight)))
            .border(1.dp, SlateBorder.copy(.78f), RoundedCornerShape(18.dp * scale))
            .drawBehind {
                drawRoundRect(
                    color = Color.Black.copy(.22f),
                    topLeft = Offset(0f, 3.dp.toPx()),
                    size = size,
                    cornerRadius = CornerRadius(18.dp.toPx())
                )
            }
    ) {
        content()
    }
}

@Composable
fun MascotArt(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.mascot),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

@Composable
fun CircleIconButton(
    scale: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(43.dp * scale)
            .clip(CircleShape)
            .background(AbyssNavy)
            .border(1.dp, Color.White.copy(.04f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun AppBottomNav(
    active: AppDestination,
    scale: Float,
    onDestinationSelected: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .height(61.dp * scale),
        scale = scale
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            AppDestination.entries.forEach { destination ->
                BottomNavItem(
                    destination = destination,
                    active = destination == active,
                    scale = scale,
                    onClick = { onDestinationSelected(destination) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    destination: AppDestination,
    active: Boolean,
    scale: Float,
    onClick: () -> Unit
) {
    val color = if (active) HotRed else DimText
    Column(
        modifier = Modifier
            .width(72.dp * scale)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavIcon(destination, color, 25.dp * scale)
        Text(destination.label, color = color, fontSize = 10.sp * scale, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun NavIcon(destination: AppDestination, color: Color, size: Dp) {
    Canvas(Modifier.size(size)) {
        val w = size.toPx()
        val stroke = Stroke(1.9.dp.toPx(), cap = StrokeCap.Round)
        when (destination) {
            AppDestination.Dashboard -> {
                val path = Path().apply {
                    moveTo(w * .16f, w * .48f)
                    lineTo(w * .5f, w * .17f)
                    lineTo(w * .84f, w * .48f)
                    lineTo(w * .84f, w * .88f)
                    lineTo(w * .62f, w * .88f)
                    lineTo(w * .62f, w * .63f)
                    lineTo(w * .38f, w * .63f)
                    lineTo(w * .38f, w * .88f)
                    lineTo(w * .16f, w * .88f)
                    close()
                }
                drawPath(path, color, style = stroke)
            }
            AppDestination.Workout -> {
                drawLine(color, Offset(w * .25f, w * .5f), Offset(w * .75f, w * .5f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
                drawRoundRect(color, Offset(w * .1f, w * .34f), Size(w * .12f, w * .32f), CornerRadius(3.dp.toPx()), style = stroke)
                drawRoundRect(color, Offset(w * .23f, w * .26f), Size(w * .13f, w * .48f), CornerRadius(3.dp.toPx()), style = stroke)
                drawRoundRect(color, Offset(w * .64f, w * .26f), Size(w * .13f, w * .48f), CornerRadius(3.dp.toPx()), style = stroke)
                drawRoundRect(color, Offset(w * .78f, w * .34f), Size(w * .12f, w * .32f), CornerRadius(3.dp.toPx()), style = stroke)
            }
            AppDestination.Food -> {
                drawArc(color, 0f, 180f, false, Offset(w * .18f, w * .35f), Size(w * .64f, w * .63f), style = stroke)
                drawLine(color, Offset(w * .2f, w * .66f), Offset(w * .8f, w * .66f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
                drawLine(color, Offset(w * .54f, w * .55f), Offset(w * .74f, w * .18f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
                drawCircle(color, w * .05f, Offset(w * .76f, w * .16f), style = stroke)
            }
            AppDestination.Run -> {
                drawCircle(color, w * .08f, Offset(w * .64f, w * .14f), style = stroke)
                drawLine(color, Offset(w * .55f, w * .28f), Offset(w * .43f, w * .48f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
                drawLine(color, Offset(w * .43f, w * .48f), Offset(w * .62f, w * .58f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
                drawLine(color, Offset(w * .44f, w * .49f), Offset(w * .28f, w * .74f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
                drawLine(color, Offset(w * .62f, w * .58f), Offset(w * .78f, w * .84f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
                drawLine(color, Offset(w * .52f, w * .35f), Offset(w * .28f, w * .32f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
            }
            AppDestination.Profile -> {
                drawCircle(color, w * .16f, Offset(w * .5f, w * .34f), style = stroke)
                drawArc(color, 205f, 130f, false, Offset(w * .22f, w * .52f), Size(w * .56f, w * .42f), style = stroke)
            }
        }
    }
}

@Composable
fun SectionTitle(
    icon: AppDestination?,
    title: String,
    scale: Float,
    action: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            NavIcon(icon, HotRed, 20.dp * scale)
            androidx.compose.foundation.layout.Spacer(Modifier.width(10.dp * scale))
        }
        Text(title, color = MistText, fontSize = 15.sp * scale, modifier = Modifier.weight(1f))
        if (action != null) {
            Text(action, color = HotRed, fontSize = 12.sp * scale)
        }
    }
}

@Composable
fun MetricText(
    value: String,
    label: String,
    scale: Float,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = TextWhite, fontSize = 18.sp * scale, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        Text(label, color = MistText, fontSize = 10.sp * scale, textAlign = TextAlign.Center)
    }
}
