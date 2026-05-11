package com.appylab.bloom.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appylab.bloom.core.data.db.entities.ExerciseLog
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseLog(log: ExerciseLog): Long

    @Query("SELECT * FROM exercise_logs WHERE sessionId = :sessionId")
    fun getExercisesForSession(sessionId: Long): Flow<List<ExerciseLog>>
}
