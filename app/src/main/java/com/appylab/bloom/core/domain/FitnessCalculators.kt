package com.appylab.bloom.core.domain

import kotlin.math.roundToInt

enum class Gender {
    Male,
    Female
}

enum class ActivityLevel(val multiplier: Float) {
    Sedentary(1.2f),
    LightlyActive(1.375f),
    Active(1.55f),
    VeryActive(1.725f)
}

enum class PrimaryGoal(val calorieAdjustment: Int) {
    Lose(-400),
    Maintain(0),
    Gain(300)
}

data class StepCaloriesResult(
    val strideLengthMeters: Float,
    val distanceKm: Float,
    val caloriesBurnt: Int
)

fun calculateTdee(
    gender: Gender,
    weightKg: Float,
    heightCm: Float,
    age: Int,
    activityLevel: ActivityLevel,
    goal: PrimaryGoal
): Int {
    val bmr = when (gender) {
        Gender.Male -> (10f * weightKg) + (6.25f * heightCm) - (5f * age) + 5f
        Gender.Female -> (10f * weightKg) + (6.25f * heightCm) - (5f * age) - 161f
    }
    return (bmr * activityLevel.multiplier + goal.calorieAdjustment).roundToInt()
}

fun calculateStepCalories(
    steps: Int,
    heightCm: Float,
    weightKg: Float
): StepCaloriesResult {
    val strideLengthMeters = heightCm * 0.414f / 100f
    val distanceKm = steps * strideLengthMeters / 1000f
    val caloriesBurnt = (distanceKm * weightKg * 1.036f).roundToInt()
    return StepCaloriesResult(
        strideLengthMeters = strideLengthMeters,
        distanceKm = distanceKm,
        caloriesBurnt = caloriesBurnt
    )
}

fun calculateAdjustedCalorieGoal(
    baseDailyGoal: Int,
    stepCaloriesBurnt: Int
): Int = baseDailyGoal + stepCaloriesBurnt

fun calculateRemainingCalories(
    consumedCalories: Int,
    adjustedGoal: Int
): Int = adjustedGoal - consumedCalories

fun progressFraction(
    current: Int,
    target: Int
): Float {
    if (target <= 0) return 0f
    return (current.toFloat() / target.toFloat()).coerceIn(0f, 1f)
}
