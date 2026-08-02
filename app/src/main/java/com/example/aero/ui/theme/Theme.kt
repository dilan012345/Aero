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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.aero.data.AppDataStoreKeys
import com.example.aero.data.appDataStore
import com.example.aero.viewmodel.SettingsViewModel


private val LightColors = lightColorScheme(
    primary = LightPrimary,
    background = LightBackground,
    surface = LightQuaternary,
    secondary = LightSecondary,
    tertiary = Color(0xFFB4BFDC),
    tertiaryFixed = Color(0xFF000000)
)

private val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    background = DarkBackground,
    tertiary = Color(0xFF171A21),
    secondary = DarkSecondary,
    surface = DarkQuaternary,
    tertiaryFixed = Color(0xFFFFFFFF)
)
private val DebugColors = darkColorScheme(
    primary = DarkPrimary,
    background = Color(0xFF000000),
    tertiary = DarkTertiary,
    secondary = DarkSecondary,
    surface = Color(0xFF1E1E1E),
    tertiaryFixed = Color(0xFFE9E2FF)
)

@Composable
fun AeroTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    var debug by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        context.appDataStore.data.collect { preferences ->
            debug = preferences[AppDataStoreKeys.DEBUG] ?: false
        }
    }
    MaterialTheme(
        colorScheme = if(!debug) {


            if (darkTheme) {
                DarkColors
            } else {
                LightColors
            }
        }
        else{
            DebugColors
        }


        ,
        typography = Typography,
        content = content
    )
}