package com.appylab.bloom.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appylab.bloom.core.data.db.daos.*
import com.appylab.bloom.core.data.db.entities.*

@Database(
    entities = [
        FoodEntry::class,
        WorkoutSession::class,
        ExerciseLog::class,
        SetLog::class,
        DailySteps::class,
        WeightEntry::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BloomDatabase : RoomDatabase() {
    abstract fun foodEntryDao(): FoodEntryDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun exerciseLogDao(): ExerciseLogDao
    abstract fun setLogDao(): SetLogDao
    abstract fun dailyStepsDao(): DailyStepsDao
    abstract fun weightEntryDao(): WeightEntryDao
}
