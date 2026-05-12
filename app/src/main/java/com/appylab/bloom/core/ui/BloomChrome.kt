package com.appylab.bloom.core.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appylab.bloom.navigation.AppDestination

@Composable
fun GlassCard(
    modifier: Modifier,
    scale: Float,
    content: @Composable () -> Unit
) {
    val outline = MaterialTheme.colorScheme.outline
    val surface = MaterialTheme.colorScheme.surface
    Box(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(18.dp * scale),
                ambientColor = outline.copy(.25f),
                spotColor = outline.copy(.15f)
            )
            .clip(RoundedCornerShape(18.dp * scale))
            .background(surface)
            .border(0.5.dp, outline.copy(.4f), RoundedCornerShape(18.dp * scale))
    ) {
        content()
    }
}

@Composable
fun CircleIconButton(
    scale: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val outline = MaterialTheme.colorScheme.outline
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    Box(
        modifier = modifier
            .size(43.dp * scale)
            .shadow(2.dp, CircleShape, ambientColor = outline.copy(.25f), spotColor = outline.copy(.15f))
            .clip(CircleShape)
            .background(surfaceVariant)
            .border(0.5.dp, outline.copy(.35f), CircleShape),
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
    val outline = MaterialTheme.colorScheme.outline
    val surface = MaterialTheme.colorScheme.surface
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(61.dp * scale)
            .shadow(4.dp, RoundedCornerShape(18.dp * scale), ambientColor = outline.copy(.25f), spotColor = outline.copy(.15f))
            .clip(RoundedCornerShape(18.dp * scale))
            .background(surface)
            .border(0.5.dp, outline.copy(.4f), RoundedCornerShape(18.dp * scale))
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
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
    val color = if (active) activeColor else inactiveColor
    Column(
        modifier = Modifier
            .width(72.dp * scale)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavIcon(destination, color, 25.dp * scale)
        Text(
            destination.label,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            fontSize = MaterialTheme.typography.labelSmall.fontSize * scale,
            fontWeight = if (active) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
        )
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
    val primary = MaterialTheme.colorScheme.primary
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            NavIcon(icon, primary, 20.dp * scale)
            Spacer(Modifier.width(10.dp * scale))
        }
        Text(
            title,
            color = onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium,
            fontSize = MaterialTheme.typography.titleMedium.fontSize * scale,
            modifier = Modifier.weight(1f)
        )
        if (action != null) {
            Text(
                action,
                color = primary,
                style = MaterialTheme.typography.bodySmall,
                fontSize = MaterialTheme.typography.bodySmall.fontSize * scale
            )
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
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            color = onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontSize = MaterialTheme.typography.titleLarge.fontSize * scale,
            textAlign = TextAlign.Center
        )
        Text(
            label,
            color = onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
            fontSize = MaterialTheme.typography.labelSmall.fontSize * scale,
            textAlign = TextAlign.Center
        )
    }
}
