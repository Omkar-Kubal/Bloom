package com.appylab.bloom.feature.onboarding

data class OnboardingState(
    val username: String = "",
    val goals: List<String> = emptyList(),
    val habits: String = "",
    val activityLevel: String = "",
    val gender: String = "",
    val age: Int = 0,
    val country: String = "",
    val zipCode: String = "",
    val heightCm: Float = 0f,
    val weightKg: Float = 0f,
    val goalWeightKg: Float = 0f,
    val heightUnit: String = "cm",
    val weightUnit: String = "kg",
    val weeklyGoal: String = "",
    val mealFrequency: String = "",
    val runFrequency: String = "",
    val tdee: Int = 0
)
