package com.appylab.bloom.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_logs")
data class SetLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val exerciseLogId: Long,
    val setNumber: Int,
    val reps: Int,
    val weightKg: Float,
    val tutSeconds: Int?,
    val restSeconds: Int?
)
