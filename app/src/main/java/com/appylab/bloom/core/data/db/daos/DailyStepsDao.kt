package com.appylab.bloom.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appylab.bloom.core.data.db.entities.DailySteps
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyStepsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertToday(dailySteps: DailySteps)

    @Query("SELECT * FROM daily_steps WHERE userId = :userId AND date = :date")
    fun getSteps(userId: String, date: String): Flow<DailySteps?>

    @Query("SELECT * FROM daily_steps WHERE userId = :userId AND date = :date")
    suspend fun getStepsSync(userId: String, date: String): DailySteps?

    @Query("SELECT * FROM daily_steps WHERE userId = :userId AND date >= :startDate AND date <= :endDate ORDER BY date ASC")
    suspend fun getRange(userId: String, startDate: String, endDate: String): List<DailySteps>
}
