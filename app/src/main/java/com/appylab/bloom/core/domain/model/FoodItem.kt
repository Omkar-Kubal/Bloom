package com.appylab.bloom.core.domain.model

data class FoodItem(
    val barcode: String,
    val name: String,
    val brand: String,
    val caloriesPer100g: Float,
    val carbsPer100g: Float,
    val fatPer100g: Float,
    val proteinPer100g: Float
)
