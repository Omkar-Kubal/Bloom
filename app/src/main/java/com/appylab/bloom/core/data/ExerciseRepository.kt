package com.appylab.bloom.core.data

import android.content.Context
import com.appylab.bloom.core.domain.model.Exercise
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
    private var exercisesCache: List<Exercise> = emptyList()

    fun getExercises(): List<Exercise> {
        if (exercisesCache.isNotEmpty()) return exercisesCache
        try {
            val jsonString = context.assets.open("exercises.json").bufferedReader().use { it.readText() }
            exercisesCache = json.decodeFromString<List<Exercise>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return exercisesCache
    }

    fun searchExercises(query: String): List<Exercise> {
        val all = getExercises()
        if (query.isBlank()) return all
        return all.filter { it.name.contains(query, ignoreCase = true) || it.muscleGroup.contains(query, ignoreCase = true) }
    }

    fun getExerciseById(id: String): Exercise? {
        return getExercises().find { it.id == id }
    }
}
