package com.appylab.bloom.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenFoodFactsSearchResponse(
    val count: Int,
    val page: Int,
    val products: List<OffProductDto>
)

@Serializable
data class OpenFoodFactsProductResponse(
    val code: String,
    val product: OffProductDto?,
    val status: Int
)

@Serializable
data class OffProductDto(
    @SerialName("_id") val id: String? = null,
    val code: String? = null,
    @SerialName("product_name") val productName: String? = null,
    val brands: String? = null,
    val nutriments: OffNutrimentsDto? = null
)

@Serializable
data class OffNutrimentsDto(
    @SerialName("energy-kcal_100g") val energyKcal100g: Float? = null,
    @SerialName("carbohydrates_100g") val carbs100g: Float? = null,
    @SerialName("fat_100g") val fat100g: Float? = null,
    @SerialName("proteins_100g") val proteins100g: Float? = null
)
