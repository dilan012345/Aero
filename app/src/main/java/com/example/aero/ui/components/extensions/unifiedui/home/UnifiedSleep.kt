package com.example.aero.ui.components.extensions.unifiedui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.Provider
import com.example.aero.R
import com.example.aero.ui.components.extensions.unifiedui.sleep.UnifiedSleepPage
import com.example.aero.ui.components.extensions.unifiedui.steps.UnifiedStepsPage
import com.example.aero.ui.theme.boldonse

@Composable
fun BoxScope.UnifiedSleepCard (
    screenWidth: Dp,
    activeProvider: Provider,
    ){
    val sleeptarget:Int = 11
    var value by remember { mutableIntStateOf(0) }
    //sleep info coroutine
    LaunchedEffect(activeProvider) {
        value = activeProvider.getSleep().toInt()
    }
    val navigator = LocalNavigator.currentOrThrow
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth(0.95f)
            .padding(top = 0.025 * screenWidth)
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable {
                navigator.push(UnifiedSleepPage())
            }
//Color(0xFFB4BFDC)
    ){

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .width(100.dp)
                .height(100.dp)
                .align(Alignment.CenterStart)



        ) {
            CircularWavyProgressIndicator(
                progress = { (value.toFloat() / sleeptarget) },
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(100.dp)
                    .height(100.dp),
                color = Color(0xFF598FB9),
                trackColor = Color(0xF2484848),
                stroke = Stroke(width = 20f, cap = StrokeCap.Round),
                trackStroke = Stroke(width = 15f, cap = StrokeCap.Round),
                gapSize = 10.dp,
                amplitude = { 50f },
                wavelength = 35.dp,
                waveSpeed = 30.dp
            )
            Text(
                text = value.toString() + "h",
                fontFamily = boldonse,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 15.dp),
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.tertiaryFixed

            )
            Icon(
                painter = painterResource(R.drawable.round_dark_mode_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
                    .align(Alignment.Center)
                    .offset(y = 15.dp),
                tint = Color(0xF2656565)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(200.dp)
                .height(100.dp)

        ) {

            Column(
                modifier = Modifier
                    .padding(end = 40.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),

            ) {
                Text(
                    text = when {
                        value == 0 -> "Get to sleeping!"
                        value >= (sleeptarget - 3) && value < sleeptarget -> "Great Sleep!"
                        value == sleeptarget -> "Perfect!"
                        value > sleeptarget -> "You've smashed\nyour target!"
                        else -> "Take a nap!"
                    },
                    fontFamily = boldonse,
                    color = MaterialTheme.colorScheme.tertiaryFixed,
                    fontSize = 16.sp
                )

                Text(
                    text = "Target: ${sleeptarget}h",
                    fontFamily = boldonse,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
        }
    }
}
