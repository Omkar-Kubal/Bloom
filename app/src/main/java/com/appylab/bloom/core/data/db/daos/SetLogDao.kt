package com.appylab.bloom.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appylab.bloom.core.data.db.entities.SetLog
import kotlinx.coroutines.flow.Flow

@Dao
interface SetLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetLog(log: SetLog): Long

    @Query("SELECT * FROM set_logs WHERE exerciseLogId = :exerciseLogId ORDER BY setNumber ASC")
    fun getSetsForExercise(exerciseLogId: Long): Flow<List<SetLog>>
}
