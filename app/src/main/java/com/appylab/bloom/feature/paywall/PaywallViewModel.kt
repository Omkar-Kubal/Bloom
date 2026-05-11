package com.appylab.bloom.feature.paywall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appylab.bloom.core.data.UserProfileDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Plan {
    Monthly,
    Yearly
}

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val dataStore: UserProfileDataStore
) : ViewModel() {
    private val _selectedPlan = MutableStateFlow(Plan.Yearly)
    val selectedPlan: StateFlow<Plan> = _selectedPlan.asStateFlow()

    fun selectPlan(plan: Plan) {
        _selectedPlan.value = plan
    }

    fun startTrial(onComplete: () -> Unit) {
        viewModelScope.launch {
            dataStore.savePreference(UserProfileDataStore.Keys.SUBSCRIPTION_STATUS, "trial")
            dataStore.savePreference(UserProfileDataStore.Keys.TRIAL_START_DATE, System.currentTimeMillis())
            onComplete()
        }
    }
}
