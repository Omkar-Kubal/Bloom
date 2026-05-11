package com.appylab.bloom.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appylab.bloom.core.data.db.entities.FoodEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: FoodEntry)

    @Query("SELECT * FROM food_entries WHERE userId = :userId AND date = :date ORDER BY loggedAt DESC")
    fun getEntriesByDate(userId: String, date: String): Flow<List<FoodEntry>>

    @Query("DELETE FROM food_entries WHERE id = :id")
    suspend fun deleteEntry(id: Long)
}
