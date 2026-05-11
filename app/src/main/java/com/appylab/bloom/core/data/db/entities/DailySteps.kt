package com.appylab.bloom.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_steps")
data class DailySteps(
    @PrimaryKey val date: String,
    val userId: String,
    val stepCount: Int = 0,
    val calorieBurn: Int = 0,
    val distanceKm: Float = 0f,
    val activeMinutes: Int = 0,
    val sensorBaseline: Int = 0,
    val goal: Int = 8000
)
