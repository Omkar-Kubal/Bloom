package com.appylab.bloom.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

@Singleton
class UserProfileDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    object Keys {
        val USERNAME = stringPreferencesKey("username")
        val GOALS = stringPreferencesKey("goals") // JSON array
        val HABITS = stringPreferencesKey("habits")
        val ACTIVITY_LEVEL = stringPreferencesKey("activity_level") // NOT/LIGHT/ACTIVE/VERY_ACTIVE
        val GENDER = stringPreferencesKey("gender")
        val AGE = intPreferencesKey("age")
        val COUNTRY = stringPreferencesKey("country")
        val ZIP_CODE = stringPreferencesKey("zip_code")
        val HEIGHT_CM = floatPreferencesKey("height_cm")
        val WEIGHT_KG = floatPreferencesKey("weight_kg")
        val GOAL_WEIGHT_KG = floatPreferencesKey("goal_weight_kg")
        val HEIGHT_UNIT = stringPreferencesKey("height_unit") // "cm" / "ftin"
        val WEIGHT_UNIT = stringPreferencesKey("weight_unit") // "kg" / "lbs"
        val WEEKLY_GOAL = stringPreferencesKey("weekly_goal") // "gain" / "lose"
        val MEAL_FREQUENCY = stringPreferencesKey("meal_frequency") // NEVER/RARELY/OCCASIONALLY/FREQUENTLY/ALWAYS
        val RUN_FREQUENCY = stringPreferencesKey("run_frequency")
        val DAILY_CALORIE_GOAL = intPreferencesKey("daily_calorie_goal")
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
        val SUBSCRIPTION_STATUS = stringPreferencesKey("subscription_status") // "trial"/"pro"/"free"
        val TRIAL_START_DATE = longPreferencesKey("trial_start_date")
    }

    val data: Flow<Preferences> = dataStore.data

    suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}
