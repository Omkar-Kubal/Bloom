package com.appylab.bloom.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appylab.bloom.core.data.UserProfileDataStore
import com.appylab.bloom.core.domain.ActivityLevel
import com.appylab.bloom.core.domain.Gender
import com.appylab.bloom.core.domain.PrimaryGoal
import com.appylab.bloom.core.domain.calculateTdee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStore: UserProfileDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun updateUsername(username: String) { _state.value = _state.value.copy(username = username) }
    
    fun toggleGoal(goal: String) {
        val current = _state.value.goals.toMutableList()
        if (current.contains(goal)) current.remove(goal) else current.add(goal)
        _state.value = _state.value.copy(goals = current)
    }
    
    fun updateHabits(habits: String) { _state.value = _state.value.copy(habits = habits) }
    fun updateActivityLevel(level: String) { _state.value = _state.value.copy(activityLevel = level) }
    fun updateAboutYou(gender: String, age: Int, country: String, zipCode: String) {
        _state.value = _state.value.copy(gender = gender, age = age, country = country, zipCode = zipCode)
    }
    fun updateBodyStats(heightCm: Float, weightKg: Float, goalWeightKg: Float, heightUnit: String, weightUnit: String) {
        _state.value = _state.value.copy(heightCm = heightCm, weightKg = weightKg, goalWeightKg = goalWeightKg, heightUnit = heightUnit, weightUnit = weightUnit)
        calculateAndSetTdee()
    }
    fun updateWeeklyGoal(weeklyGoal: String) {
        _state.value = _state.value.copy(weeklyGoal = weeklyGoal)
        calculateAndSetTdee()
    }
    fun updateMealFrequency(freq: String) { _state.value = _state.value.copy(mealFrequency = freq) }
    fun updateRunFrequency(freq: String) { _state.value = _state.value.copy(runFrequency = freq) }
    
    fun calculateAndSetTdee() {
        val s = _state.value
        val genderEnum = if (s.gender.equals("Female", true)) Gender.Female else Gender.Male
        val activityEnum = when (s.activityLevel) {
            "NOT" -> ActivityLevel.Sedentary
            "LIGHT" -> ActivityLevel.LightlyActive
            "ACTIVE" -> ActivityLevel.Active
            "VERY_ACTIVE" -> ActivityLevel.VeryActive
            else -> ActivityLevel.Sedentary
        }
        val goalEnum = when (s.weeklyGoal) {
            "Gain 0.5kg" -> PrimaryGoal.Gain
            "Lose 0.5kg" -> PrimaryGoal.Lose
            else -> PrimaryGoal.Maintain
        }
        
        val tdee = calculateTdee(
            gender = genderEnum,
            weightKg = s.weightKg,
            heightCm = s.heightCm,
            age = if (s.age > 0) s.age else 25,
            activityLevel = activityEnum,
            goal = goalEnum
        )
        _state.value = _state.value.copy(tdee = tdee)
    }

    fun finishOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            val s = _state.value
            calculateAndSetTdee()
            val latest = _state.value
            dataStore.savePreference(UserProfileDataStore.Keys.USERNAME, s.username)
            dataStore.savePreference(UserProfileDataStore.Keys.GOALS, Json.encodeToString(latest.goals))
            dataStore.savePreference(UserProfileDataStore.Keys.HABITS, s.habits)
            dataStore.savePreference(UserProfileDataStore.Keys.ACTIVITY_LEVEL, s.activityLevel)
            dataStore.savePreference(UserProfileDataStore.Keys.GENDER, s.gender)
            dataStore.savePreference(UserProfileDataStore.Keys.AGE, s.age)
            dataStore.savePreference(UserProfileDataStore.Keys.COUNTRY, s.country)
            dataStore.savePreference(UserProfileDataStore.Keys.ZIP_CODE, s.zipCode)
            dataStore.savePreference(UserProfileDataStore.Keys.HEIGHT_CM, s.heightCm)
            dataStore.savePreference(UserProfileDataStore.Keys.WEIGHT_KG, s.weightKg)
            dataStore.savePreference(UserProfileDataStore.Keys.GOAL_WEIGHT_KG, s.goalWeightKg)
            dataStore.savePreference(UserProfileDataStore.Keys.HEIGHT_UNIT, s.heightUnit)
            dataStore.savePreference(UserProfileDataStore.Keys.WEIGHT_UNIT, s.weightUnit)
            dataStore.savePreference(UserProfileDataStore.Keys.WEEKLY_GOAL, s.weeklyGoal)
            dataStore.savePreference(UserProfileDataStore.Keys.MEAL_FREQUENCY, s.mealFrequency)
            dataStore.savePreference(UserProfileDataStore.Keys.RUN_FREQUENCY, s.runFrequency)
            dataStore.savePreference(UserProfileDataStore.Keys.DAILY_CALORIE_GOAL, latest.tdee)
            
            dataStore.savePreference(UserProfileDataStore.Keys.ONBOARDING_COMPLETE, true)
            onComplete()
        }
    }
}
