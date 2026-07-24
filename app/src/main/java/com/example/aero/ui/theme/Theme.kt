package com.example.aero.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.aero.viewmodel.SettingsViewModel

private val LightColors = lightColorScheme(
    primary = LightPrimary,
    background = LightBackground,
    surface = LightQuaternary,
    secondary = LightSecondary,
    tertiary = LightTertiary
)

private val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    background = DarkBackground,
    tertiary = DarkTertiary,
    secondary = DarkSecondary,
    surface = DarkQuaternary,
)

@Composable
fun AeroTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) {
            DarkColors
        } else {
            LightColors
        },
        typography = Typography,
        content = content
    )
}