package com.appylab.bloom.feature.dashboard

data class DashboardUiState(
    val displayName: String,
    val greetingLine: String,
    val motivationalLine: String,
    val dates: List<DateChipUi>,
    val calories: CalorieSummary,
    val macros: MacroSummary,
    val steps: StepSummary,
    val workout: TodayWorkoutSummary?,
    val weightTrend: WeightTrendSummary,
    val netCalorieNote: String
) {
    companion object {
        val Preview = DashboardUiState(
            displayName = "Omkar",
            greetingLine = "Good Morning,",
            motivationalLine = "Ready to rock?",
            dates = listOf(
                DateChipUi("17", "SAT", false),
                DateChipUi("18", "SUN", false),
                DateChipUi("19", "MON", false),
                DateChipUi("20", "TUE", true),
                DateChipUi("21", "WED", false),
                DateChipUi("22", "THU", false),
                DateChipUi("23", "FRI", false)
            ),
            calories = CalorieSummary(
                consumed = 1690,
                baseGoal = 2000,
                adjustedGoal = 2230,
                remaining = 540,
                progress = 0.76f
            ),
            macros = MacroSummary(
                carbs = MacroProgress("Carbs", 186, 250, 0.74f),
                fat = MacroProgress("Fat", 48, 65, 0.73f),
                protein = MacroProgress("Protein", 118, 150, 0.78f)
            ),
            steps = StepSummary(
                steps = 7528,
                goal = 8000,
                distanceKm = 4.06f,
                caloriesBurnt = 250,
                progress = 0.94f
            ),
            workout = TodayWorkoutSummary(
                title = "Your Workout\nRoutine",
                durationMinutes = 118,
                caloriesBurnt = 310,
                completedAtLabel = "Today's Plan"
            ),
            weightTrend = WeightTrendSummary(
                values = listOf(28f, 61f, 28f, 100f, 75f, 75f, 48f, 95f, 56f, 69f, 28f, 70f, 75f),
                startWeightKg = 85,
                currentWeightKg = 76,
                targetWeightKg = 70
            ),
            netCalorieNote = "+230 kcal added from steps - adjusted goal: 2,230 kcal"
        )
    }
}

data class DateChipUi(
    val number: String,
    val weekday: String,
    val isSelected: Boolean
)

data class CalorieSummary(
    val consumed: Int,
    val baseGoal: Int,
    val adjustedGoal: Int,
    val remaining: Int,
    val progress: Float
)

data class MacroSummary(
    val carbs: MacroProgress,
    val fat: MacroProgress,
    val protein: MacroProgress
) {
    val items: List<MacroProgress> = listOf(carbs, fat, protein)
}

data class MacroProgress(
    val label: String,
    val consumedGrams: Int,
    val targetGrams: Int,
    val progress: Float,
    val isOverGoal: Boolean = consumedGrams > targetGrams
)

data class StepSummary(
    val steps: Int,
    val goal: Int,
    val distanceKm: Float,
    val caloriesBurnt: Int,
    val progress: Float
)

data class TodayWorkoutSummary(
    val title: String,
    val durationMinutes: Int,
    val caloriesBurnt: Int,
    val completedAtLabel: String
)

data class WeightTrendSummary(
    val values: List<Float>,
    val startWeightKg: Int,
    val currentWeightKg: Int,
    val targetWeightKg: Int
)
