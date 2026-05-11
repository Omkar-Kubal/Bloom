package com.appylab.bloom.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_sessions")
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val startTime: Long,
    val endTime: Long?,
    val estimatedCalories: Int?,
    val aiSummaryJson: String?
)
