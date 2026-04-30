package app.fitmess.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Flag
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.fitmess.ui.theme.BloomCoralFlame
import app.fitmess.ui.theme.DeepWineGlow
import app.fitmess.ui.theme.FitMessTheme
import app.fitmess.ui.theme.FreshCoralHighlight
import app.fitmess.ui.theme.MidnightPlumCanvas
import app.fitmess.ui.theme.MutedRoseGray
import app.fitmess.ui.theme.MutedWarmGray
import app.fitmess.ui.theme.PeachHairlineBorder
import app.fitmess.ui.theme.PearlCardSurface
import app.fitmess.ui.theme.PressedEmberRed
import app.fitmess.ui.theme.PrimaryInk
import app.fitmess.ui.theme.RosewoodBorder
import app.fitmess.ui.theme.SmokedCardSurface
import app.fitmess.ui.theme.SoftAshText
import app.fitmess.ui.theme.SoftCharcoal
import app.fitmess.ui.theme.SoftPetalCoral
import app.fitmess.ui.theme.WarmMilkCanvas

@Composable
fun BloomDashboardScreen(modifier: Modifier = Modifier) {
    val darkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val colors = if (darkTheme) DashboardColors.dark() else DashboardColors.light()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .dashboardBackdrop(colors, darkTheme)
            .statusBarsPadding()
    ) {
        val horizontalPadding = if (maxWidth < 370.dp) 18.dp else 22.dp
        val compact = maxWidth < 370.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = horizontalPadding)
                .padding(bottom = 116.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            DashboardHeader(colors = colors, compact = compact)
            MetricCards(colors = colors)
            PromoCard(
                title = "Track workout",
                body = "Log your workouts, stay consistent,\nand see your progress grow.",
                action = "Start tracking",
                colors = colors,
                artwork = { DumbbellArt(colors = colors, modifier = Modifier.size(150.dp)) }
            )
            PromoCard(
                title = "Weekly Habits",
                body = "Congratulations on your week of habits!\nLet's keep your momentum rolling.",
                action = "Quick check in",
                colors = colors,
                artwork = { ConfettiArt(colors = colors, modifier = Modifier.size(168.dp)) }
            )
            ConsistencyCard(colors = colors)
            BottomStats(colors = colors)
        }

        DashboardBottomBar(
            colors = colors,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = horizontalPadding)
                .navigationBarsPadding()
                .padding(bottom = 10.dp)
        )
    }
}

@Composable
private fun DashboardHeader(colors: DashboardColors, compact: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BloomBadge(colors = colors, modifier = Modifier.size(if (compact) 52.dp else 60.dp))
            Spacer(Modifier.width(14.dp))
            Text(
                text = "Bloom",
                color = colors.wordmarkFallback,
                fontSize = if (compact) 32.sp else 39.sp,
                lineHeight = if (compact) 38.sp else 44.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.sp,
                style = TextStyle(brush = Brush.verticalGradient(colors.wordmarkGradient))
            )
            Spacer(Modifier.weight(1f))
            NotificationButton(colors = colors)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Good morning! 🌸",
                    color = colors.primaryText,
                    fontSize = if (compact) 25.sp else 29.sp,
                    lineHeight = if (compact) 31.sp else 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Let's keep growing today.",
                    color = colors.secondaryText,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Today, 27 Apr 2026",
                    color = colors.secondaryText,
                    fontSize = if (compact) 14.sp else 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
                EditPill(colors = colors)
            }
        }
    }
}

@Composable
private fun NotificationButton(colors: DashboardColors) {
    Box(contentAlignment = Alignment.TopEnd) {
        Icon(
            imageVector = Icons.Rounded.NotificationsNone,
            contentDescription = "Notifications",
            tint = colors.primaryText,
            modifier = Modifier
                .size(44.dp)
                .padding(5.dp)
        )
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(BloomCoralFlame)
        )
    }
}

@Composable
private fun EditPill(colors: DashboardColors) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(colors.pillBackground)
            .border(1.dp, colors.border, RoundedCornerShape(18.dp))
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Edit",
            color = colors.accent,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun MetricCards(colors: DashboardColors) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DashboardCard(
            colors = colors,
            modifier = Modifier
                .weight(1f)
                .height(226.dp)
        ) {
            MoveCardContent(colors = colors)
        }
        DashboardCard(
            colors = colors,
            modifier = Modifier
                .weight(1f)
                .height(226.dp)
        ) {
            CaloriesCardContent(colors = colors)
        }
    }
}

@Composable
private fun ColumnScope.MoveCardContent(colors: DashboardColors) {
    Row(verticalAlignment = Alignment.Top) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Move",
                color = colors.primaryText,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp
            )
            Text(
                text = "51/200",
                color = colors.accent,
                fontSize = 25.sp,
                lineHeight = 31.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            )
            Text(
                text = "KCAL",
                color = colors.secondaryText,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp
            )
        }
        CircleIconButton(
            icon = Icons.Rounded.Add,
            colors = colors,
            modifier = Modifier.size(46.dp)
        )
    }
    Spacer(Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp),
        contentAlignment = Alignment.Center
    ) {
        ActivityRing(colors = colors, modifier = Modifier.size(112.dp))
    }
    MoveTimeline(colors = colors, modifier = Modifier.fillMaxWidth().height(38.dp))
    Text(
        text = "TOTAL 1,272 KCAL  >",
        color = colors.accent,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp
    )
}

@Composable
private fun ColumnScope.CaloriesCardContent(colors: DashboardColors) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = "Calories",
            color = colors.primaryText,
            fontSize = 22.sp,
            lineHeight = 27.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Remaining =\nGoal - Food + Exercise",
            color = colors.secondaryText,
            fontSize = 11.sp,
            lineHeight = 17.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.End,
            letterSpacing = 0.sp
        )
    }
    Spacer(Modifier.height(12.dp))
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CaloriesRing(colors = colors, modifier = Modifier.size(118.dp))
    }
    Spacer(Modifier.height(8.dp))
    CalorieRow(icon = Icons.Rounded.Flag, label = "Base Goal", value = "2,000", tint = colors.flag, colors = colors)
    CalorieRow(icon = Icons.Rounded.RestaurantMenu, label = "Food", value = "0", tint = colors.food, colors = colors)
    CalorieRow(icon = Icons.Rounded.LocalFireDepartment, label = "Exercise", value = "15", tint = colors.accent, colors = colors)
}

@Composable
private fun CalorieRow(
    icon: ImageVector,
    label: String,
    value: String,
    tint: Color,
    colors: DashboardColors
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = label,
            color = colors.primaryText,
            fontSize = 15.sp,
            lineHeight = 19.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = colors.primaryText,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun PromoCard(
    title: String,
    body: String,
    action: String,
    colors: DashboardColors,
    artwork: @Composable BoxScope.() -> Unit
) {
    DashboardCard(
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp),
        contentPadding = 14.dp
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight()
                    .fillMaxWidth(0.58f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        color = colors.primaryText,
                        fontSize = 22.sp,
                        lineHeight = 27.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.sp
                    )
                    Spacer(Modifier.width(10.dp))
                    BetaBadge(colors = colors)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = body,
                    color = colors.secondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = action,
                        color = colors.accent,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    )
                    Spacer(Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = null,
                        tint = colors.accent,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Box(
                modifier = Modifier.align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center,
                content = artwork
            )
        }
    }
}

@Composable
private fun BetaBadge(colors: DashboardColors) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(colors.badgeBackground)
            .border(1.dp, colors.border, RoundedCornerShape(15.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "BETA",
            color = colors.accent,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun ConsistencyCard(colors: DashboardColors) {
    DashboardCard(
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .height(172.dp)
    ) {
        Row(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Consistency",
                        color = colors.primaryText,
                        fontSize = 22.sp,
                        lineHeight = 27.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "18",
                        color = colors.accent,
                        fontSize = 21.sp,
                        lineHeight = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.sp
                    )
                    Text(
                        text = "/30 days",
                        color = colors.primaryText,
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp
                    )
                }
                Text(
                    text = "Your daily activity this month",
                    color = colors.secondaryText,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp
                )
                Heatmap(colors = colors, modifier = Modifier.fillMaxWidth().height(98.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.width(126.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StreakBox(
                    icon = Icons.Rounded.LocalFireDepartment,
                    title = "Current Streak",
                    value = "6 days",
                    iconTint = colors.accent,
                    colors = colors,
                    modifier = Modifier.weight(1f)
                )
                StreakBox(
                    icon = Icons.Rounded.EmojiEvents,
                    title = "Longest Streak",
                    value = "12 days",
                    iconTint = colors.gold,
                    colors = colors,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StreakBox(
    icon: ImageVector,
    title: String,
    value: String,
    iconTint: Color,
    colors: DashboardColors,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.innerSurface)
            .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(30.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                color = colors.secondaryText,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp
            )
            Text(
                text = value,
                color = colors.primaryText,
                fontSize = 18.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            )
        }
    }
}

@Composable
private fun BottomStats(colors: DashboardColors) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SmallStatCard(
            title = "Recovery Score",
            value = "78",
            status = "Good",
            body = "Your body is recovering well.",
            icon = Icons.Rounded.FavoriteBorder,
            iconTint = colors.recovery,
            colors = colors,
            modifier = Modifier.weight(1f)
        )
        SmallStatCard(
            title = "Workout Streak",
            value = "6 days",
            status = "",
            body = "Keep it going!",
            icon = Icons.Rounded.LocalFireDepartment,
            iconTint = colors.gold,
            colors = colors,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SmallStatCard(
    title: String,
    value: String,
    status: String,
    body: String,
    icon: ImageVector,
    iconTint: Color,
    colors: DashboardColors,
    modifier: Modifier = Modifier
) {
    DashboardCard(
        colors = colors,
        modifier = modifier.height(136.dp),
        contentPadding = 13.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = title,
                color = colors.primaryText,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                modifier = Modifier.weight(1f)
            )
            CircleIconButton(
                icon = Icons.Rounded.ChevronRight,
                colors = colors,
                modifier = Modifier.size(32.dp),
                iconSize = 22.dp
            )
        }
        Spacer(Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                color = colors.primaryText,
                fontSize = 30.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            )
            if (status.isNotEmpty()) {
                Spacer(Modifier.width(8.dp))
                Text(
                    text = status,
                    color = colors.recovery,
                    fontSize = 17.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        if (status.isNotEmpty()) {
            ProgressBar(colors = colors, tint = colors.recovery, modifier = Modifier.fillMaxWidth().height(7.dp))
            Spacer(Modifier.height(8.dp))
        } else {
            FireRow(colors = colors)
            Spacer(Modifier.height(4.dp))
        }
        Text(
            text = body,
            color = colors.secondaryText,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun DashboardBottomBar(colors: DashboardColors, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(78.dp)
            .shadow(
                elevation = 18.dp,
                shape = RoundedCornerShape(42.dp),
                ambientColor = colors.shadow,
                spotColor = colors.shadow
            )
            .clip(RoundedCornerShape(42.dp))
            .background(colors.navSurface)
            .border(1.dp, colors.border, RoundedCornerShape(42.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavItem(icon = Icons.Rounded.Dashboard, label = "Dashboard", active = true, colors = colors)
        NavItem(icon = Icons.Rounded.Restaurant, label = "Food", active = false, colors = colors)
        NavItem(icon = Icons.Rounded.FitnessCenter, label = "Workout", active = false, colors = colors)
        NavItem(icon = Icons.AutoMirrored.Rounded.DirectionsRun, label = "Go Run", active = false, colors = colors)
        Box(
            modifier = Modifier
                .size(58.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = BloomCoralFlame.copy(alpha = 0.32f),
                    spotColor = BloomCoralFlame.copy(alpha = 0.32f)
                )
                .clip(CircleShape)
                .background(Brush.verticalGradient(listOf(FreshCoralHighlight, BloomCoralFlame)))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    active: Boolean,
    colors: DashboardColors
) {
    Column(
        modifier = Modifier
            .width(if (active) 86.dp else 58.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(34.dp))
            .background(if (active) colors.navActiveBackground else Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (active) colors.accent else colors.primaryText,
            modifier = Modifier.size(25.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            color = if (active) colors.accent else colors.primaryText,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun DashboardCard(
    colors: DashboardColors,
    modifier: Modifier = Modifier,
    contentPadding: Dp = 14.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = colors.shadow,
                spotColor = colors.shadow
            )
            .clip(RoundedCornerShape(18.dp))
            .background(colors.card)
            .border(1.dp, colors.border, RoundedCornerShape(18.dp))
            .padding(contentPadding),
        content = content
    )
}

@Composable
private fun CircleIconButton(
    icon: ImageVector,
    colors: DashboardColors,
    modifier: Modifier = Modifier,
    iconSize: Dp = 28.dp
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(colors.innerSurface)
            .border(1.dp, colors.border, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.accent,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
private fun BloomBadge(colors: DashboardColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = colors.border,
            radius = size.minDimension * 0.49f,
            center = center,
            style = Stroke(width = 1.1.dp.toPx())
        )
        drawBloomLogo(colors)
    }
}

@Composable
private fun ActivityRing(colors: DashboardColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.24f
        val inset = strokeWidth / 2f
        val ringRect = Rect(inset, inset, size.width - inset, size.height - inset)
        drawArc(
            color = colors.track,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = ringRect.topLeft,
            size = ringRect.size,
            style = Stroke(strokeWidth, cap = StrokeCap.Round)
        )
        drawArc(
            brush = Brush.sweepGradient(
                listOf(
                    colors.accent.copy(alpha = 0.28f),
                    colors.accent,
                    FreshCoralHighlight,
                    colors.accent.copy(alpha = 0.28f)
                ),
                center = center
            ),
            startAngle = -84f,
            sweepAngle = 156f,
            useCenter = false,
            topLeft = ringRect.topLeft,
            size = ringRect.size,
            style = Stroke(strokeWidth, cap = StrokeCap.Round)
        )
        drawIntoCenterArrow(colors)
    }
}

@Composable
private fun CaloriesRing(colors: DashboardColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.14f
        val inset = strokeWidth / 2f
        drawArc(
            color = colors.track,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(inset, inset),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            style = Stroke(strokeWidth, cap = StrokeCap.Round)
        )
        drawLine(
            color = colors.accent,
            start = Offset(center.x, inset),
            end = Offset(center.x, inset + 12.dp.toPx()),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = colors.track.copy(alpha = 0.82f),
            start = Offset(center.x, inset + 16.dp.toPx()),
            end = Offset(center.x, inset + 36.dp.toPx()),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "2,015",
            color = colors.primaryText,
            fontSize = 29.sp,
            lineHeight = 33.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.sp
        )
        Text(
            text = "Remaining",
            color = colors.secondaryText,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun MoveTimeline(colors: DashboardColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val baseY = size.height * 0.58f
        val dotCount = 42
        repeat(dotCount) { index ->
            val x = index * size.width / (dotCount - 1)
            drawCircle(
                color = colors.accent,
                radius = 1.35.dp.toPx(),
                center = Offset(x, baseY)
            )
        }
        val bars = listOf(11 to 9f, 12 to 18f, 13 to 13f, 14 to 7f, 21 to 30f, 31 to 18f, 32 to 12f)
        bars.forEach { (index, height) ->
            val x = index * size.width / (dotCount - 1)
            drawLine(
                color = colors.accent,
                start = Offset(x, baseY),
                end = Offset(x, baseY - height.dp.toPx()),
                strokeWidth = 2.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun Heatmap(colors: DashboardColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val labelWidth = 20.dp.toPx()
        val top = 3.dp.toPx()
        val cell = 9.5.dp.toPx()
        val gap = 4.5.dp.toPx()
        val labels = listOf("M", "T", "W", "T", "F", "S", "S")
        labels.forEachIndexed { row, label ->
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = colors.primaryText.toArgbCompat()
                    textSize = 12.sp.toPx()
                    isAntiAlias = true
                }
                drawText(label, 0f, top + row * (cell + gap) + cell, paint)
            }
        }
        val intensities = listOf(
            1, 3, 1, 1, 4, 1, 2, 3, 4, 2, 3, 2, 1, 1, 0, 1, 1, 1,
            2, 4, 3, 3, 2, 4, 3, 4, 2, 3, 3, 1, 1, 0, 1, 0, 1, 1,
            3, 4, 4, 2, 4, 1, 3, 3, 4, 2, 3, 3, 2, 1, 0, 1, 0, 0,
            2, 3, 2, 1, 4, 3, 2, 3, 4, 3, 1, 1, 0, 1, 0, 0, 0, 0,
            1, 2, 4, 3, 4, 1, 3, 2, 4, 3, 0, 1, 1, 0, 0, 0, 0, 0,
            2, 1, 3, 4, 3, 0, 2, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        )
        repeat(7) { row ->
            repeat(18) { column ->
                val value = intensities[row * 18 + column]
                val color = colors.heatLevels[value]
                val x = labelWidth + column * (cell + gap)
                val y = top + row * (cell + gap)
                drawRoundRect(
                    color = color,
                    topLeft = Offset(x, y),
                    size = Size(cell, cell),
                    cornerRadius = CornerRadius(2.8.dp.toPx(), 2.8.dp.toPx())
                )
            }
        }
        val legendY = size.height - 10.dp.toPx()
        drawContext.canvas.nativeCanvas.apply {
            val paint = android.graphics.Paint().apply {
                color = colors.primaryText.toArgbCompat()
                textSize = 11.sp.toPx()
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.RIGHT
            }
            drawText("Less", size.width * 0.46f, legendY, paint)
            paint.textAlign = android.graphics.Paint.Align.LEFT
            drawText("More", size.width * 0.76f, legendY, paint)
        }
        repeat(4) { index ->
            drawRoundRect(
                color = colors.heatLevels[index + 1],
                topLeft = Offset(size.width * 0.48f + index * (cell + 5.dp.toPx()), legendY - cell + 2.dp.toPx()),
                size = Size(cell, cell),
                cornerRadius = CornerRadius(2.8.dp.toPx(), 2.8.dp.toPx())
            )
        }
    }
}

@Composable
private fun ProgressBar(colors: DashboardColors, tint: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(colors.track)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.78f)
                .clip(RoundedCornerShape(6.dp))
                .background(tint)
        )
    }
}

@Composable
private fun FireRow(colors: DashboardColors) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        repeat(8) { index ->
            Icon(
                imageVector = Icons.Rounded.LocalFireDepartment,
                contentDescription = null,
                tint = if (index < 6) colors.gold else colors.tertiaryText.copy(alpha = 0.5f),
                modifier = Modifier.size(19.dp)
            )
        }
    }
}

@Composable
private fun DumbbellArt(colors: DashboardColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width * 0.54f, size.height * 0.52f)
        drawSparkleField(colors)
        rotate(degrees = -24f, pivot = center) {
            drawRoundRect(
                color = colors.accent.copy(alpha = 0.92f),
                topLeft = Offset(size.width * 0.28f, size.height * 0.47f),
                size = Size(size.width * 0.44f, size.height * 0.11f),
                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
            )
            drawWeightPlate(size.width * 0.20f, size.height * 0.38f, colors)
            drawWeightPlate(size.width * 0.66f, size.height * 0.38f, colors)
        }
    }
}

private fun DrawScope.drawWeightPlate(x: Float, y: Float, colors: DashboardColors) {
    drawRoundRect(
        brush = Brush.verticalGradient(listOf(FreshCoralHighlight, BloomCoralFlame)),
        topLeft = Offset(x, y),
        size = Size(size.width * 0.18f, size.height * 0.28f),
        cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
    )
    drawRoundRect(
        color = Color.White.copy(alpha = 0.16f),
        topLeft = Offset(x + size.width * 0.03f, y + size.height * 0.03f),
        size = Size(size.width * 0.04f, size.height * 0.22f),
        cornerRadius = CornerRadius(7.dp.toPx(), 7.dp.toPx())
    )
}

@Composable
private fun ConfettiArt(colors: DashboardColors, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawSparkleField(colors)
        val pieces = listOf(
            Offset(0.18f, 0.70f) to 42f,
            Offset(0.30f, 0.52f) to -18f,
            Offset(0.46f, 0.42f) to 14f,
            Offset(0.58f, 0.30f) to -36f,
            Offset(0.72f, 0.46f) to 22f,
            Offset(0.84f, 0.64f) to -18f,
            Offset(0.52f, 0.72f) to 8f,
            Offset(0.36f, 0.24f) to -30f
        )
        pieces.forEachIndexed { index, (point, angle) ->
            val color = if (index % 2 == 0) colors.accent else SoftPetalCoral
            rotate(degrees = angle, pivot = Offset(size.width * point.x, size.height * point.y)) {
                drawLine(
                    color = color,
                    start = Offset(size.width * point.x - 8.dp.toPx(), size.height * point.y),
                    end = Offset(size.width * point.x + 8.dp.toPx(), size.height * point.y),
                    strokeWidth = 2.4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
        repeat(5) { index ->
            val x = size.width * (0.22f + index * 0.14f)
            val y = size.height * (0.30f + (index % 3) * 0.18f)
            drawArc(
                color = if (index % 2 == 0) colors.accent else colors.tertiaryText,
                startAngle = 20f,
                sweepAngle = 230f,
                useCenter = false,
                topLeft = Offset(x, y),
                size = Size(20.dp.toPx(), 18.dp.toPx()),
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

private fun DrawScope.drawSparkleField(colors: DashboardColors) {
    val points = listOf(
        Offset(size.width * 0.08f, size.height * 0.26f),
        Offset(size.width * 0.38f, size.height * 0.10f),
        Offset(size.width * 0.82f, size.height * 0.22f),
        Offset(size.width * 0.90f, size.height * 0.58f)
    )
    points.forEachIndexed { index, point ->
        rotate(degrees = 45f, pivot = point) {
            drawRect(
                color = colors.accent.copy(alpha = if (index == 1) 0.72f else 0.44f),
                topLeft = Offset(point.x - 3.dp.toPx(), point.y - 3.dp.toPx()),
                size = Size(6.dp.toPx(), 6.dp.toPx()),
                style = Stroke(width = 1.4.dp.toPx(), join = StrokeJoin.Round)
            )
        }
    }
}

private fun DrawScope.drawBloomLogo(colors: DashboardColors) {
    val center = this.center
    val petalWidth = size.minDimension * 0.18f
    val petalHeight = size.minDimension * 0.27f
    val strokeWidth = size.minDimension * 0.045f
    val logoBrush = Brush.verticalGradient(
        colors = colors.logoGradient,
        startY = center.y - petalHeight,
        endY = center.y + petalHeight
    )

    listOf(-50f, 0f, 50f, -108f, 108f).forEach {
        rotate(degrees = it, pivot = center) {
            val petal = Path().apply {
                moveTo(center.x, center.y + petalHeight * 0.48f)
                cubicTo(
                    center.x - petalWidth * 0.64f,
                    center.y + petalHeight * 0.14f,
                    center.x - petalWidth * 0.48f,
                    center.y - petalHeight * 0.38f,
                    center.x,
                    center.y - petalHeight * 0.58f
                )
                cubicTo(
                    center.x + petalWidth * 0.48f,
                    center.y - petalHeight * 0.38f,
                    center.x + petalWidth * 0.64f,
                    center.y + petalHeight * 0.14f,
                    center.x,
                    center.y + petalHeight * 0.48f
                )
            }
            drawPath(
                path = petal,
                brush = logoBrush,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}

private fun DrawScope.drawIntoCenterArrow(colors: DashboardColors) {
    val circleCenter = Offset(center.x, size.height * 0.16f)
    drawCircle(
        brush = Brush.verticalGradient(listOf(SoftPetalCoral.copy(alpha = 0.95f), FreshCoralHighlight.copy(alpha = 0.8f))),
        radius = size.minDimension * 0.16f,
        center = circleCenter
    )
    drawLine(
        color = colors.primaryText,
        start = Offset(circleCenter.x - 8.dp.toPx(), circleCenter.y),
        end = Offset(circleCenter.x + 10.dp.toPx(), circleCenter.y),
        strokeWidth = 2.2.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = colors.primaryText,
        start = Offset(circleCenter.x + 10.dp.toPx(), circleCenter.y),
        end = Offset(circleCenter.x + 3.dp.toPx(), circleCenter.y - 7.dp.toPx()),
        strokeWidth = 2.2.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = colors.primaryText,
        start = Offset(circleCenter.x + 10.dp.toPx(), circleCenter.y),
        end = Offset(circleCenter.x + 3.dp.toPx(), circleCenter.y + 7.dp.toPx()),
        strokeWidth = 2.2.dp.toPx(),
        cap = StrokeCap.Round
    )
}

private fun Modifier.dashboardBackdrop(colors: DashboardColors, darkTheme: Boolean): Modifier {
    return drawBehind {
        drawCircle(
            brush = Brush.radialGradient(
                colors = colors.topGlow,
                center = Offset(size.width * 0.28f, 0f),
                radius = size.maxDimension * if (darkTheme) 0.48f else 0.36f
            ),
            radius = size.maxDimension * if (darkTheme) 0.48f else 0.36f,
            center = Offset(size.width * 0.28f, 0f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = colors.bottomGlow,
                center = Offset(size.width * 0.72f, size.height * 0.86f),
                radius = size.maxDimension * if (darkTheme) 0.34f else 0.25f
            ),
            radius = size.maxDimension * if (darkTheme) 0.34f else 0.25f,
            center = Offset(size.width * 0.72f, size.height * 0.86f)
        )
    }
}

private fun Color.toArgbCompat(): Int {
    return android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}

@Immutable
private data class DashboardColors(
    val background: Color,
    val card: Color,
    val innerSurface: Color,
    val navSurface: Color,
    val navActiveBackground: Color,
    val border: Color,
    val shadow: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val tertiaryText: Color,
    val accent: Color,
    val recovery: Color,
    val food: Color,
    val flag: Color,
    val gold: Color,
    val track: Color,
    val pillBackground: Color,
    val badgeBackground: Color,
    val wordmarkFallback: Color,
    val wordmarkGradient: List<Color>,
    val logoGradient: List<Color>,
    val heatLevels: List<Color>,
    val topGlow: List<Color>,
    val bottomGlow: List<Color>
) {
    companion object {
        fun light() = DashboardColors(
            background = WarmMilkCanvas,
            card = PearlCardSurface.copy(alpha = 0.86f),
            innerSurface = Color.White.copy(alpha = 0.54f),
            navSurface = Color.White.copy(alpha = 0.78f),
            navActiveBackground = SoftPetalCoral.copy(alpha = 0.18f),
            border = PeachHairlineBorder.copy(alpha = 0.92f),
            shadow = SoftPetalCoral.copy(alpha = 0.16f),
            primaryText = PrimaryInk,
            secondaryText = SoftCharcoal,
            tertiaryText = MutedWarmGray,
            accent = BloomCoralFlame,
            recovery = Color(0xFF2EBF5A),
            food = Color(0xFF4287FB),
            flag = Color(0xFFA99898),
            gold = Color(0xFFFDA92F),
            track = Color(0xFFF1E7E5),
            pillBackground = Color.White.copy(alpha = 0.5f),
            badgeBackground = SoftPetalCoral.copy(alpha = 0.18f),
            wordmarkFallback = PressedEmberRed,
            wordmarkGradient = listOf(PressedEmberRed, BloomCoralFlame, FreshCoralHighlight),
            logoGradient = listOf(PressedEmberRed, BloomCoralFlame, FreshCoralHighlight),
            heatLevels = listOf(
                Color(0xFFF9E8E5),
                Color(0xFFFFE3DE),
                Color(0xFFFFB5A8),
                Color(0xFFFF7F70),
                BloomCoralFlame
            ),
            topGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.30f),
                SoftPetalCoral.copy(alpha = 0.08f),
                Color.Transparent
            ),
            bottomGlow = listOf(
                SoftPetalCoral.copy(alpha = 0.22f),
                Color.Transparent
            )
        )

        fun dark() = DashboardColors(
            background = MidnightPlumCanvas,
            card = SmokedCardSurface.copy(alpha = 0.66f),
            innerSurface = Color.Black.copy(alpha = 0.10f),
            navSurface = Color(0xE60B090D),
            navActiveBackground = BloomCoralFlame.copy(alpha = 0.12f),
            border = RosewoodBorder.copy(alpha = 0.86f),
            shadow = BloomCoralFlame.copy(alpha = 0.10f),
            primaryText = Color.White,
            secondaryText = SoftAshText,
            tertiaryText = MutedRoseGray,
            accent = FreshCoralHighlight,
            recovery = Color(0xFF35E56D),
            food = FreshCoralHighlight,
            flag = SoftPetalCoral,
            gold = Color(0xFFFFB336),
            track = Color(0xFF1E1A1D),
            pillBackground = Color.Black.copy(alpha = 0.12f),
            badgeBackground = BloomCoralFlame.copy(alpha = 0.16f),
            wordmarkFallback = SoftPetalCoral,
            wordmarkGradient = listOf(SoftPetalCoral, FreshCoralHighlight),
            logoGradient = listOf(SoftPetalCoral, FreshCoralHighlight),
            heatLevels = listOf(
                Color(0xFF211D22),
                Color(0xFF322026),
                Color(0xFF6B2929),
                Color(0xFFB63F35),
                FreshCoralHighlight
            ),
            topGlow = listOf(
                DeepWineGlow.copy(alpha = 0.95f),
                BloomCoralFlame.copy(alpha = 0.20f),
                Color.Transparent
            ),
            bottomGlow = listOf(
                BloomCoralFlame.copy(alpha = 0.14f),
                Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomDashboardScreenLightPreview() {
    FitMessTheme(darkTheme = false) {
        BloomDashboardScreen()
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun BloomDashboardScreenDarkPreview() {
    FitMessTheme(darkTheme = true) {
        BloomDashboardScreen()
    }
}
