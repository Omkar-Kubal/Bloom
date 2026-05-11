package com.appylab.bloom.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    val id: String,
    val name: String,
    val muscleGroup: String,
    val primaryMuscles: List<String>,
    val secondaryMuscles: List<String>,
    val met: Float,
    val equipment: String
)
