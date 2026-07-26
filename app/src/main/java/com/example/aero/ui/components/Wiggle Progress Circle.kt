package com.example.aero.ui.components

import android.R.attr.color
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aero.ui.theme.googlesans


@Composable
fun WiggleProgressCircle(value:Int,target:Int){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularWavyProgressIndicator(
            progress = {(value.toFloat()/target)},
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.5f),
            color = MaterialTheme.colorScheme.tertiary,
            trackColor = Color(0xF2484848),
            stroke = Stroke(width = 20f, cap = StrokeCap.Round),
            trackStroke = Stroke(width = 15f, cap = StrokeCap.Round),
            gapSize = 10.dp,
            amplitude = { 4f },
            wavelength = 60.dp,
            waveSpeed = 30.dp
        )
        Text(
            text = value.toString() + "h",
            fontFamily = googlesans,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 20.dp),
            fontSize = 60.sp,
            color = Color.White

        )
    }
}