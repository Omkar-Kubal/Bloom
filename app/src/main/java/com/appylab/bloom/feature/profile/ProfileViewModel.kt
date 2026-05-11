package com.appylab.bloom.feature.profile

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "profile_prefs")

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val heightKey = stringPreferencesKey("height")
    private val weightKey = stringPreferencesKey("weight")
    private val nameKey = stringPreferencesKey("name")

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            val prefs = context.dataStore.data.first()
            _profileState.value = ProfileState(
                name = prefs[nameKey] ?: "Omkar",
                height = prefs[heightKey] ?: "175",
                weight = prefs[weightKey] ?: "70"
            )
        }
    }

    fun updateProfile(name: String, height: String, weight: String) {
        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                prefs[nameKey] = name
                prefs[heightKey] = height
                prefs[weightKey] = weight
            }
            _profileState.value = ProfileState(name, height, weight)
        }
    }
}

data class ProfileState(
    val name: String = "",
    val height: String = "",
    val weight: String = ""
)
