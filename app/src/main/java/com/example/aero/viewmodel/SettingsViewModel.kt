package com.example.aero.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.util.Log
import com.example.aero.data.ProviderType

class SettingsViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val DARK_MODE = booleanPreferencesKey("dark_mode")

    val darkTheme = dataStore.data
        .map { preferences ->
            preferences[DARK_MODE] ?: true
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            true
        )

    fun updateDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DARK_MODE] = enabled
            }
            Log.d("SettingsVM", "Dark theme: ${darkTheme.value}")
        }


    }
}



