package com.example.aero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.aero.ui.App
import com.example.aero.ui.theme.AeroTheme
import com.example.aero.viewmodel.SettingsViewModel
import androidx.compose.runtime.collectAsState
import com.example.aero.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(appModule)
            }
        }



        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()

            AeroTheme(
                darkTheme = settingsViewModel.darkTheme.collectAsState().value
            ) {
                App()
            }
        }
    }
}

