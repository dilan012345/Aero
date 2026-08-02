package com.example.aero.ui.components.extensions.ghealthui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.Provider
import com.example.aero.data.ProviderType
import com.example.aero.ui.components.old.Bar
import com.example.aero.ui.components.old.BarChart
import com.example.aero.ui.components.major.Card

import com.example.aero.ui.theme.googlesans

@Composable
fun StepGraph(
    activeProvider : Provider
){
    val navigator = LocalNavigator.currentOrThrow
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)



    ) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(300.dp)

        ) {
            var selected by rememberSaveable { mutableStateOf(1) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .padding(top = 10.dp)
                        .height(30.dp)
                        .clip(
                            RoundedCornerShape(
                                50.dp
                            )
                        )
                        .background(
                            if (selected == 0)
                                MaterialTheme.colorScheme.surfaceBright
                            else
                                Color.Transparent
                        )
                        .border(
                            2.dp,
                            if (selected == 0)
                                MaterialTheme.colorScheme.tertiary
                            else
                                MaterialTheme.colorScheme.surfaceBright,
                            androidx.compose.foundation.shape.RoundedCornerShape(50.dp)
                        )
                        .clickable {
                            selected = 0
                        }

                ) {
                    Text(
                        text = "Last week",
                        modifier = Modifier
                            .align(Alignment.Center),
                        fontSize = 13.sp,
                        fontFamily = googlesans,
                        color = MaterialTheme.colorScheme.tertiaryFixed
                    )
                }
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .padding(top = 10.dp)
                        .height(30.dp)
                        .clip(
                            androidx.compose.foundation.shape.RoundedCornerShape(
                                50.dp
                            )
                        )
                        .background(
                            if (selected == 1)
                                MaterialTheme.colorScheme.surfaceBright
                            else
                                Color.Transparent
                        )
                        .border(
                            2.dp,
                            if (selected == 1)
                                MaterialTheme.colorScheme.tertiary
                            else
                                MaterialTheme.colorScheme.surfaceBright,
                            androidx.compose.foundation.shape.RoundedCornerShape(50.dp)
                        )
                        .clickable {
                            selected = 1
                        }

                ) {
                    Text(
                        text = "This week",
                        modifier = Modifier
                            .align(Alignment.Center),
                        fontSize = 13.sp,
                        fontFamily = googlesans,
                        color = MaterialTheme.colorScheme.tertiaryFixed
                    )
                }

            }

            val bars = listOf(
                Bar("M", 5000f),
                Bar("T", 7200f),
                Bar("W", 8300f),
                Bar("T", 6100f),
                Bar("F", 9500f),
                Bar("S", 12020f),
                Bar("S", 7000f)
            )
            BarChart(
                bars = bars,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp)
                    .fillMaxWidth(0.9f)
                    .height(200.dp),
                color = when (activeProvider.type) {
                    ProviderType.GOOGLE_HEALTH ->
                        //Color(0xFF4A90E2)
                        MaterialTheme.colorScheme.tertiary

                    ProviderType.SAMSUNG_HEALTH ->
                        Color(0xFF72BB67)


                    ProviderType.STRAVA ->

                        Color(0xFFFF8C42)


                    else ->
                        MaterialTheme.colorScheme.tertiary


                }

            )
        }
    }
}