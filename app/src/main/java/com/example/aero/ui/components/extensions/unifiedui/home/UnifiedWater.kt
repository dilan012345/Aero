package com.example.aero.ui.components.extensions.unifiedui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.Provider
import com.example.aero.ui.components.extensions.unifiedui.water.UnifiedWaterPage
import com.example.aero.ui.components.minor.rememberRotatedFanShape
import com.example.aero.ui.theme.robotosb
import com.example.aero.ui.theme.boldonse
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.UnifiedWaterCard (
    screenWidth: Dp,
) {

    val navigator = LocalNavigator.currentOrThrow

    val progress = 0.5f
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth(0.95f)
            .padding(top = 0.025 * screenWidth)
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable {
                Log.d("CLICK", "Water clicked")
                navigator.push(UnifiedWaterPage())
            }
    ){
        Text(
            text = "Water",
            fontFamily = robotosb,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 20.dp),
            color = MaterialTheme.colorScheme.tertiaryFixed
        )
        Text(
            text = "1500",
            fontFamily = boldonse,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 40.dp),
            color = MaterialTheme.colorScheme.tertiaryFixed,
            fontSize = 30.sp
        )
        Text(
            text = "/ 3000ml",
            fontFamily = robotosb,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 25.dp),
            color = Color.Gray,
            fontSize = 15.sp
        )

        val fanShape = rememberRotatedFanShape()
        Box(
            Modifier
                .align(Alignment.CenterEnd)
                .size(120.dp)
                .offset(x = -20.dp)

        ) {


            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(fanShape)
                    .background(Color(0xF23A3A3A))
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(120.dp)
                        .fillMaxHeight(progress)
                        .background(Color(0xFF598FB9))
                ){
                    LinearWavyProgressIndicator(
                        progress = { 1f },
                        stroke = Stroke(width = 20f, cap = StrokeCap.Round),
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = -4.dp)
                            .height(10.dp)
                            .align(Alignment.TopCenter),
                        color = Color(0xFF598FB9),
                        trackColor = Color.Transparent,
                        gapSize = 0.dp,
                        amplitude = { 5f },
                        wavelength = 35.dp,
                        waveSpeed = 30.dp

                    )

                }
            }

        }

    }
}