package com.example.aero.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "Toolbar_preferences"
)
object AppDataStoreKeys {
    val DEBUG = booleanPreferencesKey("Debug")
    val ADVANCED = booleanPreferencesKey("Advanced")
    fun Context.observeAppSettings() = appDataStore.data
        .map { preferences ->
            val debug = preferences[AppDataStoreKeys.DEBUG] ?: false
            val advanced = preferences[AppDataStoreKeys.ADVANCED] ?: false

            DebugLogger.log(
                "TOOLBAR DATASTORE DEBUG: $debug | ADVANCED: $advanced",
                Color.Magenta
            )

            debug to advanced
        }
        .distinctUntilChanged()


}

