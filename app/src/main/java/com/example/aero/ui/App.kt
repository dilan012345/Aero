package com.example.aero.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.aero.ui.screens.Home
import android.util.Log
import com.example.aero.viewmodel.SettingsViewModel

@Composable
fun App(settingsViewModel: SettingsViewModel) {

    Log.d("APP", "App recomposed")
    Navigator(Home()) { navigator ->
        SlideTransition(navigator)
    }
}