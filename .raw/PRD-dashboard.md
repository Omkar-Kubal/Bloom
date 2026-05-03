# Bloom — Dashboard PRD
## Dashboard Screen + All Sub-screens Logic

> **Version:** 1.0 · **Feature owner:** Omkar · **Last updated:** 2026-05-03
> **Screens covered:**
> - `DashboardScreen` (Home)
> - `MoveDetailScreen` (Move ring drill-down)
> - `WeeklyHabitsScreen` (Habit check-in form)
> - `CaloriesDetailScreen` (linked from Calories card — covered in PRD-nutrition.md)
>
> **Mockup refs:**
> - `bloom-ui/bloom-dashboard-dark.png` · `bloom-ui/bloom-dashboard-light.png`
> - `bloom-ui/bloom-move-dark.png` · `bloom-ui/bloom-move-light.png`
> - `bloom-ui/bloom-weeklyhabits-dark.png` · `bloom-ui/bloom-weeklyhabits-light.png`
>
> **Module:** `:feature:home` (Dashboard) · `:feature:steps` (Move detail) · `:feature:habits` (Weekly Habits)
> **Backend:** Supabase · Health Connect · PRD § 8 formulas for all calculations

---

## 1. Screen map — dashboard cluster

```
DashboardScreen (root of main nav)
    │
    ├─ Move card (+ button)     ──────────────→ MoveDetailScreen
    │       │
    │       └─ Step Count card (›)            → (future: StepCountDetailScreen)
    │       └─ Step Distance card (›)         → (future: StepDistanceDetailScreen)
    │       └─ "Edit Summary" button          → (future: EditSummarySheet)
    │
    ├─ Calories card (tap anywhere)  ─────────→ CaloriesDetailScreen (PRD-nutrition.md)
    │
    ├─ Track Workout card
    │       └─ "Start tracking ›"   ─────────→ WorkoutHomeScreen (PRD-workout.md)
    │
    ├─ Weekly Habits card
    │       └─ "Quick check in ›"   ─────────→ WeeklyHabitsScreen
    │
    ├─ Recovery Score card (›)       ─────────→ (future: RecoveryDetailScreen)
    │
    ├─ Workout Streak card (›)       ─────────→ (future: WorkoutStreakDetailScreen)
    │
    └─ Bottom nav
            ├─ Dashboard (active)             → stays on DashboardScreen
            ├─ Food                           → CaloriesDiaryScreen (PRD-nutrition.md)
            ├─ Workout                        → WorkoutHomeScreen
            ├─ Go Run                         → RunHomeScreen (PRD-running.md)
            └─ FAB (+)                        → QuickAddSheet (bottom sheet)
```

---

## 2. Dashboard screen — full spec

### 2.1 Layout architecture

```
┌─────────────────────────────────────────────┐  ← Status bar
│  [🌸 Bloom]                       [🔔●]     │  ← Top bar
│                                             │
│  Good morning! 🌸                           │
│  Let's keep growing today.                  │  ← Greeting row
│                           Today, 27 Apr 2026│
│                                    [Edit]   │  ← Date + Edit
├───────────────────┬─────────────────────────┤
│  Move card        │  Calories card           │  ← Row 1: 2-column
│  (left, ~48%)     │  (right, ~48%)           │
├───────────────────┴─────────────────────────┤
│  Track workout card (full-width)             │  ← Row 2
├─────────────────────────────────────────────┤
│  Weekly Habits card (full-width)             │  ← Row 3
├─────────────────────────────────────────────┤
│  Consistency card (full-width)               │  ← Row 4
├───────────────────┬─────────────────────────┤
│  Recovery Score   │  Workout Streak          │  ← Row 5: 2-column
└───────────────────┴─────────────────────────┘
│  [Dashboard] [Food] [Workout] [Go Run] [+]  │  ← Bottom nav
└─────────────────────────────────────────────┘
```

The entire content area is a **`LazyColumn`** (scrollable). Bottom nav is fixed. FAB is fixed, overlapping the nav bar at top-right.

---

### 2.2 Top bar

#### Brand row
- Left: Bloom lotus logo (32×32dp circle, `CardSurfaceDark/Light` fill, `BorderDark/Light` outline) + "Bloom" wordmark (`BloomCoralFlame`, Bold 22sp, 8dp gap)
- Right: bell icon button (40×40dp tap target)
  - Notification dot: 8dp circle, `BloomCoralFlame`, top-right of bell — shown when unread notifications exist
  - Tap: navigates to `NotificationsScreen` (future — v2)
  - No dot: no unread notifications

#### Greeting row
- Left column:
  - Greeting text: time-based (see § 2.2.1), Bold 22sp, `Text1`
  - Flower emoji 🌸 inline after greeting text
  - Subtext: "Let's keep growing today." — Regular 14sp, `Text2`, 2dp below greeting
- Right column (aligned to top-right):
  - Date: "Today, 27 Apr 2026" — Regular 13sp, `Text2`
  - Edit button: pill shape, `CardSurfaceDark/Light` fill, `BorderDark/Light` outline, "Edit" in `BloomCoralFlame`, 12sp Medium
  - Edit tap: navigates to `GoalEditScreen` (future — v2, shows calorie goal + macro split)

#### 2.2.1 Greeting logic

```kotlin
fun getGreeting(hour: Int): String = when (hour) {
    in 5..11  -> "Good morning!"
    in 12..16 -> "Good afternoon!"
    in 17..20 -> "Good evening!"
    else      -> "Good night!"
}
// hour = LocalTime.now().hour (device local time)
```

The greeting recalculates each time the screen enters composition (not on a timer). If the user leaves the app and returns 4 hours later, the greeting updates.

---

### 2.3 Move card (left column, top row)

**Entry point:** Tapping the `+` circle button navigates to `MoveDetailScreen`. Tapping the card body also navigates to `MoveDetailScreen`.

#### Data sources (in priority order)
1. **Health Connect** `ExerciseSessionRecord` + `ActiveCaloriesBurnedRecord` — preferred
2. **Health Connect** `StepsRecord` converted to kcal via PRD formula
3. **Sensor fallback** `TYPE_STEP_COUNTER` — if Health Connect unavailable

#### Displayed values
| Element | Value | Source |
|---|---|---|
| "Move" label | Static | — |
| `51/200` | `activeKcalToday / moveGoalKcal` | Health Connect |
| "KCAL" label | Static | — |
| Ring progress | `activeKcalToday / moveGoalKcal` × 360° | Calculated |
| Sparkline bars | Hourly kcal buckets, 24 buckets (12AM→12AM) | Health Connect |
| `12 AM 6 AM 12 PM 6 PM 12 AM` | X-axis labels | Static |
| "TOTAL 1,272 KCAL" | Total kcal burned all day (TDEE contribution) | Health Connect |

#### Ring component spec
- Stroke width: 18dp (thick Apple Watch style)
- Start angle: 270° (12 o'clock position)
- Direction: clockwise
- Fill: `BloomCoralFlame (#FD6055)`
- Track: `RingTrackDark/Light (#1E1A1D / #FEEEEA)`
- Arrow tip: small `→` arrow rotated to match arc endpoint tangent
- Cap: round (`StrokeCap.Round`)
- Animation: arc draws 0→current on first composition, 700ms ease-out

#### `+` button (top-right of Move card)
- 28×28dp circle, `CardSurfaceDark/Light` fill, `BorderDark/Light` outline
- `+` icon in `BloomCoralFlame`, 16dp
- Tap → `MoveDetailScreen`

#### Sparkline
- 24 vertical bars representing 1-hour buckets (midnight to midnight)
- Bar height proportional to kcal in that hour
- Active bars (hours with activity): `BloomCoralFlame`
- Peak bar (highest kcal hour): slightly brighter/taller visual emphasis
- Baseline: dotted horizontal line in `Text3`
- No Y-axis labels on dashboard sparkline (only on MoveDetailScreen)
- No tap interaction on sparkline in dashboard — entire card taps to MoveDetailScreen

#### Move goal
- Default: 200 kcal/day (user-configurable in profile settings)
- Stored in: `UserGoal.moveGoalKcal` in Room
- Used for ring percentage + "X/200 KCAL" display

#### Move card empty state
- Ring shows 0% fill (track only)
- "0/200 KCAL" displayed
- Sparkline is a flat dotted line
- "TOTAL 0 KCAL" shown
- No error state — empty is valid at day start

---

### 2.4 Calories card (right column, top row)

**Tap:** entire card taps to `CaloriesDetailScreen` (see PRD-nutrition.md).

#### Formula (shown explicitly in mockup top-right)
```
Remaining = Goal – Food + Exercise
```

```kotlin
val remaining = calorieGoal - foodCaloriesToday + exerciseCaloriesToday
// exerciseCaloriesToday = activeKcalBurned from Health Connect
// foodCaloriesToday = sum of all food entries in Room for today
```

#### Displayed values
| Element | Value |
|---|---|
| "2,015" (center) | `remaining` kcal — large, Bold 800, 36sp, `Text1` |
| "Remaining" label | Static, Regular 13sp, `Text2` |
| 🏁 Base Goal | `userGoal.calorieGoal` (default 2,000) |
| 🍴 Food | `foodCaloriesToday` (sum of today's food entries) |
| 🔥 Exercise | `exerciseCaloriesToday` (from Health Connect) |

#### Calorie ring (in card)
- Thin donut ring (stroke ~8dp), centered
- Fill represents `foodCaloriesToday / calorieGoal` percentage (eaten portion)
- A small amber marker (`#FDA92F`) sits at the 12 o'clock position (top)
- Empty ring = goal remaining = good state
- Over 100% fill: ring completes and overlaps (no hard cap on display)
- Track: `RingTrackDark/Light`

#### Remaining value color rules
```kotlin
val textColor = when {
    remaining >= 200  -> Text1         // plenty left — neutral white/black
    remaining in 1..199 -> StreakGold  // getting close — amber warning
    remaining <= 0    -> Color(0xFFE53935) // over goal — red
}
```

---

### 2.5 Track Workout card (full-width)

#### Always visible — no conditional display logic
- Title: "Track workout" — Bold 700, 18sp, `Text1`
- BETA badge: "BETA" pill — `BloomCoralFlame` text, `AccentSoftDark/Light` fill, 9sp Bold, 6dp horizontal padding, 3dp vertical padding
- Body text: "Log your workouts, stay consistent, and see your progress grow." — Regular 14sp, `Text2`
- Illustration: 3D glossy dumbbell, right side — decorative, no tap target
- Scattered decorative dots: `BloomCoralFlame` at 30% opacity

#### "Start tracking ›" button
- Pill shape, `AccentSoftDark/Light` fill, no border
- "Start tracking" in `BloomCoralFlame`, Medium 13sp
- `›` chevron in `BloomCoralFlame`
- Tap → `WorkoutHomeScreen`
- Entire card is also tappable → same destination

---

### 2.6 Weekly Habits card (full-width)

#### Visibility logic
This card is **conditionally shown** based on whether:
1. The current week has at least 1 day of recorded activity, AND
2. The user has not checked in this week already

```kotlin
val showWeeklyHabitsCard = hasActivityThisWeek && !hasCheckedInThisWeek
// hasCheckedInThisWeek: check weekly_habit_checkins table for current week's ISO week number
```

If hidden: card is removed from the LazyColumn (not just invisible — remove entirely to avoid layout gap).

#### Dynamic copy variants

| Condition | Title | Body |
|---|---|---|
| Week complete, no check-in | "Weekly Habits" | "Congratulations on your week of habits! Let's keep your momentum rolling." |
| Mid-week, 3+ active days | "Weekly Habits" | "You're on a roll! Keep building this week's habits." |
| First week ever | "Weekly Habits" | "Start your first habit tracking week. Small steps lead to big results." |

#### "Quick check in ›" button
- Same pill style as Track Workout card
- Tap → `WeeklyHabitsScreen`
- Entire card also tappable → same destination

#### Confetti illustration
- Decorative right-side confetti SVG
- Colors: `BloomCoralFlame`, `FreshCoralHighlight`, `SoftPetalCoral`, plus neutral gray/silver
- No tap target, no animation on dashboard (animation plays on `WeeklyHabitsScreen` submit)

---

### 2.7 Consistency card (full-width)

#### Header row
- Left: "Consistency" — Bold 700, 17sp, `Text1`
- Subtext: "Your daily activity this month" — Regular 13sp, `Text2`
- Right: `18` in `BloomCoralFlame` + `/30 days` in `Text3` — "18/30 days"
  - `18` = number of days with any recorded activity in current month
  - `30` = total days in current calendar month

#### Heatmap grid

**Layout:**
- 7 rows (M T W T F S S) × N columns (days in month, from start of month to today)
- Each cell: 10×10dp, 2dp gap, `BorderRadiusSM (3dp)`
- Day labels (M T W T F S S): 10sp, `Text3`, left column

**Cell color intensity (5 levels):**
```kotlin
val intensity = when {
    kcalBurned == 0                         -> 0  // no activity
    kcalBurned in 1..50                     -> 1  // very low
    kcalBurned in 51..150                   -> 2  // low
    kcalBurned in 151..300                  -> 3  // moderate
    kcalBurned > 300                        -> 4  // high
}

val cellColor = when (intensity) {
    0 -> RingTrackDark/Light              // #1E1A1D / #FEEEEA
    1 -> SoftPetalCoral.copy(alpha=0.35)  // #F9A495 at 35%
    2 -> SoftPetalCoral                   // #F9A495 full
    3 -> FreshCoralHighlight              // #FE7867
    4 -> BloomCoralFlame                  // #FD6055
    else -> RingTrackDark
}
```

**Future days** (after today in current month): shown as empty cells (`RingTrackDark/Light`) — no fill, no label.

**Legend row** (below grid):
- "Less" label in `Text3` + 5 sample squares (opacity 0→1 of `BloomCoralFlame`) + "More" label
- This is a visual legend only — not interactive

#### Streak mini-cards (right side of Consistency card)

Two stacked mini-cards inside the Consistency card:

**Current Streak card:**
- Flame icon `🔥` in 28dp circle, `AccentSoftDark/Light` fill
- "Current Streak" — 11sp, `Text3`
- `6 days` — Bold 700, 18sp, `Text1`
- Data: count of consecutive days (ending today or yesterday) with ≥1 kcal burned

**Longest Streak card:**
- Trophy icon `🏆` in 28dp circle, `AccentSoftDark/Light` fill but amber tint (`StreakGold` at 15%)
- "Longest Streak" — 11sp, `Text3`
- `12 days` — Bold 700, 18sp, `Text1`
- Data: all-time longest consecutive streak for this user (stored in `UserStats.longestStreak`)

**Streak calculation logic:**
```kotlin
fun calculateCurrentStreak(activityLog: List<LocalDate>): Int {
    val today = LocalDate.now()
    val sortedDates = activityLog.sortedDescending().toSet()
    var streak = 0
    var checkDate = today

    // Allow 1-day grace: if today has no activity yet, start from yesterday
    if (today !in sortedDates) checkDate = today.minusDays(1)

    while (checkDate in sortedDates) {
        streak++
        checkDate = checkDate.minusDays(1)
    }
    return streak
}
```

No tap on these mini-cards in v1 (chevron for future navigation — placeholder).

---

### 2.8 Recovery Score card (left column, bottom row)

#### Data source
- v1: **Manually calculated proxy** — not wearable-connected
- Formula: blend of sleep quality (stubbed at 7/10), step count vs goal (%), and workout frequency this week
- Range: 0–100

```kotlin
fun calculateRecoveryScore(
    stepsToday: Int,
    stepGoal: Int,
    workoutsThisWeek: Int
): Int {
    val stepScore = (stepsToday.toFloat() / stepGoal).coerceIn(0f, 1f) * 40
    val workoutScore = workoutsThisWeek.coerceIn(0, 5) * 8f  // max 40 pts for 5 workouts
    val baseScore = 20f  // everyone starts at 20
    return (baseScore + stepScore + workoutScore).toInt().coerceIn(0, 100)
}
```

#### Display logic
| Score range | Label | Label color |
|---|---|---|
| 80–100 | "Excellent" | `FreshLeafGreen (#57CB77)` |
| 60–79 | "Good" | `RecoveryGreen (#2EBF5A)` |
| 40–59 | "Fair" | `StreakGold (#FDA92F)` |
| 0–39 | "Low" | `BloomCoralFlame (#FD6055)` |

#### Displayed elements
- Heart-rate icon in 32dp circle, `RecoveryGreen` tint fill
- "Recovery Score" — Regular 13sp, `Text2`
- `78` — ExtraBold 800, 28sp, label color from table above
- "Good" — SemiBold 600, 14sp, label color
- Progress bar: width proportional to score (78%), `RecoveryGreen` fill, 4dp height, `RingTrack` background
- "Your body is recovering well." — Regular 12sp, `Text3`
- `›` chevron: navigates to future `RecoveryDetailScreen`

---

### 2.9 Workout Streak card (right column, bottom row)

#### Data source
- Count of consecutive days with at least 1 workout logged in Room's `WorkoutSession` table
- Separate from activity/step streak — only counts deliberate logged workouts

#### Displayed elements
- Flame icon `🔥` in 32dp circle, amber tint fill
- "Workout Streak" — Regular 13sp, `Text2`
- `6 days` — ExtraBold 800, 26sp, `Text1`
- Flame row: 8 flame emojis representing last 8 days
  - Active day (workout logged): `🔥` full opacity
  - Inactive day (no workout): `🔥` at 20% opacity (grayed out)
  - Arranged left to right, oldest to newest (leftmost = 8 days ago, rightmost = today)
- "Keep it going!" — Regular 12sp, `Text3`
- `›` chevron: future navigation

---

### 2.10 Bottom navigation

**Structure:**
```
[Dashboard] [Food] [Workout] [Go Run]      [+ FAB]
```

| Tab | Icon | Active behavior | Destination |
|---|---|---|---|
| Dashboard | 4-square grid icon | `BloomCoralFlame` icon + label, soft rounded highlight | Stays on DashboardScreen |
| Food | Plate/cutlery icon | Same active treatment | `CaloriesDiaryScreen` |
| Workout | Barbell/dumbbell icon | Same | `WorkoutHomeScreen` |
| Go Run | Running figure icon | Same | `RunHomeScreen` |

**FAB (floating action button):**
- 52×52dp circle, `BloomCoralFlame` fill
- White `+` icon, 24dp
- Positioned: 16dp from right edge, overlapping top of nav bar by ~16dp
- Tap → `QuickAddSheet` (bottom sheet with: Log Food / Log Workout / Start Run / Log Water / Log Weight)
- Coral glow shadow beneath FAB: `BloomCoralFlame` at 40% opacity, 12dp blur

**Nav bar styling:**
- Dark: `NavSurfaceDark (#0C0B0F)`, `BorderDark` top border
- Light: `NavSurfaceLight (#FEFBF9)`, `BorderLight` top border
- Height: 64dp + bottom safe area inset
- Active item: icon + label in `BloomCoralFlame`, soft rounded rectangle highlight behind (16dp radius, `AccentSoftDark/Light`)
- Inactive: icon + label in `Text3`

---

### 2.11 Dashboard ViewModel

```kotlin
data class DashboardUiState(
    // Greeting
    val greeting: String = "",
    val todayDateFormatted: String = "",        // "Today, 27 Apr 2026"

    // Move card
    val activeKcalToday: Int = 0,
    val moveGoalKcal: Int = 200,
    val hourlyKcalBuckets: List<Float> = List(24) { 0f },
    val totalKcalBurned: Int = 0,

    // Calories card
    val calorieGoal: Int = 2000,
    val foodCaloriesToday: Int = 0,
    val exerciseCaloriesToday: Int = 0,
    val caloriesRemaining: Int = 2000,

    // Weekly habits card
    val showWeeklyHabitsCard: Boolean = false,
    val weeklyHabitsCardVariant: HabitsCardVariant = HabitsCardVariant.WEEK_COMPLETE,

    // Consistency
    val monthlyActivityDays: Map<LocalDate, Int> = emptyMap(), // date → kcal burned
    val daysActiveThisMonth: Int = 0,
    val totalDaysInMonth: Int = 30,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,

    // Recovery
    val recoveryScore: Int = 0,
    val recoveryLabel: String = "",
    val recoveryLabelColor: Color = RecoveryGreen,

    // Workout streak
    val workoutStreak: Int = 0,
    val lastEightDaysWorkouts: List<Boolean> = List(8) { false },

    // Notifications
    val hasUnreadNotifications: Boolean = false,

    // Loading
    val isLoading: Boolean = true,
)

enum class HabitsCardVariant {
    WEEK_COMPLETE, MID_WEEK_ACTIVE, FIRST_WEEK
}

sealed class DashboardEffect {
    object NavigateToMoveDetail : DashboardEffect()
    object NavigateToCaloriesDetail : DashboardEffect()
    object NavigateToWorkoutHome : DashboardEffect()
    object NavigateToWeeklyHabits : DashboardEffect()
    object NavigateToNotifications : DashboardEffect()
    object NavigateToGoalEdit : DashboardEffect()
    object ShowQuickAddSheet : DashboardEffect()
}
```

---

### 2.12 Data loading sequence

```
DashboardViewModel.init()
    │
    ├─ Launch coroutine 1: Health Connect data
    │       ├─ Request READ_STEPS + READ_ACTIVE_CALORIES_BURNED permissions
    │       ├─ Query today's ExerciseSessionRecords
    │       ├─ Query today's ActiveCaloriesBurnedRecords (hourly buckets)
    │       └─ Update: activeKcalToday, hourlyKcalBuckets, totalKcalBurned
    │
    ├─ Launch coroutine 2: Room food data
    │       ├─ Observe Flow<List<FoodEntry>> for today
    │       └─ Update: foodCaloriesToday (reacts to new entries automatically)
    │
    ├─ Launch coroutine 3: User goals
    │       ├─ Room query: UserGoal table
    │       └─ Update: calorieGoal, moveGoalKcal
    │
    ├─ Launch coroutine 4: Consistency / streaks
    │       ├─ Room query: ActivityLog for current month
    │       ├─ Supabase query: UserStats for longestStreak
    │       └─ Calculate: currentStreak, heatmap data, daysActiveThisMonth
    │
    ├─ Launch coroutine 5: Workout streak
    │       ├─ Room query: WorkoutSession last 8 days
    │       └─ Update: workoutStreak, lastEightDaysWorkouts
    │
    └─ Launch coroutine 6: Weekly habits visibility
            ├─ Room query: WeeklyHabitCheckin for current ISO week
            ├─ Check: hasActivityThisWeek (from ActivityLog)
            └─ Update: showWeeklyHabitsCard, variant
```

All 6 launch simultaneously (`launch`, not sequential). `isLoading = false` only after all 6 complete. While loading, show skeleton placeholders for each card section.

---

### 2.13 Skeleton loading state

While `isLoading == true`, each card shows:
- Animated shimmer rectangle (same dimensions as the card)
- Shimmer: gradient sweep from `CardSurface` → slightly lighter → back to `CardSurface`
- Duration: 1200ms loop, ease-in-out

No error state on dashboard — if data fails to load, cards show 0/default values with a small retry icon in top-right of affected card.

---

### 2.14 Pull-to-refresh

- `SwipeRefresh` / `pullRefresh` Compose modifier on the `LazyColumn`
- Triggers full data reload (re-runs all 6 coroutines)
- Indicator: `BloomCoralFlame` circular progress at top
- Minimum visible time: 600ms (so it doesn't flash)

---

### 2.15 Daily reset logic

All "today" data resets at **midnight device local time**.

```kotlin
// In DashboardViewModel, observe date changes:
val currentDate = snapshotFlow { LocalDate.now() }
    .distinctUntilChanged()
    .onEach { newDate ->
        if (newDate != lastObservedDate) {
            lastObservedDate = newDate
            reloadAllData()  // force full refresh at day boundary
        }
    }
    .launchIn(viewModelScope)
```

---

## 3. Move detail screen — full spec

**Route:** `move/detail`
**Entry:** Move card `+` tap on Dashboard, or Move card body tap
**Exit:** Back arrow → Dashboard

### 3.1 Layout

```
┌─────────────────────────────────────────────┐
│  [←]   Today, 27 Apr 2026   [📅] [↑Share] │  ← Top bar
├─────────────────────────────────────────────┤
│  M   T   W   T   F   S   S                 │  ← Weekly ring strip
│  [●] [ ] [ ] [ ] [ ] [ ] [ ]               │
├─────────────────────────────────────────────┤
│  Move card (large, full-width)              │
│    51/200 KCAL                              │
│    25% of your goal                         │
│                                             │
│         [Big ring — 200dp diameter]         │
│              51                             │
│             KCAL                            │
│            /200 KCAL                        │
│                                             │
│    21 KCAL ........ [sparkline] .........   │
│    12:00 AM  6:00 AM  12:00 PM  6:00 PM    │
│    TOTAL 1,272 KCAL                         │
├─────────────────────────────────────────────┤
│  [Step Count  › ]  [Step Distance  › ]      │  ← 2-col stat cards
│   Today            Today                    │
│   2,591            1.65 KM                  │
│   [sparkline]      [sparkline]              │
│   12AM 6AM 12PM 6PM 12AM 6AM 12PM 6PM     │
├─────────────────────────────────────────────┤
│  [ Edit Summary ]                           │  ← Bottom sticky button
└─────────────────────────────────────────────┘
```

### 3.2 Top bar

- Back arrow (←): 40×40dp circle button, `CardSurface` fill, `Border` outline → `popBackStack()`
- Date: "Today, 27 Apr 2026" — SemiBold 17sp, `Text1`, centered
- Calendar icon button (📅): opens `DatePickerDialog` to view historical Move data (future feature — v2, show as non-functional in v1)
- Share icon (↑): iOS-style share sheet for exporting today's move summary (future — v2, show as non-functional in v1)

### 3.3 Weekly ring strip

7 mini-rings representing M T W T F S S of the current week (starting Monday).

**Each mini-ring:**
- Diameter: 52dp, stroke: 4dp
- Track: `RingTrackDark/Light`
- Fill: `BloomCoralFlame`
- Fill percentage: `min(dayKcal / moveGoalKcal, 1.0)` × 100%
- Today's ring: slightly larger label circle (`M` in `BloomCoralFlame`-filled circle, white text)
- Past days with data: fill shown
- Future days: track only (0% fill)
- Day label: `M T W T F S S` — 10sp, `Text3` below each ring
- Tap on any ring: updates the main Move card to show that day's data (date in top bar updates too)

**Selected day state:**
- The selected day ring gets a `BloomCoralFlame` border halo (2dp, 4dp gap from ring)
- Today is selected by default on entry

### 3.4 Move card (large version)

All data same as dashboard card, but:
- Ring: 200dp diameter (vs ~120dp on dashboard)
- Center: large "51" (ExtraBold 800, 48sp, `BloomCoralFlame`) + "KCAL" (12sp, `Text3`) + "/200 KCAL" (12sp, `Text3`) stacked vertically
- "25% of your goal" — Regular 14sp, `Text2` below the `51/200 KCAL` header
- `→` button (top-right): future navigation to full goal settings

**Sparkline (larger version):**
- Full card width, ~60dp height
- Same 24 hourly buckets as dashboard
- Y-axis label: "21 KCAL" — peak value shown as a dotted horizontal reference line
- X-axis labels: `12:00 AM · 6:00 AM · 12:00 PM · 6:00 PM`
- Bar color: `BloomCoralFlame`
- Dotted baseline: `Text3`
- Tap a bar: shows tooltip with exact kcal for that hour (future v2 — skip in v1)

### 3.5 Step Count card (left, bottom 2-col)

| Element | Value |
|---|---|
| "Step Count" | Header, SemiBold 600, 15sp, `Text1` |
| `›` | Chevron, future navigation |
| "Today" | Regular 13sp, `Text2` |
| `2,591` | ExtraBold 800, 28sp, `BloomCoralFlame` |
| Sparkline | Same 24-bucket hourly sparkline, `BloomCoralFlame` bars, smaller format |
| X-axis | `12 AM · 6 AM · 12 PM · 6 PM` |

**Data source:** Health Connect `StepsRecord`, grouped by hour.

### 3.6 Step Distance card (right, bottom 2-col)

| Element | Value |
|---|---|
| "Step Distance" | Header |
| `›` | Chevron |
| "Today" | Subtext |
| `1.65 KM` | `ExtraBold 800, 28sp, `BloomCoralFlame` for "1.65", `Text1` for "KM"` |
| Sparkline | Same format as Step Count but showing distance per hour |

**Distance calculation (PRD formula):**
```kotlin
val strideLength_m = (userHeight_cm * if (userSex == Male) 0.415 else 0.413) / 100
val distanceKm = (totalSteps * strideLength_m) / 1000
// Display: formatted to 2 decimal places, always in KM (v1 — no imperial toggle)
```

### 3.7 "Edit Summary" button (sticky bottom)

- Full-width, 52dp height, 14dp radius
- Background: `AccentSoftDark/Light` fill, `BloomCoralFlame` border 1dp
- Text: "Edit Summary" in `BloomCoralFlame`, SemiBold 600, 16sp
- Tap: future — opens `EditGoalSheet` to change move calorie goal (v2 placeholder in v1)
- Position: sticky at bottom of screen, above bottom safe area
- Note: No main bottom nav on this screen — back arrow only navigation

### 3.8 MoveDetailViewModel

```kotlin
data class MoveDetailUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val weekDates: List<LocalDate> = emptyList(),      // Mon–Sun of current week
    val weekRingData: List<Float> = List(7) { 0f },    // 0.0–1.0 fill per day
    val activeKcal: Int = 0,
    val moveGoalKcal: Int = 200,
    val percentOfGoal: Int = 0,
    val hourlyKcalBuckets: List<Float> = List(24) { 0f },
    val peakKcal: Int = 0,
    val totalKcalBurned: Int = 0,
    val stepCount: Int = 0,
    val stepDistanceKm: Float = 0f,
    val hourlyStepBuckets: List<Float> = List(24) { 0f },
    val isLoading: Boolean = true,
)

sealed class MoveDetailEvent {
    data class DaySelected(val date: LocalDate) : MoveDetailEvent()
    object BackClicked : MoveDetailEvent()
    object CalendarClicked : MoveDetailEvent()
    object ShareClicked : MoveDetailEvent()
    object EditSummaryClicked : MoveDetailEvent()
}
```

---

## 4. Weekly Habits screen — full spec

**Route:** `habits/weekly-checkin`
**Entry:** "Quick check in ›" on Weekly Habits dashboard card
**Exit:** Back arrow → Dashboard (without submitting) OR Submit → Dashboard (with success state)

### 4.1 Layout

```
┌─────────────────────────────────────────────┐
│  [←]          Weekly Habits                 │  ← Top bar
├─────────────────────────────────────────────┤
│  How did your habit go?                     │  ← Page heading
│  Let's reflect and look ahead to your       │
│  next healthy habit.                        │
├─────────────────────────────────────────────┤
│  Q1: Do you feel like you're making         │
│  progress toward your health and fitness    │
│  goals? *                                   │
│  [Select                            ▾]      │  ← Dropdown
├─────────────────────────────────────────────┤
│  Q2: Does this habit feel "automatic"       │
│  (or effortless) to complete? *             │
│  [Select                            ▾]      │  ← Dropdown
├─────────────────────────────────────────────┤
│  Q3: Do you want to continue tracking       │
│  this habit? *                              │
│  ◉ Let's keep going with this habit!        │
│  ○ I'm going to start a different habit     │  ← Radio group
│  ○ I will not start another habit           │
│  ○ Unsure                                   │
├─────────────────────────────────────────────┤
│  Anything more to add?                      │
│  ┌───────────────────────────────────────┐  │
│  │ Type your thoughts...              0/250│  │  ← Text area
│  └───────────────────────────────────────┘  │
├─────────────────────────────────────────────┤
│  [           Submit              ]          │  ← Sticky CTA
└─────────────────────────────────────────────┘
```

### 4.2 Top bar

- Back arrow (←): 40×40dp circle button, `CardSurface/Border`
- "Weekly Habits" — SemiBold 600, 17sp, `Text1`, centered
- No right-side action
- Tap back → `popBackStack()`, no save of partial data

### 4.3 Page heading

- "How did your habit go?" — Bold 700, 22sp, `Text1`
- "Let's reflect and look ahead to your next healthy habit." — Regular 14sp, `Text2`, 4dp below heading
- 24dp section spacing below subtext

### 4.4 Question 1 — Progress dropdown

**Label:** "Do you feel like you're making progress toward your health and fitness goals?" + `*` in `BloomCoralFlame` (required)

**Dropdown options:**
```
1. "Yes, definitely!"
2. "Yes, somewhat"
3. "Not really"
4. "No, I'm struggling"
5. "I'm not sure yet"
```

**Dropdown component:**
- Closed state: full-width, 56dp height, 12dp radius, `CardSurface` fill, `Border` outline
- "Select" placeholder in `Text3`, 15sp
- `▾` chevron in `Text3`, right-aligned
- Tap: opens `DropdownMenu` (Material 3) with 5 options, each 48dp height
- Selected state: chosen option text in `Text1`, chevron turns to `BloomCoralFlame`
- Dark mode chevron: `BloomCoralFlame` on selection, `Text3` when unselected
- Light mode: same behavior

### 4.5 Question 2 — Effortless dropdown

**Label:** "Does this habit feel \"automatic\" (or effortless) to complete?" + `*`

**Dropdown options:**
```
1. "Yes, it feels completely automatic"
2. "Mostly — barely have to think about it"
3. "Sometimes, but still requires effort"
4. "No, I still have to push myself"
5. "I haven't tried this week"
```

Same component spec as Q1.

### 4.6 Question 3 — Continue tracking radio group

**Label:** "Do you want to continue tracking this habit?" + `*`

**Options (radio buttons, single-select):**
```
● Let's keep going with this habit!       ← default selected
○ I'm going to start a different habit
○ I will not start another habit
○ Unsure
```

**Radio button spec:**
- Outer circle: 20×20dp, `Border` outline
- Selected: outer circle `BloomCoralFlame` fill (full), inner white dot 8dp
- Unselected: outer circle `Border` outline only, no fill
- Label: Regular 15sp, `Text1`, 12dp left gap
- Row height: 48dp (comfortable tap target)
- Spacing between options: 4dp

**Conditional behavior based on selection:**

| Selection | Post-submit action |
|---|---|
| "Let's keep going!" | Log check-in, keep current habit active |
| "Different habit" | Log check-in, prompt "Start a new habit" on Dashboard next week |
| "Not starting another" | Log check-in, hide Weekly Habits card for 2 weeks |
| "Unsure" | Log check-in, show card again next week |

### 4.7 Free text — "Anything more to add?"

**Label:** "Anything more to add?" — Bold 600, 16sp, `Text1`

**Text area:**
- Multi-line, `CardSurface` fill, `Border` outline, 12dp radius
- Min height: 96dp (3 lines)
- Placeholder: "Type your thoughts..." in `Text3`, 14sp
- Character counter: "0/250" in `Text3`, 11sp, bottom-right of field
- Max length: 250 characters enforced (input is cut off at 250)
- Counter color at limit: `BloomCoralFlame` when 250/250
- Optional (not required, no `*`)
- `KeyboardOptions(imeAction = ImeAction.Default)` — Enter creates new line

### 4.8 Submit button

**Visual:**
- Full-width, 56dp height, 28dp radius (fully rounded pill)
- Background: `BloomCoralFlame (#FD6055)` — both modes
- Text: "Submit" — SemiBold 600, 16sp, `#FFFFFF`
- Disabled state: 40% opacity
- Loading state: `CircularProgressIndicator` replaces text

**Enabled/disabled logic:**
```kotlin
val isSubmitEnabled = q1Selection != null
                   && q2Selection != null
                   && q3Selection != null
                   && !isSubmitting
// Free text (q4) is optional — does not affect enabled state
```

**Submit flow:**
```
Tap "Submit"
        │
        ▼
Step 1: Validate (all 3 required questions answered)
        → If any null: scroll to first unanswered, show red outline on its container
        │
        ▼ (all answered)
Step 2: Show loading (button spinner)
        │
        ▼
Step 3: Insert to Supabase `weekly_habit_checkins` table
        {
          user_id:     auth.uid(),
          week_number: currentISOWeekNumber,
          year:        currentYear,
          q1_progress: q1Selection,
          q2_automatic: q2Selection,
          q3_continue: q3Selection,
          q4_notes:    freeText.trim() or null,
          submitted_at: now()
        }
        │
        ├─ SUCCESS
        │       │
        │       ▼
        │   Step 4a: Update local Room cache
        │       (mark this week as checked-in → hides card on Dashboard)
        │       │
        │       ▼
        │   Step 4b: Show success animation
        │       - Confetti burst animation plays (full-screen overlay, 1.5s)
        │       - SnackBar: "Check-in saved! Keep growing 🌸"
        │       │
        │       ▼
        │   Step 4c: Navigate back to Dashboard
        │       (popBackStack — Dashboard re-evaluates showWeeklyHabitsCard → hides it)
        │
        └─ ERROR
                → SnackBar: "Couldn't save check-in. Please try again."
                → Re-enable form
```

### 4.9 Validation error states

When "Submit" is tapped with unanswered required questions:

- **Dropdown (Q1/Q2):** border becomes `BloomCoralFlame` (1.5dp), red error text below: "This field is required"
- **Radio group (Q3):** heading text gets `BloomCoralFlame` color + small "Required" note appears below heading
- Screen auto-scrolls to the first unanswered question
- Errors clear as soon as user makes a selection

### 4.10 Back navigation without saving

- Tap back arrow OR system back → `popBackStack()` immediately
- **No confirmation dialog** — data is ephemeral, user can return and re-fill
- Partially filled state is **not saved** — no draft persistence for this form

### 4.11 Supabase table

```sql
CREATE TABLE weekly_habit_checkins (
  id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id      UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
  week_number  INTEGER NOT NULL,   -- ISO week 1–53
  year         INTEGER NOT NULL,
  q1_progress  TEXT NOT NULL,      -- dropdown selection value
  q2_automatic TEXT NOT NULL,      -- dropdown selection value
  q3_continue  TEXT NOT NULL,      -- radio selection value
  q4_notes     TEXT,               -- nullable free text
  submitted_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  UNIQUE(user_id, week_number, year)
);

ALTER TABLE weekly_habit_checkins ENABLE ROW LEVEL SECURITY;
CREATE POLICY "Users manage own check-ins"
  ON weekly_habit_checkins FOR ALL
  USING (auth.uid() = user_id);
```

### 4.12 WeeklyHabitsViewModel

```kotlin
data class WeeklyHabitsUiState(
    val q1Selection: String? = null,
    val q2Selection: String? = null,
    val q3Selection: String? = null,       // radio value key
    val q4FreeText: String = "",
    val q4CharCount: Int = 0,

    // Errors (null = no error)
    val q1Error: String? = null,
    val q2Error: String? = null,
    val q3Error: String? = null,

    val isSubmitEnabled: Boolean = false,
    val isSubmitting: Boolean = false,
)

sealed class WeeklyHabitsEvent {
    data class Q1Selected(val value: String) : WeeklyHabitsEvent()
    data class Q2Selected(val value: String) : WeeklyHabitsEvent()
    data class Q3Selected(val value: String) : WeeklyHabitsEvent()
    data class Q4TextChanged(val text: String) : WeeklyHabitsEvent()
    object SubmitClicked : WeeklyHabitsEvent()
    object BackClicked : WeeklyHabitsEvent()
}

sealed class WeeklyHabitsEffect {
    object NavigateBack : WeeklyHabitsEffect()
    object ShowConfettiAndNavigateBack : WeeklyHabitsEffect()
    data class ShowError(val message: String) : WeeklyHabitsEffect()
    data class ScrollToQuestion(val questionIndex: Int) : WeeklyHabitsEffect()
}
```

---

## 5. Shared data — Room schema additions

```sql
-- Activity log (for consistency heatmap + streaks)
CREATE TABLE activity_log (
  id          UUID PRIMARY KEY,
  user_id     TEXT NOT NULL,
  date        TEXT NOT NULL,      -- ISO date "2026-04-27"
  kcal_burned INTEGER NOT NULL DEFAULT 0,
  step_count  INTEGER NOT NULL DEFAULT 0,
  created_at  TEXT NOT NULL,
  updated_at  TEXT NOT NULL,
  UNIQUE(user_id, date)
);

-- User stats (for longestStreak — synced to Supabase)
CREATE TABLE user_stats (
  user_id         TEXT PRIMARY KEY,
  longest_streak  INTEGER NOT NULL DEFAULT 0,
  total_workouts  INTEGER NOT NULL DEFAULT 0,
  updated_at      TEXT NOT NULL
);

-- User goals
CREATE TABLE user_goal (
  user_id          TEXT PRIMARY KEY,
  calorie_goal     INTEGER NOT NULL DEFAULT 2000,
  move_goal_kcal   INTEGER NOT NULL DEFAULT 200,
  step_goal        INTEGER NOT NULL DEFAULT 10000,
  carb_pct         INTEGER NOT NULL DEFAULT 40,
  protein_pct      INTEGER NOT NULL DEFAULT 30,
  fat_pct          INTEGER NOT NULL DEFAULT 30,
  weight_goal_kg   REAL,
  updated_at       TEXT NOT NULL
);
```

---

## 6. Analytics events

| Event | Trigger | Properties |
|---|---|---|
| `dashboard_viewed` | DashboardScreen entered | `{ date }` |
| `move_card_tapped` | Move card or + button tapped | — |
| `calories_card_tapped` | Calories card tapped | — |
| `track_workout_tapped` | "Start tracking" or card tapped | — |
| `weekly_habits_tapped` | "Quick check in" or card tapped | — |
| `recovery_card_tapped` | Recovery Score card `›` tapped | `{ score }` |
| `workout_streak_tapped` | Workout Streak card `›` tapped | `{ streak }` |
| `fab_tapped` | `+` FAB tapped | — |
| `dashboard_refreshed` | Pull-to-refresh triggered | — |
| `move_detail_viewed` | MoveDetailScreen entered | `{ date }` |
| `move_day_selected` | Weekly strip day tapped | `{ day_of_week }` |
| `weekly_habits_viewed` | WeeklyHabitsScreen entered | `{ week_number }` |
| `weekly_habits_submitted` | Submit tapped successfully | `{ q3_continue, has_notes }` |
| `weekly_habits_abandoned` | Back tapped without submitting | `{ had_any_input }` |

---

## 7. Permissions required

| Permission | Used for | When requested |
|---|---|---|
| `ACTIVITY_RECOGNITION` | Step counter fallback | Dashboard first load if Health Connect unavailable |
| `health.READ_STEPS` | Health Connect steps | Onboarding permissions screen (C8) |
| `health.READ_ACTIVE_CALORIES_BURNED` | Move ring data | Onboarding permissions screen (C8) |
| `health.READ_EXERCISE` | Workout calorie sync | Onboarding permissions screen (C8) |

If permissions are denied: Move card shows `0/200 KCAL` with a subtle "Grant access" link that re-triggers the permission request. Recovery score uses 0 for step contribution.

---

## 8. Definition of done — dashboard feature

- [ ] `DashboardScreen` renders pixel-faithful to both dark and light mockups
- [ ] All 6 data sources load simultaneously and populate their cards
- [ ] Greeting changes correctly based on time of day
- [ ] Move ring animates from 0 to value on first composition
- [ ] Calories remaining formula correct: `goal - food + exercise`
- [ ] Remaining calorie color changes at <200 (amber) and ≤0 (red)
- [ ] Heatmap renders correct 5-level intensity colors
- [ ] Current streak and longest streak calculate correctly across month boundaries
- [ ] Weekly Habits card hides after check-in (same session)
- [ ] Weekly Habits card shows correct copy variant
- [ ] Skeleton shimmer shows while loading
- [ ] Pull-to-refresh works and re-fetches all data
- [ ] Daily reset triggers automatically at midnight
- [ ] Bottom nav tabs navigate to correct destinations
- [ ] FAB taps to QuickAddSheet
- [ ] `MoveDetailScreen` renders pixel-faithful to both dark and light mockups
- [ ] Weekly ring strip shows correct fill for each past day
- [ ] Tapping a week day updates the main card to show that day's data
- [ ] Step distance formula uses correct stride constant per sex
- [ ] `WeeklyHabitsScreen` renders pixel-faithful to both dark and light mockups
- [ ] All 3 dropdowns and radio group function correctly
- [ ] Submit disabled until all 3 required questions answered
- [ ] Submit inserts row to `weekly_habit_checkins` in Supabase
- [ ] Confetti animation plays on successful submit
- [ ] Dashboard hides Weekly Habits card after successful check-in
- [ ] All analytics events fire at correct moments
- [ ] Health Connect gracefully degrades to sensor fallback if unavailable
- [ ] All `contentDescription`s set on icon-only elements

---

_End of PRD-dashboard.md v1.0_
