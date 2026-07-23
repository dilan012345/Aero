package com.example.aero.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.example.aero.ui.screens.Home

@Composable
fun App() {

    Navigator(
        screen = Home()
    )

}