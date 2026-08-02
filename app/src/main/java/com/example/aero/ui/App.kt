package com.example.aero.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.aero.ui.screens.`Global Home`
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider

@Composable
fun App() {
    val context = LocalContext.current

    ProviderSettings.initialise(context)
    val providers = remember(context) {
        listOf(
            GoogleHealthProvider(context),
            SamsungHealthProvider(context),
            StravaProvider(),
            UnifiedProvider(context)
        )
    }

    LaunchedEffect(Unit) {
        ProviderSettings.initialise(context)

        providers.forEach { provider ->
            provider.sync()
        }
    }
    Navigator(`Global Home`()) { navigator ->
        SlideTransition(navigator)
    }

}