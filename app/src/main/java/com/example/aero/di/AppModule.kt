package com.example.aero.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.tooling.ComposeToolingApi
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.Provider
import org.koin.core.module.dsl.viewModel
import com.example.aero.data.dataStore
import com.example.aero.healthConnect.HealthConnectManager
import com.example.aero.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.get
import org.koin.dsl.module


val appModule = module {
    single {
        androidContext().dataStore
    }
    single {
        HealthConnectManager(androidContext())
    }

    single {
        GoogleHealthProvider(get())
    }

    viewModel {
        SettingsViewModel(get())
    }
}