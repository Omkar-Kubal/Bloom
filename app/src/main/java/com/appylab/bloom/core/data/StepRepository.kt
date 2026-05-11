package com.appylab.bloom.core.data

import com.appylab.bloom.core.data.db.daos.DailyStepsDao
import com.appylab.bloom.core.data.db.entities.DailySteps
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepRepository @Inject constructor(
    private val stepsDao: DailyStepsDao
) {
    private val todayStr: String
        get() = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

    suspend fun getTodaySteps(userId: String): DailySteps? {
        return stepsDao.getStepsSync(userId, todayStr)
    }

    fun getTodayStepsFlow(userId: String): Flow<DailySteps?> {
        return stepsDao.getSteps(userId, todayStr)
    }

    suspend fun updateSteps(userId: String, newTotalSteps: Int) {
        val today = getTodaySteps(userId)
        if (today == null) {
            stepsDao.upsertToday(
                DailySteps(
                    userId = userId,
                    date = todayStr,
                    stepCount = 0,
                    calorieBurn = 0,
                    distanceKm = 0f,
                    activeMinutes = 0,
                    sensorBaseline = newTotalSteps
                )
            )
        } else {
            val baseline = today.sensorBaseline
            val dailySteps = newTotalSteps - baseline
            if (dailySteps > 0) {
                // Approximate metrics: 1 step = 0.04 kcal, 1 step = 0.000762 km
                val newCount = today.stepCount + dailySteps
                stepsDao.upsertToday(
                    today.copy(
                        stepCount = newCount,
                        calorieBurn = (newCount * 0.04).toInt(),
                        distanceKm = (newCount * 0.000762).toFloat(),
                        sensorBaseline = newTotalSteps
                    )
                )
            } else if (newTotalSteps < baseline) {
                // Device rebooted, reset baseline
                stepsDao.upsertToday(today.copy(sensorBaseline = newTotalSteps))
            }
        }
    }

    suspend fun resetBaselineForNewDay(userId: String, currentSensorTotal: Int) {
        stepsDao.upsertToday(
            DailySteps(
                userId = userId,
                date = todayStr,
                stepCount = 0,
                calorieBurn = 0,
                distanceKm = 0f,
                activeMinutes = 0,
                sensorBaseline = currentSensorTotal
            )
        )
    }
}
