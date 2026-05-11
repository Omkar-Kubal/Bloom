package com.appylab.bloom.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appylab.bloom.core.data.FoodRepository
import com.appylab.bloom.core.data.StepRepository
import com.appylab.bloom.core.data.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    authRepository: com.appylab.bloom.core.data.AuthRepository,
    foodRepository: FoodRepository,
    stepRepository: StepRepository,
    workoutRepository: WorkoutRepository
) : ViewModel() {

    private val userId = authRepository.getUserId()
    private val todayStr = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

    val uiState: StateFlow<DashboardUiState> = combine(
        foodRepository.getDayLog(userId, todayStr),
        stepRepository.getTodayStepsFlow(userId),
        workoutRepository.getSessionHistory(userId),
        foodRepository.getLast30DaysWeight(userId)
    ) { foodLog, stepLog, workouts, weights ->
        
        val consumedCalories = foodLog.sumOf { it.calories.toDouble() }.toInt()
        val consumedCarbs = foodLog.sumOf { it.carbs.toDouble() }.toInt()
        val consumedFat = foodLog.sumOf { it.fat.toDouble() }.toInt()
        val consumedProtein = foodLog.sumOf { it.protein.toDouble() }.toInt()

        val steps = stepLog?.stepCount ?: 0
        val distKm = stepLog?.distanceKm ?: 0f
        val stepsCal = stepLog?.calorieBurn ?: 0
        
        val baseGoal = 2000
        val adjustedGoal = baseGoal + stepsCal
        val remaining = adjustedGoal - consumedCalories
        
        val todayWorkout = workouts.find { 
            val date = java.time.Instant.ofEpochMilli(it.startTime).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
            date.format(DateTimeFormatter.ISO_LOCAL_DATE) == todayStr
        }
        
        val workoutSummary = todayWorkout?.let {
            val duration = if (it.endTime != null) ((it.endTime - it.startTime) / 60000).toInt() else 0
            TodayWorkoutSummary(
                title = "Your Workout\nRoutine",
                durationMinutes = duration,
                caloriesBurnt = it.estimatedCalories ?: 0,
                completedAtLabel = if (it.endTime != null) "Completed" else "In Progress"
            )
        }
        
        val weightVals = weights.takeLast(14).map { it.weightKg }
        val startWeight = weights.firstOrNull()?.weightKg?.toInt() ?: 85
        val currentWeight = weights.lastOrNull()?.weightKg?.toInt() ?: 76
        
        DashboardUiState(
            displayName = "Omkar",
            greetingLine = "Good Morning,",
            motivationalLine = "Ready to rock?",
            dates = generateDates(),
            calories = CalorieSummary(
                consumed = consumedCalories,
                baseGoal = baseGoal,
                adjustedGoal = adjustedGoal,
                remaining = remaining.coerceAtLeast(0),
                progress = if (adjustedGoal > 0) (consumedCalories.toFloat() / adjustedGoal).coerceIn(0f, 1f) else 0f
            ),
            macros = MacroSummary(
                carbs = MacroProgress("Carbs", consumedCarbs, 250, (consumedCarbs.toFloat()/250).coerceIn(0f,1f)),
                fat = MacroProgress("Fat", consumedFat, 65, (consumedFat.toFloat()/65).coerceIn(0f,1f)),
                protein = MacroProgress("Protein", consumedProtein, 150, (consumedProtein.toFloat()/150).coerceIn(0f,1f))
            ),
            steps = StepSummary(
                steps = steps,
                goal = 8000,
                distanceKm = distKm,
                caloriesBurnt = stepsCal,
                progress = (steps.toFloat() / 8000).coerceIn(0f, 1f)
            ),
            workout = workoutSummary,
            weightTrend = WeightTrendSummary(
                values = if (weightVals.isEmpty()) listOf(75f, 75f) else weightVals,
                startWeightKg = startWeight,
                currentWeightKg = currentWeight,
                targetWeightKg = 70
            ),
            netCalorieNote = "+$stepsCal kcal added from steps - adjusted goal: ${"%,d".format(adjustedGoal)} kcal"
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Preview
    )
    
    private fun generateDates(): List<DateChipUi> {
        val today = LocalDate.now()
        return (-3..3).map { offset ->
            val date = today.plusDays(offset.toLong())
            DateChipUi(
                number = date.dayOfMonth.toString(),
                weekday = date.dayOfWeek.name.take(3),
                isSelected = offset == 0
            )
        }
    }
}
