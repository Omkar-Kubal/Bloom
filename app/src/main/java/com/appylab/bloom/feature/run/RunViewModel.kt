package com.appylab.bloom.feature.run

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appylab.bloom.core.data.StepRepository
import com.appylab.bloom.core.data.db.entities.DailySteps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    authRepository: com.appylab.bloom.core.data.AuthRepository,
    stepRepository: StepRepository
) : ViewModel() {
    
    val todaySteps: StateFlow<DailySteps?> = stepRepository.getTodayStepsFlow(authRepository.getUserId())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

}
