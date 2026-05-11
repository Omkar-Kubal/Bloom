package com.appylab.bloom.feature.run

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.appylab.bloom.core.data.StepRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MidnightResetWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val stepRepository: StepRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            stepRepository.resetBaselineForNewDay("mock_user_123", 0)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
