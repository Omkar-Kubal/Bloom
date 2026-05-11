package com.appylab.bloom.core.data

import com.appylab.bloom.core.data.db.daos.ExerciseLogDao
import com.appylab.bloom.core.data.db.daos.SetLogDao
import com.appylab.bloom.core.data.db.daos.WorkoutSessionDao
import com.appylab.bloom.core.data.db.entities.ExerciseLog
import com.appylab.bloom.core.data.db.entities.SetLog
import com.appylab.bloom.core.data.db.entities.WorkoutSession
import com.appylab.bloom.core.domain.model.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val sessionDao: WorkoutSessionDao,
    private val exerciseLogDao: ExerciseLogDao,
    private val setLogDao: SetLogDao
) {
    suspend fun startSession(userId: String): Long {
        val session = WorkoutSession(
            userId = userId,
            startTime = System.currentTimeMillis(),
            endTime = null,
            estimatedCalories = null,
            aiSummaryJson = null
        )
        return sessionDao.insertSession(session)
    }

    suspend fun addExercise(sessionId: Long, exercise: Exercise): Long {
        val log = ExerciseLog(
            sessionId = sessionId,
            exerciseName = exercise.name,
            muscleGroup = exercise.muscleGroup,
            met = exercise.met
        )
        return exerciseLogDao.insertExerciseLog(log)
    }

    suspend fun logSet(exerciseLogId: Long, setNumber: Int, reps: Int, weightKg: Float, tutSeconds: Int?, restSeconds: Int?): Long {
        val set = SetLog(
            exerciseLogId = exerciseLogId,
            setNumber = setNumber,
            reps = reps,
            weightKg = weightKg,
            tutSeconds = tutSeconds,
            restSeconds = restSeconds
        )
        return setLogDao.insertSetLog(set)
    }

    suspend fun finishSession(sessionId: Long, estimatedCalories: Int, aiSummaryJson: String?) {
        val session = sessionDao.getSessionById(sessionId)
        if (session != null) {
            sessionDao.insertSession(
                session.copy(
                    endTime = System.currentTimeMillis(),
                    estimatedCalories = estimatedCalories,
                    aiSummaryJson = aiSummaryJson
                )
            )
        }
    }

    fun getSessionHistory(userId: String): Flow<List<WorkoutSession>> {
        return sessionDao.getSessions(userId)
    }

    suspend fun getSessionById(sessionId: Long): WorkoutSession? {
        return sessionDao.getSessionById(sessionId)
    }
}
