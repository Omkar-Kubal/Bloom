package com.appylab.bloom.core.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class FitnessCalculatorsTest {
    @Test
    fun calculateTdee_usesMifflinStJeorAndGoalAdjustment() {
        val tdee = calculateTdee(
            gender = Gender.Male,
            weightKg = 80f,
            heightCm = 180f,
            age = 30,
            activityLevel = ActivityLevel.Active,
            goal = PrimaryGoal.Lose
        )

        assertEquals(2359, tdee)
    }

    @Test
    fun calculateStepCalories_usesPrdFormula() {
        val result = calculateStepCalories(
            steps = 8000,
            heightCm = 180f,
            weightKg = 80f
        )

        assertEquals(0.7452f, result.strideLengthMeters, 0.0001f)
        assertEquals(5.9616f, result.distanceKm, 0.0001f)
        assertEquals(494, result.caloriesBurnt)
    }

    @Test
    fun calculateAdjustedGoal_addsStepCalories() {
        val adjustedGoal = calculateAdjustedCalorieGoal(
            baseDailyGoal = 2000,
            stepCaloriesBurnt = 230
        )

        assertEquals(2230, adjustedGoal)
    }

    @Test
    fun progressFraction_isClamped() {
        assertEquals(1f, progressFraction(current = 2500, target = 2000), 0.0f)
        assertEquals(0f, progressFraction(current = 10, target = 0), 0.0f)
    }
}
