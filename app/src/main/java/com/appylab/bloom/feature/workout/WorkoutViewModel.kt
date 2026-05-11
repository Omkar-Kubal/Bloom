package com.appylab.bloom.feature.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appylab.bloom.core.data.ExerciseRepository
import com.appylab.bloom.core.data.WorkoutRepository
import com.appylab.bloom.core.data.db.entities.WorkoutSession
import com.appylab.bloom.core.domain.model.Exercise
import com.appylab.bloom.core.network.GeminiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    authRepository: com.appylab.bloom.core.data.AuthRepository,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val geminiService: GeminiService
) : ViewModel() {

    private val currentUserId = authRepository.getUserId()

    val sessionHistory: StateFlow<List<WorkoutSession>> = workoutRepository.getSessionHistory(currentUserId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeSessionId = MutableStateFlow<Long?>(null)
    val activeSessionId = _activeSessionId.asStateFlow()

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises = _exercises.asStateFlow()

    private val _restTimerSeconds = MutableStateFlow(0)
    val restTimerSeconds = _restTimerSeconds.asStateFlow()

    init {
        // Load initial exercises
        searchExercises("")
    }

    fun startSession() {
        viewModelScope.launch {
            _activeSessionId.value = workoutRepository.startSession(currentUserId)
        }
    }

    fun searchExercises(query: String) {
        _exercises.value = exerciseRepository.searchExercises(query)
    }

    fun addExerciseToSession(exercise: Exercise, onAdded: (Long) -> Unit) {
        val sessionId = _activeSessionId.value ?: return
        viewModelScope.launch {
            val logId = workoutRepository.addExercise(sessionId, exercise)
            onAdded(logId)
        }
    }

    fun logSet(exerciseLogId: Long, setNumber: Int, reps: Int, weightKg: Float, tutSeconds: Int?, restSeconds: Int?) {
        viewModelScope.launch {
            workoutRepository.logSet(exerciseLogId, setNumber, reps, weightKg, tutSeconds, restSeconds)
            if (restSeconds != null && restSeconds > 0) {
                startRestTimer(restSeconds)
            }
        }
    }

    private fun startRestTimer(seconds: Int) {
        viewModelScope.launch {
            _restTimerSeconds.value = seconds
            while (_restTimerSeconds.value > 0) {
                delay(1000)
                _restTimerSeconds.value -= 1
            }
        }
    }

    fun finishSession(onFinished: (WorkoutSession?) -> Unit) {
        val sessionId = _activeSessionId.value ?: return
        viewModelScope.launch {
            val fallbackCalories = 300 // MVP fallback
            var summaryJson: String? = null
            
            // Generate AI Summary
            val sessionDataString = "Session ID: $sessionId. Random weights and reps."
            summaryJson = geminiService.generateWorkoutSummary(sessionDataString)

            workoutRepository.finishSession(sessionId, fallbackCalories, summaryJson)
            _activeSessionId.value = null
            onFinished(workoutRepository.getSessionById(sessionId))
        }
    }
}
