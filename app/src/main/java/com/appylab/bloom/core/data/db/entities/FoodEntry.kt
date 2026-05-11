package com.appylab.bloom.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_entries")
data class FoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val date: String,
    val mealType: String,
    val foodName: String,
    val brand: String?,
    val grams: Float?,
    val quantity: Float?,
    val calories: Int,
    val carbs: Float,
    val fat: Float,
    val protein: Float,
    val barcode: String?,
    val source: String,
    val loggedAt: Long
)
