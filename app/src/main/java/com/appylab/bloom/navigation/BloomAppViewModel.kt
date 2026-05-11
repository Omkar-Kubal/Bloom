package com.appylab.bloom.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appylab.bloom.core.data.UserProfileDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BloomAppViewModel @Inject constructor(
    userProfileDataStore: UserProfileDataStore
) : ViewModel() {
    val onboardingComplete: StateFlow<Boolean> = userProfileDataStore.data
        .map { preferences -> preferences[UserProfileDataStore.Keys.ONBOARDING_COMPLETE] ?: false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
}
