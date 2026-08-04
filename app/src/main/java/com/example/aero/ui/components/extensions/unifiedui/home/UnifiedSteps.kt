package com.example.aero.ui.components.extensions.unifiedui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.Provider
import com.example.aero.ui.components.extensions.shealthui.home.WeeklyBarChartSmall
import com.example.aero.ui.components.extensions.unifiedui.steps.UnifiedStepsPage

import com.example.aero.ui.theme.boldonse
import com.example.aero.ui.theme.robotoFlex
import com.example.aero.ui.theme.robotosb
import java.text.NumberFormat
@Composable
fun BoxScope.UnifiedStepsCard (
    screenWidth: Dp,
    activeProvider: Provider,
){
    val navigator = LocalNavigator.currentOrThrow
    var steps by remember { mutableIntStateOf(0) }
    var target:Int by remember { mutableIntStateOf(10000)}
    LaunchedEffect(activeProvider) {
        activeProvider.sync()
        steps = activeProvider.getSteps()
    }

    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth(0.95f)
            .padding(top = 0.025 * screenWidth)
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable {
                navigator.push(UnifiedStepsPage())
            }

    ) {
        Text(
            text = "Steps",
            fontFamily = robotosb,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 20.dp),
            color = MaterialTheme.colorScheme.tertiaryFixed
        )
        Text(
            text = NumberFormat.getIntegerInstance().format(steps).toString(),
            fontFamily = boldonse,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 40.dp),
            color = MaterialTheme.colorScheme.tertiaryFixed,
            fontSize = 30.sp
        )
        Text(
            text = "/ "+NumberFormat.getIntegerInstance().format(target).toString(),
            fontFamily = robotosb,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 25.dp),
            color = Color.Gray,
            fontSize = 15.sp
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .width(0.4 * screenWidth)
                .fillMaxHeight(0.5f)
        ) {
            WeeklyBarChartSmall(
                primary = Color(0xFF598FB9)
            )
        }
    }
}