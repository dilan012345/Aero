package com.example.aero.ui.components.drawers


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aero.data.Provider
import com.example.aero.data.ProviderType


@Composable
fun BottomDrawer(
    modifier: Modifier = Modifier,
    activeProvider: Provider,
    content: @Composable BoxScope.() -> Unit
) {
    val primary = when (activeProvider.type) {

        ProviderType.GOOGLE_HEALTH ->
            Color(0xFFFF5722)


        ProviderType.SAMSUNG_HEALTH ->
            Color(0xFF67BB94)


        ProviderType.STRAVA ->
            Color(0xFFFC4C02)

        ProviderType.UNIFIED ->
            Color(0x79A6A6D3)


        else ->
            Color(0xFF0B5048)

    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )
            .border(
                1.dp,
                primary,
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        content()
    }
}