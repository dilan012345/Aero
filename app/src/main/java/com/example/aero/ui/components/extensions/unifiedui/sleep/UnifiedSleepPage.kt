package com.example.aero.ui.components.extensions.unifiedui.sleep

import com.example.aero.ui.components.extensions.unifiedui.home.UnifiedSleepCard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.data.DebugLogger
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.ProviderManager
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.components.extensions.unifiedui.home.UnifiedStepsCard
import com.example.aero.ui.components.extensions.unifiedui.home.UnifiedWaterCard
import com.example.aero.ui.components.major.Background
import com.example.aero.ui.components.major.Card
import com.example.aero.ui.components.old.Bar
import com.example.aero.ui.components.old.BarChart
import com.example.aero.ui.theme.boldonse
import com.example.aero.ui.theme.robotosb
import kotlin.math.roundToInt
import kotlin.random.Random

class UnifiedSleepPage(): Screen {
    @Composable
    override fun Content() {
        DebugLogger.log(
            "sleep Recomposed",
            Color.Green
        )
        val providerType = ProviderSettings.getProvider()
        val context = LocalContext.current

        val activeProvider = remember(context) {
            ProviderManager.getActiveProvider(context)
        }

        val gradient = activeProvider.type.gradient

        Background() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = gradient
                        )
                    )
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithCache {

                            val noisePoints = List(3000) {
                                val x = Random.nextFloat()
                                val y = Random.nextFloat()

                                Triple(
                                    Offset(x, y),
                                    Random.nextBoolean(),
                                    1f - y
                                )
                            }

                            onDrawBehind {
                                noisePoints.forEach { (point, white, intensity) ->

                                    drawCircle(
                                        color = if (white)
                                            Color.White.copy(alpha = 0.18f * intensity)
                                        else
                                            Color.Black.copy(alpha = 0.18f * intensity),

                                        radius = 2f,

                                        center = Offset(
                                            point.x * size.width,
                                            point.y * size.height
                                        )
                                    )
                                }
                            }
                        }
                ) {
                    // REQUIRED Canvas draw block
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 20.dp, top = 50.dp)
                    .width(130.dp)
                    .height(60.dp)
//                        .clip(RoundedCornerShape(20.dp))
//                        .background(
//                            MaterialTheme.colorScheme.tertiary
//                        )
            ) {


            }
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            LazyColumn(

                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Sleep",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 20.dp),
                            color = MaterialTheme.colorScheme.tertiaryFixed,
                            fontSize = 30.sp,
                            fontFamily = boldonse
                        )
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(160.dp)
                    ) {
                        var steps by remember {
                            mutableFloatStateOf(9f)
                        }
                        val sliderColors = SliderDefaults.colors(
                            thumbColor = Color(0xFF598FB9),
                            activeTrackColor = Color(0xFF598FB9),
                            inactiveTrackColor = Color(0xFF598FB9).copy(alpha = 0.25f)
                        )

                        val haptic = LocalHapticFeedback.current
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Target",
                                color = MaterialTheme.colorScheme.tertiaryFixed,
                                fontSize = 15.sp,
                                fontFamily = robotosb
                            )

                            Slider(
                                value = steps,
                                onValueChange = {
                                    if (steps != it) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    steps = it
                                },
                                modifier = Modifier.fillMaxWidth(0.8f),
                                colors = sliderColors,
                                track = { state ->
                                    SliderDefaults.Track(
                                        sliderState = state,
                                        colors = sliderColors
                                    )
                                },
                                steps = 11,
                                valueRange = 0f..12f
                            )

                            Box(
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFF2D2D2D))
                            ) {
                                Text(
                                    text = steps.roundToInt().toString() + " hours",
                                    modifier = Modifier.align(Alignment.Center),
                                    color = MaterialTheme.colorScheme.tertiaryFixed,
                                    fontSize = 15.sp,
                                    fontFamily = robotosb
                                )
                            }

                        }
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }

                item{
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(300.dp)
                    ) {
                        val bars =
                            listOf(Bar("M", 5000f),
                                Bar("T", 7200f),
                                Bar("W", 800f),
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
                            color = Color(0xFF598FB9)
                        )
                    }
                }
                item {

                    UnifiedSleepCard(
                        screenWidth = screenWidth,
                        activeProvider = activeProvider
                    )

                }
                item {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
            }
        }
    }
}