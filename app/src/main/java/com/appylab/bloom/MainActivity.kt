package com.appylab.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appylab.bloom.ui.theme.BloomTheme

private val AbyssNavy = Color(0xFF171C2F)
private val DeepInk = Color(0xFF001630)
private val PanelNavy = Color(0xFF0E1828)
private val RaisedMidnight = Color(0xFF142338)
private val MutedSteel = Color(0xFF232F49)
private val SlateBorder = Color(0xFF384358)
private val BloomPeach = Color(0xFFFFA586)
private val SoftPeach = Color(0xFFF49E85)
private val ActivityRed = Color(0xFFE51A2B)
private val HotRed = Color(0xFFFF3A3D)
private val WineShadow = Color(0xFF561A22)
private val HeaderBurgundy = Color(0xFF881C2C)
private val TextWhite = Color(0xFFFCFBFB)
private val MistText = Color(0xFFDDE3EE)
private val DimText = Color(0xFF9AA4B6)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BloomTheme(dynamicColor = false, darkTheme = true) {
                Surface(color = DeepInk) {
                    FitnessDashboard()
                }
            }
        }
    }
}

@Composable
private fun FitnessDashboard() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
    ) {
        val scale = (maxWidth / 393.dp).coerceAtMost(maxHeight / 852.dp)
        val hPad = 13.dp * scale
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = hPad)
                .padding(top = 18.dp * scale, bottom = 15.dp * scale),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            StatusBar(scale)
            Header(scale)
            DateStrip(scale)
            PlanCard(scale)
            MetricGrid(scale)
            ReportCard(scale)
            BottomNav(scale)
        }
    }
}

private fun screenBrush() = Brush.verticalGradient(
    colorStops = arrayOf(
        0.00f to Color(0xFF4B0F24),
        0.12f to HeaderBurgundy,
        0.22f to ActivityRed,
        0.35f to BloomPeach,
        0.44f to DeepInk,
        1.00f to AbyssNavy
    )
)

@Composable
private fun StatusBar(scale: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
            drawRoundRect(
                color = TextWhite,
                topLeft = Offset(w * .82f, h * .12f),
                size = Size(w * .14f, h * .72f),
                cornerRadius = CornerRadius(3.dp.toPx())
            )
            drawRoundRect(
                color = TextWhite,
                topLeft = Offset(w * .86f, h * .02f),
                size = Size(w * .06f, h * .1f),
                cornerRadius = CornerRadius(2.dp.toPx())
            )
        }
    }
}

@Composable
private fun Header(scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp * scale),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarButton(scale)
        Spacer(Modifier.width(16.dp * scale))
        Column(modifier = Modifier.weight(1f)) {
            Text("Good Morning,", color = TextWhite, fontSize = 16.sp * scale)
            Text("Ready to rock?", color = BloomPeach, fontSize = 19.sp * scale, fontWeight = FontWeight.Bold)
        }
        BellButton(scale)
    }
}

@Composable
private fun AvatarButton(scale: Float) {
    Box(
        modifier = Modifier
            .size(50.dp * scale)
            .clip(CircleShape)
            .background(AbyssNavy)
            .border(1.dp, Color.White.copy(.04f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(35.dp * scale)) {
            drawCircle(SoftPeach, radius = size.minDimension * .22f, center = Offset(size.width / 2, size.height * .34f))
            drawOval(
                color = Color(0xFFFF786B),
                topLeft = Offset(size.width * .18f, size.height * .58f),
                size = Size(size.width * .64f, size.height * .3f)
            )
        }
    }
}

@Composable
private fun BellButton(scale: Float) {
    Box(
        modifier = Modifier
            .size(43.dp * scale)
            .clip(CircleShape)
            .background(AbyssNavy)
            .border(1.dp, Color.White.copy(.04f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(26.dp * scale)) {
            val stroke = Stroke(2.2.dp.toPx(), cap = StrokeCap.Round)
            drawArc(
                color = TextWhite,
                startAngle = 194f,
                sweepAngle = 152f,
                useCenter = false,
                topLeft = Offset(size.width * .2f, size.height * .08f),
                size = Size(size.width * .6f, size.height * .8f),
                style = stroke
            )
            drawLine(TextWhite, Offset(size.width * .22f, size.height * .74f), Offset(size.width * .78f, size.height * .74f), strokeWidth = 2.2.dp.toPx(), cap = StrokeCap.Round)
            drawLine(TextWhite, Offset(size.width * .38f, size.height * .88f), Offset(size.width * .62f, size.height * .88f), strokeWidth = 2.2.dp.toPx(), cap = StrokeCap.Round)
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 3.dp * scale, y = 1.dp * scale)
                .size(9.dp * scale)
                .clip(CircleShape)
                .background(HotRed)
        )
    }
}

@Composable
private fun DateStrip(scale: Float) {
    val days = listOf("17" to "SAT", "18" to "SUN", "19" to "MON", "20" to "TUE", "21" to "WED", "22" to "THU", "23" to "FRI")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp * scale),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEach { (num, day) ->
            DateChip(num, day, selected = num == "20", scale)
        }
    }
}

@Composable
private fun DateChip(num: String, day: String, selected: Boolean, scale: Float) {
    val bg = if (selected) Brush.verticalGradient(listOf(Color(0xFFFFC0A7), BloomPeach)) else Brush.verticalGradient(listOf(AbyssNavy, Color(0xFF0C1D33)))
    Column(
        modifier = Modifier
            .width(38.dp * scale)
            .height(51.dp * scale)
            .clip(RoundedCornerShape(23.dp * scale))
            .background(bg)
            .border(1.dp, if (selected) Color.White.copy(.12f) else Color.White.copy(.04f), RoundedCornerShape(23.dp * scale))
            .padding(top = 8.dp * scale),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(25.dp * scale)
                .clip(CircleShape)
                .border(if (selected) 0.dp else 1.dp, BloomPeach, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(num, color = if (selected) Color.Black else TextWhite, fontSize = 13.sp * scale, fontWeight = FontWeight.Bold)
        }
        Text(day, color = if (selected) Color.Black else TextWhite, fontSize = 7.5.sp * scale, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun GlassCard(
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
private fun PlanCard(scale: Float) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(164.dp * scale),
        scale = scale
    ) {
        Row(Modifier.fillMaxSize().padding(20.dp * scale), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(.58f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CalendarIcon(20.dp * scale)
                    Spacer(Modifier.width(10.dp * scale))
                    Text("Today's Plan", color = MistText, fontSize = 15.sp * scale)
                }
                Spacer(Modifier.height(16.dp * scale))
                Text("Your Workout\nRoutine", color = TextWhite, fontSize = 26.sp * scale, lineHeight = 31.sp * scale, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(15.dp * scale))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ClockIcon(16.dp * scale)
                    Spacer(Modifier.width(10.dp * scale))
                    Text("01:58 min", color = TextWhite, fontSize = 16.sp * scale, fontWeight = FontWeight.Medium)
                }
            }
            Mascot(Modifier.weight(.42f).fillMaxHeight())
        }
    }
}

@Composable
private fun MetricGrid(scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(209.dp * scale),
        horizontalArrangement = Arrangement.spacedBy(10.dp * scale)
    ) {
        CalorieCard(Modifier.weight(1f), scale)
        StepCard(Modifier.weight(1f), scale)
    }
}

@Composable
private fun CalorieCard(modifier: Modifier, scale: Float) {
    GlassCard(modifier = modifier.fillMaxHeight(), scale = scale) {
        Column(Modifier.fillMaxSize().padding(19.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FlameIcon(21.dp * scale)
                Spacer(Modifier.width(9.dp * scale))
                Text("Calorie Card", color = MistText, fontSize = 15.sp * scale)
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Ring(progress = .87f, color = HotRed, scale = scale, modifier = Modifier.size(132.dp * scale))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("1,690", color = TextWhite, fontSize = 22.sp * scale, fontWeight = FontWeight.ExtraBold)
                    Text("kcal burned", color = TextWhite, fontSize = 12.5.sp * scale)
                    Spacer(Modifier.height(10.dp * scale))
                    Row(verticalAlignment = Alignment.Top) {
                        CrownIcon(13.dp * scale)
                        Spacer(Modifier.width(4.dp * scale))
                        Text("1,820 kcal\nyour target", color = MistText, fontSize = 11.5.sp * scale, lineHeight = 15.sp * scale)
                    }
                }
            }
        }
    }
}

@Composable
private fun StepCard(modifier: Modifier, scale: Float) {
    GlassCard(modifier = modifier.fillMaxHeight(), scale = scale) {
        Column(Modifier.fillMaxSize().padding(19.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ShoeIcon(22.dp * scale)
                Spacer(Modifier.width(9.dp * scale))
                Text("Step Card", color = MistText, fontSize = 15.sp * scale)
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ArcRing(scale = scale, modifier = Modifier.size(134.dp * scale))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.offset(y = 8.dp * scale)) {
                    FootprintsIcon(23.dp * scale)
                    Text("7,528", color = TextWhite, fontSize = 21.sp * scale, fontWeight = FontWeight.ExtraBold)
                    Text("steps", color = TextWhite, fontSize = 12.5.sp * scale)
                    Spacer(Modifier.height(9.dp * scale))
                    Text("Distance\n4.06 km\n250 kcal", color = TextWhite, fontSize = 12.5.sp * scale, lineHeight = 16.sp * scale, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun ReportCard(scale: Float) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(181.dp * scale),
        scale = scale
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = 19.dp * scale, vertical = 14.dp * scale)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TrendIcon(23.dp * scale)
                Spacer(Modifier.width(10.dp * scale))
                Text("Today's Reports", color = MistText, fontSize = 15.sp * scale)
            }
            Chart(Modifier.fillMaxWidth().height(97.dp * scale).padding(top = 6.dp * scale), scale)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                WeightStat("Start", "85 kg", TextWhite, scale)
                DividerStat(scale)
                WeightStat("Current", "76 kg", HotRed, scale)
                DividerStat(scale)
                WeightStat("Target", "70 kg", BloomPeach, scale)
            }
        }
    }
}

@Composable
private fun Chart(modifier: Modifier, scale: Float) {
    Canvas(modifier = modifier) {
        val left = 49.dp.toPx()
        val right = size.width - 50.dp.toPx()
        val top = 6.dp.toPx()
        val bottom = size.height - 18.dp.toPx()
        val values = listOf(28f, 61f, 28f, 100f, 75f, 75f, 48f, 95f, 56f, 69f, 28f, 70f, 75f)
        fun y(v: Float) = bottom - (v / 100f) * (bottom - top)
        listOf(0f, .25f, .5f, .75f, 1f).forEach {
            val yy = bottom - it * (bottom - top)
            drawLine(SlateBorder.copy(.42f), Offset(left, yy), Offset(right, yy), strokeWidth = .7.dp.toPx())
        }
        drawLine(MistText.copy(.72f), Offset(left, y(75f)), Offset(right, y(75f)), strokeWidth = .8.dp.toPx(), pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(9.dp.toPx(), 7.dp.toPx())))
        val step = (right - left) / (values.size - 1)
        val points = values.mapIndexed { i, v -> Offset(left + i * step, y(v)) }
        for (i in 0 until points.lastIndex) {
            drawLine(HotRed, points[i], points[i + 1], strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
        }
        points.forEach {
            drawCircle(HotRed, 4.5.dp.toPx(), it)
            drawCircle(SoftPeach, 2.1.dp.toPx(), it)
        }
        drawCircle(SlateBorder, 4.8.dp.toPx(), points.last())
        drawCircle(BloomPeach, 3.2.dp.toPx(), points.last())
        val labels = listOf("100", "75", "50", "25", "0")
        labels.forEachIndexed { i, label ->
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    alpha = 230
                    textSize = 9.sp.toPx()
                    isAntiAlias = true
                }
                drawText(label, 0f, top + i * ((bottom - top) / 4f) + 4.dp.toPx(), paint)
            }
        }
        drawContext.canvas.nativeCanvas.apply {
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.rgb(221, 227, 238)
                textSize = 10.sp.toPx()
                isAntiAlias = true
            }
            drawText("Target", right + 7.dp.toPx(), y(75f) - 8.dp.toPx(), paint)
            drawText("76 kg", right + 7.dp.toPx(), y(75f) + 8.dp.toPx(), paint)
        }
    }
}

@Composable
private fun WeightStat(label: String, value: String, color: Color, scale: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = MistText, fontSize = 11.sp * scale)
        Text(value, color = color, fontSize = 13.sp * scale, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun DividerStat(scale: Float) {
    Box(
        modifier = Modifier
            .height(22.dp * scale)
            .width(1.dp)
            .background(SlateBorder)
    )
}

@Composable
private fun BottomNav(scale: Float) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp * scale),
        scale = scale
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            NavItem("Dashboard", true, scale) { HomeNavIcon(it, true) }
            NavItem("Workout", false, scale) { DumbbellNavIcon(it, false) }
            NavItem("Food", false, scale) { BowlNavIcon(it, false) }
            NavItem("Run", false, scale) { RunNavIcon(it, false) }
        }
    }
}

@Composable
private fun NavItem(label: String, active: Boolean, scale: Float, icon: @Composable (Dp) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        icon(25.dp * scale)
        Text(label, color = if (active) HotRed else DimText, fontSize = 10.sp * scale, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
private fun Ring(progress: Float, color: Color, scale: Float, modifier: Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 9.dp.toPx()
        val rect = Rect(stroke / 2, stroke / 2, size.width - stroke / 2, size.height - stroke / 2)
        drawArc(WineShadow.copy(.68f), -90f, 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(color, -88f, progress * 360f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
    }
}

@Composable
private fun ArcRing(scale: Float, modifier: Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 9.dp.toPx()
        val rect = Rect(stroke / 2, stroke / 2, size.width - stroke / 2, size.height - stroke / 2)
        drawArc(MutedSteel.copy(.9f), 210f, 120f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(BloomPeach, 135f, 270f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Round))
        drawArc(WineShadow.copy(.62f), 225f, 22f, false, rect.topLeft, rect.size, style = Stroke(stroke, cap = StrokeCap.Butt))
    }
}

@Composable
private fun Mascot(modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth(.86f)
                .height(18.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-12).dp)
        ) {
            drawOval(Color.Black.copy(.32f), size = size)
        }
        Image(
            painter = painterResource(R.drawable.mascot),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun CalendarIcon(size: Dp) = Canvas(Modifier.size(size)) {
    val stroke = Stroke(1.8.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(HotRed, Offset(size.toPx() * .12f, size.toPx() * .2f), Size(size.toPx() * .76f, size.toPx() * .68f), CornerRadius(2.dp.toPx()), style = stroke)
    drawLine(HotRed, Offset(size.toPx() * .12f, size.toPx() * .4f), Offset(size.toPx() * .88f, size.toPx() * .4f), strokeWidth = 1.8.dp.toPx())
    drawLine(HotRed, Offset(size.toPx() * .3f, 0f), Offset(size.toPx() * .3f, size.toPx() * .28f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(HotRed, Offset(size.toPx() * .7f, 0f), Offset(size.toPx() * .7f, size.toPx() * .28f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
}

@Composable
private fun ClockIcon(size: Dp) = Canvas(Modifier.size(size)) {
    drawCircle(HotRed, size.toPx() * .42f, Offset(size.toPx() / 2, size.toPx() / 2), style = Stroke(2.dp.toPx()))
    drawLine(HotRed, Offset(size.toPx() / 2, size.toPx() * .24f), Offset(size.toPx() / 2, size.toPx() * .52f), strokeWidth = 1.6.dp.toPx(), cap = StrokeCap.Round)
    drawLine(HotRed, Offset(size.toPx() / 2, size.toPx() / 2), Offset(size.toPx() * .68f, size.toPx() * .62f), strokeWidth = 1.6.dp.toPx(), cap = StrokeCap.Round)
}

@Composable
private fun FlameIcon(size: Dp) = Canvas(Modifier.size(size)) {
    val p = Path().apply {
        moveTo(size.toPx() * .52f, size.toPx() * .05f)
        cubicTo(size.toPx() * .15f, size.toPx() * .34f, size.toPx() * .75f, size.toPx() * .45f, size.toPx() * .36f, size.toPx() * .82f)
        cubicTo(size.toPx() * .17f, size.toPx() * .66f, size.toPx() * .05f, size.toPx() * .45f, size.toPx() * .22f, size.toPx() * .22f)
        cubicTo(size.toPx() * .02f, size.toPx() * .38f, size.toPx() * .04f, size.toPx() * .9f, size.toPx() * .5f, size.toPx() * .92f)
        cubicTo(size.toPx() * .95f, size.toPx() * .89f, size.toPx() * .98f, size.toPx() * .43f, size.toPx() * .68f, size.toPx() * .2f)
        cubicTo(size.toPx() * .66f, size.toPx() * .32f, size.toPx() * .57f, size.toPx() * .32f, size.toPx() * .52f, size.toPx() * .05f)
    }
    drawPath(p, HotRed, style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
}

@Composable
private fun ShoeIcon(size: Dp) = Canvas(Modifier.size(size)) {
    rotate(-18f) {
        drawRoundRect(HotRed, Offset(size.toPx() * .12f, size.toPx() * .43f), Size(size.toPx() * .72f, size.toPx() * .27f), CornerRadius(4.dp.toPx()), style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
        drawLine(HotRed, Offset(size.toPx() * .36f, size.toPx() * .42f), Offset(size.toPx() * .47f, size.toPx() * .17f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
        drawLine(HotRed, Offset(size.toPx() * .53f, size.toPx() * .43f), Offset(size.toPx() * .64f, size.toPx() * .18f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
    }
}

@Composable
private fun TrendIcon(size: Dp) = Canvas(Modifier.size(size)) {
    val points = listOf(.1f to .75f, .28f to .47f, .43f to .62f, .6f to .22f, .8f to .35f, .93f to .1f)
    for (i in 0 until points.lastIndex) {
        drawLine(HotRed, Offset(size.toPx() * points[i].first, size.toPx() * points[i].second), Offset(size.toPx() * points[i + 1].first, size.toPx() * points[i + 1].second), strokeWidth = 1.7.dp.toPx(), cap = StrokeCap.Round)
    }
    points.forEach { drawCircle(HotRed, 2.2.dp.toPx(), Offset(size.toPx() * it.first, size.toPx() * it.second), style = Stroke(1.5.dp.toPx())) }
}

@Composable
private fun CrownIcon(size: Dp) = Canvas(Modifier.size(size)) {
    val w = size.toPx()
    val p = Path().apply {
        moveTo(w * .08f, w * .75f)
        lineTo(w * .2f, w * .32f)
        lineTo(w * .43f, w * .62f)
        lineTo(w * .56f, w * .2f)
        lineTo(w * .78f, w * .62f)
        lineTo(w * .93f, w * .34f)
        lineTo(w * .88f, w * .75f)
        close()
    }
    drawPath(p, BloomPeach)
}

@Composable
private fun FootprintsIcon(size: Dp) = Canvas(Modifier.size(size)) {
    val w = size.toPx()
    val stroke = Stroke(1.6.dp.toPx(), cap = StrokeCap.Round)
    drawOval(BloomPeach, Offset(w * .18f, w * .18f), Size(w * .22f, w * .42f), style = stroke)
    drawOval(BloomPeach, Offset(w * .58f, w * .1f), Size(w * .22f, w * .42f), style = stroke)
    drawCircle(BloomPeach, w * .045f, Offset(w * .18f, w * .67f), style = stroke)
    drawCircle(BloomPeach, w * .045f, Offset(w * .31f, w * .72f), style = stroke)
    drawCircle(BloomPeach, w * .045f, Offset(w * .63f, w * .61f), style = stroke)
    drawCircle(BloomPeach, w * .045f, Offset(w * .78f, w * .58f), style = stroke)
}

@Composable
private fun HomeNavIcon(size: Dp, active: Boolean) = Canvas(Modifier.size(size)) {
    val c = if (active) HotRed else DimText
    val w = size.toPx()
    val stroke = Stroke(1.9.dp.toPx(), cap = StrokeCap.Round)
    val p = Path().apply {
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
    drawPath(p, c, style = stroke)
}

@Composable
private fun DumbbellNavIcon(size: Dp, active: Boolean) = Canvas(Modifier.size(size)) {
    val c = if (active) HotRed else DimText
    val w = size.toPx()
    val stroke = Stroke(2.dp.toPx(), cap = StrokeCap.Round)
    drawLine(c, Offset(w * .25f, w * .5f), Offset(w * .75f, w * .5f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(c, Offset(w * .1f, w * .34f), Size(w * .12f, w * .32f), CornerRadius(3.dp.toPx()), style = stroke)
    drawRoundRect(c, Offset(w * .23f, w * .26f), Size(w * .13f, w * .48f), CornerRadius(3.dp.toPx()), style = stroke)
    drawRoundRect(c, Offset(w * .64f, w * .26f), Size(w * .13f, w * .48f), CornerRadius(3.dp.toPx()), style = stroke)
    drawRoundRect(c, Offset(w * .78f, w * .34f), Size(w * .12f, w * .32f), CornerRadius(3.dp.toPx()), style = stroke)
}

@Composable
private fun BowlNavIcon(size: Dp, active: Boolean) = Canvas(Modifier.size(size)) {
    val c = if (active) HotRed else DimText
    val w = size.toPx()
    val stroke = Stroke(1.9.dp.toPx(), cap = StrokeCap.Round)
    drawArc(c, 0f, 180f, false, Offset(w * .18f, w * .35f), Size(w * .64f, w * .63f), style = stroke)
    drawLine(c, Offset(w * .2f, w * .66f), Offset(w * .8f, w * .66f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
    drawLine(c, Offset(w * .54f, w * .55f), Offset(w * .74f, w * .18f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(c, w * .05f, Offset(w * .76f, w * .16f), style = stroke)
}

@Composable
private fun RunNavIcon(size: Dp, active: Boolean) = Canvas(Modifier.size(size)) {
    val c = if (active) HotRed else DimText
    val w = size.toPx()
    val stroke = Stroke(1.9.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(c, w * .08f, Offset(w * .64f, w * .14f), style = stroke)
    drawLine(c, Offset(w * .55f, w * .28f), Offset(w * .43f, w * .48f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
    drawLine(c, Offset(w * .43f, w * .48f), Offset(w * .62f, w * .58f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
    drawLine(c, Offset(w * .44f, w * .49f), Offset(w * .28f, w * .74f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
    drawLine(c, Offset(w * .62f, w * .58f), Offset(w * .78f, w * .84f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
    drawLine(c, Offset(w * .52f, w * .35f), Offset(w * .28f, w * .32f), strokeWidth = 1.9.dp.toPx(), cap = StrokeCap.Round)
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun DashboardPreview() {
    BloomTheme(dynamicColor = false, darkTheme = true) {
        FitnessDashboard()
    }
}

