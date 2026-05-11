package com.appylab.bloom.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appylab.bloom.core.data.db.entities.WeightEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: WeightEntry)

    @Query("SELECT * FROM weight_entries WHERE userId = :userId ORDER BY date DESC LIMIT 30")
    fun getLast30Days(userId: String): Flow<List<WeightEntry>>
}
