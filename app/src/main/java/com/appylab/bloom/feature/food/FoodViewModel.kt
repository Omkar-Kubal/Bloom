package com.appylab.bloom.feature.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appylab.bloom.core.data.AuthRepository
import com.appylab.bloom.core.data.FoodRepository
import com.appylab.bloom.core.data.UserProfileDataStore
import com.appylab.bloom.core.data.db.entities.FoodEntry
import com.appylab.bloom.core.domain.model.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    authRepository: AuthRepository,
    private val repository: FoodRepository,
    dataStore: UserProfileDataStore
) : ViewModel() {

    private val currentUserId = authRepository.getUserId()
    private val todayStr = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

    val dayLog: StateFlow<List<FoodEntry>> = repository.getDayLog(currentUserId, todayStr)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyCalorieGoal: StateFlow<Int> = dataStore.data
        .map { prefs -> prefs[UserProfileDataStore.Keys.DAILY_CALORIE_GOAL] ?: 2000 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 2000)

    private val _searchResults = MutableStateFlow<List<FoodItem>>(emptyList())
    val searchResults: StateFlow<List<FoodItem>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    fun searchFood(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        viewModelScope.launch {
            _isSearching.value = true
            val results = repository.searchFood(query)
            _searchResults.value = results
            _isSearching.value = false
        }
    }

    fun getFoodByBarcode(barcode: String, onResult: (FoodItem?) -> Unit) {
        viewModelScope.launch {
            val item = repository.getFoodByBarcode(barcode)
            onResult(item)
        }
    }

    fun logFood(foodItem: FoodItem, grams: Float, mealType: String) {
        viewModelScope.launch {
            val ratio = grams / 100f
            val entry = FoodEntry(
                userId = currentUserId,
                date = todayStr,
                mealType = mealType,
                foodName = foodItem.name,
                brand = foodItem.brand,
                grams = grams,
                quantity = null,
                calories = (foodItem.caloriesPer100g * ratio).toInt(),
                carbs = foodItem.carbsPer100g * ratio,
                fat = foodItem.fatPer100g * ratio,
                protein = foodItem.proteinPer100g * ratio,
                barcode = foodItem.barcode,
                source = "OpenFoodFacts",
                loggedAt = System.currentTimeMillis()
            )
            repository.logFood(entry)
        }
    }

    fun logManualFood(entry: FoodEntry) {
        viewModelScope.launch {
            repository.logFood(entry.copy(userId = currentUserId, date = todayStr, loggedAt = System.currentTimeMillis()))
        }
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch {
            repository.deleteFoodEntry(id)
        }
    }
}
