package com.example.aero.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.tooling.ComposeToolingApi
import org.koin.core.module.dsl.viewModel
import com.example.aero.data.dataStore
import com.example.aero.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.get
import org.koin.dsl.module


val appModule = module {
    single {
        androidContext().dataStore
    }

    viewModel {
        SettingsViewModel(get())
    }
}