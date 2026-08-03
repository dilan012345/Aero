package com.example.aero.data

import androidx.compose.ui.graphics.Color

enum class ProviderType(
    val gradient: List<Color>
) {
    GOOGLE_HEALTH(
        listOf(
            Color(0xFFFF5722),
            Color(0xAAE24A75),
            Color.Transparent
        )
    ),

    SAMSUNG_HEALTH(
        listOf(
            Color(0xFF0B2C50),
            Color(0xFF67BB94),
            Color.Transparent
        )
    ),

    STRAVA(
        listOf(
            Color(0xFFFC4C02),
            Color(0xFFFF8C42),
            Color.Transparent
        )
    ),

    UNIFIED(
        listOf(
            Color(0xFF708F96),
            Color(0xFF598FB9),
            Color.Transparent
        )
    )
}