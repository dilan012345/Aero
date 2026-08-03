package com.example.aero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.example.aero.ui.App
import com.example.aero.ui.theme.AeroTheme
import com.example.aero.viewmodel.SettingsViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.aero.data.AppDataStoreKeys.observeAppSettings
import com.example.aero.di.appModule
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
        enableEdgeToEdge()
        window.setNavigationBarContrastEnforced(false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false}
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(appModule)
            }
        }



        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                context.observeAppSettings().collect()
            }
            AeroTheme(
                darkTheme = settingsViewModel.darkTheme.collectAsState().value
            ) {

                App()
            }
        }
    }
}

