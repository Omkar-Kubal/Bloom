# Bloom — MVP Product Requirements Document
**Version:** 1.0 | **Status:** Draft | **Date:** May 2026  
**Platform:** Android (Native) | **Stack:** Jetpack Compose + Material 3  
**Scope:** MVP only — shippable, stable, monetization-ready v1.0

---

## 1. MVP Definition

MVP = a fully functional Android app covering 3 core modules:
1. Food Tracker (log, scan, manual)
2. Workout Tracker + AI Summary
3. Step Tracker

Supported by: Splash → Onboarding → Dashboard → Profile flow.  
Target timeline: **8–10 weeks**, solo or small team.

---

## 2. Project Type

| Attribute | Decision |
|---|---|
| Language | Kotlin 2.x |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture — MVVM |
| Modules | `:app` `:feature:food` `:feature:workout` `:feature:steps` `:core:ui` `:core:data` `:core:network` |
| Local DB | Room + DataStore |
| Networking | Retrofit + OkHttp + Kotlin Serialization |
| DI | Hilt |
| Navigation | Compose Navigation (single-activity) |
| AI | Gemini 1.5 Flash |
| Auth + Sync | Firebase Auth + Firestore |
| Analytics | Firebase Analytics + Crashlytics |
| Charts | Vico (Compose-native) |
| Camera | CameraX + ML Kit |
| Image Loading | Coil 3 |

---

## 3. MVP Screen List

| # | Screen | Module |
|---|---|---|
| 1 | Splash | Core |
| 2–12 | Onboarding (11 screens) | Core |
| 13 | Main Dashboard | Core |
| 14 | Food Log — Day View | Food |
| 15 | Food Search | Food |
| 16 | Barcode Scanner | Food |
| 17 | Manual Food Entry | Food |
| 18 | Calorie + Macro Summary | Food |
| 19 | Weight Log | Food |
| 20 | Workout Session | Workout |
| 21 | Exercise Search / Add | Workout |
| 22 | Active Set Logger | Workout |
| 23 | AI Workout Summary | Workout |
| 24 | Step Tracker | Steps |
| 25 | Profile / Settings | Core |

**Total: 25 screens**

---

## 4. Onboarding (11 Screens)

| Screen # | Content | Data Key |
|---|---|---|
| 1 | Welcome / Brand | — |
| 2 | Name | `displayName` |
| 3 | Age | `age` |
| 4 | Gender | `gender` |
| 5 | Current Weight | `weightKg` |
| 6 | Height | `heightCm` |
| 7 | Goal Weight | `goalWeightKg` |
| 8 | Activity Level | `activityLevel` (sedentary / lightly active / active / very active) |
| 9 | Primary Goal | `goal` (lose / maintain / gain) |
| 10 | Diet Preference | `dietType` (no restriction / veg / vegan / keto) |
| 11 | Calorie Goal | `dailyCalorieGoal` (TDEE auto-calc + manual override) |

**TDEE Calculation (on-device, no API):**  
Mifflin-St Jeor equation:
```
Male:   BMR = (10 × kg) + (6.25 × cm) − (5 × age) + 5
Female: BMR = (10 × kg) + (6.25 × cm) − (5 × age) − 161
TDEE = BMR × activity multiplier (1.2 / 1.375 / 1.55 / 1.725)
```
User sees auto-calculated goal with option to override manually.

All onboarding data stored in DataStore. Synced to Firestore on auth completion.

---

## 5. Main Dashboard

Single scrollable screen. Bottom nav: **Food | Workout | Steps | Profile**

**Dashboard Cards (top → bottom):**
1. Greeting + date
2. Calorie ring — consumed / goal / remaining
3. Macro bar row — Carbs / Fat / Protein (grams: consumed vs target)
4. Step count card — steps today / goal, calories from steps
5. Today's workout card — last session summary or "Log Workout" CTA
6. Weight trend mini-chart (last 7 days)
7. Net calorie note — "Goal adjusted +230 kcal from steps"

---

## 6. Food Tracker — MVP

### 6.1 Data Sources
| Source | Use | Cost |
|---|---|---|
| Open Food Facts API | Branded/packaged goods, barcode lookup | Free, no key |
| USDA FoodData Central | Raw ingredients, generic foods | Free, API key required |
| Room (local cache) | All logged foods, recent searches | On-device |

Cache-first strategy: check Room before any API call. Store every API result locally.

### 6.2 Features in MVP

**F1 — Search + Log:**
```
User types food name
→ Debounce 400ms → query Open Food Facts
→ Results list: name, brand, kcal/100g
→ User selects → sheet opens: enter grams + quantity
→ Cal = (grams / 100) × kcal_per_100g  [same for Carbs, Fat, Protein]
→ Saved to Room FoodEntry table
→ Daily macro totals refreshed
```

**F2 — Barcode Scanner:**
```
CameraX preview + ML Kit barcode scanning
→ Decoded EAN/UPC → Open Food Facts barcode endpoint
→ Found: pre-fill nutrition bottom sheet → user confirms grams
→ Not found: redirect to Manual Entry with name pre-filled
```

**F4 — Manual Entry:**
```
User enters: Food name, Calories, Carbs, Fat, Protein (per 100g or absolute)
→ Saved to Room as custom food
→ Available in search results for future logs
```

**F5 — Calorie Goal:**
- Set during onboarding (TDEE-based or manual)
- Editable in Profile/Settings anytime
- Step calories added to goal dynamically (see §8.3)

**F6 — Macro Display:**
- 4 values shown: Calories, Carbs, Fat, Protein — nothing else
- Calorie ring (consumed / goal)
- 3 horizontal progress bars for macros
- Color-coded: over goal = red, under = green/neutral

**F7 — Weight Log:**
- Daily entry: input weight in kg or lbs (unit preference in Settings)
- Vico line chart: 30-day and 90-day view toggle
- Goal weight shown as dashed horizontal line on chart

**F8 — Meal Categorisation:**
- Each food entry tagged: Breakfast / Lunch / Dinner / Snack
- Day view groups entries by meal with subtotals

### 6.3 Food Data Model (Room)
```kotlin
@Entity
data class FoodEntry(
    @PrimaryKey val id: String,          // UUID
    val userId: String,
    val date: LocalDate,
    val mealType: MealType,              // BREAKFAST/LUNCH/DINNER/SNACK
    val foodName: String,
    val brand: String?,
    val grams: Float,
    val quantity: Float,
    val calories: Float,
    val carbs: Float,
    val fat: Float,
    val protein: Float,
    val barcode: String?,
    val source: FoodSource,              // SEARCH/BARCODE/MANUAL
    val loggedAt: Instant
)
```

---

## 7. Workout Tracker — MVP

### 7.1 Features in MVP

**W1 — Log Exercise:**
- User types exercise name → searches local bundled JSON (200 exercises)
- Selects exercise → adds to session
- Each exercise: name, muscle group, MET value (pre-loaded)

**W2 — Sets + Reps Logger:**
- Per exercise: add sets
- Each set: reps input + weight (kg/lbs) input
- Set marked complete via checkbox / swipe

**W3 — Timers:**
- **Rest timer:** countdown between sets (user sets duration: 30s / 60s / 90s / 2min / custom)
- **TUT timer:** per-set stopwatch. Tap "Start" on first rep, "Done" on last rep of set. Stored per set.

**W4 — AI Workout Summary (Gemini):**

Triggered automatically on "Finish Workout."

Gemini prompt (structured):
```
User stats: weight={kg}, gender={gender}
Session: {exercise_name}, {sets} sets, {reps} reps avg, {tut_seconds}s TUT avg, {duration_minutes} min total
Task: Return JSON with keys:
  - estimated_calories_burnt (int)
  - primary_muscles (string[])
  - secondary_muscles (string[])
  - intensity_rating (1-10)
  - recovery_tip (string, max 2 sentences)
  - next_session_suggestion (string, max 2 sentences)
```

Summary screen displays all 6 fields. Cached in Room — viewable in session history.

### 7.2 Local Exercise JSON (Bundled)
```json
[
  {
    "id": "bench_press",
    "name": "Bench Press",
    "muscleGroup": "Chest",
    "primaryMuscles": ["Pectoralis Major"],
    "secondaryMuscles": ["Triceps", "Anterior Deltoid"],
    "met": 5.0,
    "equipment": "Barbell"
  }
  // ... 199 more
]
```
Source: Compendium of Physical Activities (free, published by Arizona State University). 800+ activities — subset of 200 gym exercises bundled for MVP.

### 7.3 Calories Burnt Formula
```
Calories = MET × weightKg × durationHours
```
Calculated locally. Gemini may return a refined estimate based on intensity — app displays Gemini's value if available, formula fallback if API fails.

### 7.4 Workout Data Model (Room)
```kotlin
@Entity data class WorkoutSession(
    @PrimaryKey val id: String,
    val userId: String,
    val startTime: Instant,
    val endTime: Instant?,
    val estimatedCalories: Float?,
    val aiSummaryJson: String?           // Cached Gemini response
)

@Entity data class ExerciseLog(
    @PrimaryKey val id: String,
    val sessionId: String,
    val exerciseName: String,
    val muscleGroup: String,
    val met: Float
)

@Entity data class SetLog(
    @PrimaryKey val id: String,
    val exerciseLogId: String,
    val setNumber: Int,
    val reps: Int,
    val weightKg: Float,
    val tutSeconds: Int,
    val restSeconds: Int
)
```

---

## 8. Step Tracker — MVP

### 8.1 Sensor
- `Sensor.TYPE_STEP_COUNTER` — hardware pedometer, always-on, battery efficient
- Foreground service with persistent low-priority notification
- Daily reset at midnight via WorkManager scheduled job
- Fallback: if sensor unavailable, show "Step tracking not supported on this device" with manual entry option

### 8.2 Step Goal
- Default: 8,000 steps/day
- User-adjustable in Settings
- Circular progress ring on Steps screen + Dashboard card

### 8.3 Calorie Calculation from Steps
```
Stride length (m) = heightCm × 0.414 / 100
Distance (km)     = steps × strideLengthM / 1000
Calories burnt    = distance × weightKg × 1.036
```
Uses `heightCm` and `weightKg` from onboarding. Recalculates if user updates these in Profile.

**Dashboard integration:**
```
Net Calorie Goal = Base Daily Goal + Step Calories Burnt
```
Displayed as: *"🔥 +230 kcal added from steps — adjusted goal: 2,230 kcal"*

### 8.4 Steps Data Model (Room)
```kotlin
@Entity data class DailySteps(
    @PrimaryKey val date: LocalDate,
    val userId: String,
    val steps: Int,
    val caloriesBurnt: Float,
    val goal: Int
)
```

---

## 9. Profile / Settings Screen

| Setting | Options |
|---|---|
| Display name | Editable text |
| Profile photo | Camera / Gallery pick |
| Weight unit | kg / lbs |
| Height unit | cm / ft-in |
| Daily calorie goal | Numeric input (overrides TDEE calc) |
| Daily step goal | Numeric input |
| Rest timer default | 30s / 60s / 90s / 2min |
| Notifications | Step reminders, workout reminders (toggle) |
| Account | Sign out, delete account |

---

## 10. Authentication

- Firebase Auth: Email + Password, Google Sign-In
- Onboarding data written to Firestore on first auth
- All Room data scoped to `userId` — supports multi-user on same device
- Firestore sync: food logs, workout sessions, weight entries synced on write (online) and queued for sync (offline via Firestore offline persistence)

---

## 11. MVP Non-Goals (Explicitly Excluded)

These are **not** in MVP. Any request to add these mid-MVP should be deferred:

| Feature | Phase |
|---|---|
| Voice food logging (F3) | Phase 2 |
| Diet plans | Phase 2 |
| Workout plans / videos | Phase 2 |
| Running tracker | Phase 2 |
| Run Club / community | Phase 3 |
| Social profiles / leaderboards | Phase 3 |
| Google Fit / Health Connect integration | Phase 2 |
| Apple Health (N/A — Android only) | — |
| In-app subscriptions / paywall | Phase 2 |
| Dark mode (can be included if low effort) | Phase 2 |
| Tablet / foldable layout | Phase 2 |

---

## 12. MVP Acceptance Criteria

App is MVP-complete when all of the following pass:

**Onboarding**
- [ ] All 11 screens navigable, data persists to DataStore + Firestore
- [ ] TDEE auto-calculates correctly and is overridable

**Food**
- [ ] Search returns results from Open Food Facts within 2s
- [ ] Barcode scan resolves product in < 3s
- [ ] Manual entry saves and appears in day log
- [ ] Calorie ring and macro bars update in real time on log
- [ ] Weight log saves, chart renders 30-day view

**Workout**
- [ ] Session created, exercises added, sets logged
- [ ] TUT timer starts/stops per set, value stored
- [ ] Rest timer counts down, alerts user
- [ ] AI summary generated post-session (with graceful offline fallback)
- [ ] Calories estimated displayed on summary

**Steps**
- [ ] Steps counting live on physical device
- [ ] Daily step goal progress visible on dashboard
- [ ] Calories from steps calculated and added to net calorie goal
- [ ] Resets at midnight

**General**
- [ ] Auth: sign up, sign in, sign out functional
- [ ] App does not crash on any happy path flow
- [ ] Cold start < 2 seconds on mid-range device (Snapdragon 6xx)
- [ ] Offline: food log and workout log functional without internet

---

## 13. MVP Build Order (Recommended)

| Week | Deliverable |
|---|---|
| 1 | Project setup, multi-module scaffold, Hilt, Navigation, Theme (Material 3 seed color) |
| 2 | Splash + Onboarding (all 11 screens, DataStore, TDEE calc) |
| 3 | Firebase Auth, Firestore setup, profile persistence |
| 4 | Dashboard shell + bottom nav |
| 5 | Food — Search (Open Food Facts), log flow, Room schema |
| 6 | Food — Barcode scanner (CameraX + ML Kit), manual entry, macro display |
| 7 | Weight log + Vico chart |
| 8 | Workout — session screen, exercise picker, set/rep logger, timers |
| 9 | Workout — Gemini AI summary integration |
| 10 | Steps — foreground service, sensor, calorie calc, dashboard integration |
| +1 | Bug fixes, performance, acceptance criteria review |

---

## 14. Risk Register — MVP Scope

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| Open Food Facts API downtime | Low | High | USDA fallback + Room cache |
| Gemini API errors / timeout | Medium | Low | Graceful fallback — show formula-based calories, no AI text |
| `TYPE_STEP_COUNTER` missing on device | Low | Medium | Detect on launch, hide Steps tab with message |
| CameraX barcode scan slow on low-end | Medium | Medium | Show manual entry option immediately alongside scanner |
| Firestore sync conflicts | Low | Low | Last-write-wins for MVP; conflict resolution in Phase 2 |
| Cold start performance | Medium | Medium | Baseline profiles, R8 full mode, lazy Hilt injection |

---

*End of MVP PRD v1.0 — Bloom Fitness App*
