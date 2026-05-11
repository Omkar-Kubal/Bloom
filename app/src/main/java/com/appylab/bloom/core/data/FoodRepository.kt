package com.appylab.bloom.core.data

import com.appylab.bloom.core.data.db.daos.FoodEntryDao
import com.appylab.bloom.core.data.db.daos.WeightEntryDao
import com.appylab.bloom.core.data.db.entities.FoodEntry
import com.appylab.bloom.core.data.db.entities.WeightEntry
import com.appylab.bloom.core.domain.model.FoodItem
import com.appylab.bloom.core.network.OpenFoodFactsApi
import com.appylab.bloom.core.network.dto.OffProductDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor(
    private val api: OpenFoodFactsApi,
    private val foodEntryDao: FoodEntryDao,
    private val weightEntryDao: WeightEntryDao
) {
    suspend fun searchFood(query: String): List<FoodItem> {
        return try {
            val response = api.searchProducts(query)
            response.products.mapNotNull { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getFoodByBarcode(barcode: String): FoodItem? {
        return try {
            val response = api.getByBarcode(barcode)
            response.product?.toDomainModel()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun logFood(entry: FoodEntry) {
        foodEntryDao.insertEntry(entry)
    }

    fun getDayLog(userId: String, date: String): Flow<List<FoodEntry>> {
        return foodEntryDao.getEntriesByDate(userId, date)
    }

    suspend fun deleteFoodEntry(id: Long) {
        foodEntryDao.deleteEntry(id)
    }
    
    suspend fun logWeight(entry: WeightEntry) {
        weightEntryDao.insert(entry)
    }
    
    fun getLast30DaysWeight(userId: String): Flow<List<WeightEntry>> {
        return weightEntryDao.getLast30Days(userId)
    }

    private fun OffProductDto.toDomainModel(): FoodItem? {
        val name = productName ?: return null
        return FoodItem(
            barcode = code ?: id ?: "",
            name = name,
            brand = brands ?: "",
            caloriesPer100g = nutriments?.energyKcal100g ?: 0f,
            carbsPer100g = nutriments?.carbs100g ?: 0f,
            fatPer100g = nutriments?.fat100g ?: 0f,
            proteinPer100g = nutriments?.proteins100g ?: 0f
        )
    }
}
