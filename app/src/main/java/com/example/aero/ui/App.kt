package com.example.aero.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.aero.ui.screens.Home.Home
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.aero.data.ProviderSettings
import com.example.aero.viewmodel.SettingsViewModel

@Composable
fun App() {
    val context = LocalContext.current

    ProviderSettings.initialise(context)

    Navigator(Home()) { navigator ->
        SlideTransition(navigator)
    }

}