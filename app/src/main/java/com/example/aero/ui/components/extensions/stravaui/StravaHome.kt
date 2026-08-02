package com.example.aero.ui.components.extensions.stravaui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.Provider
import com.example.aero.ui.components.major.HomeHeader
import com.example.aero.ui.theme.boldonse

@Composable
fun StravaHome(
    activeProvider: Provider,
    EdrawerOpen: Boolean,
    onEDrawerChange: (Boolean) -> Unit,
    onSDrawerChange: (Boolean) -> Unit,
    SdrawerOpen: Boolean,
){
    val context = LocalContext.current
    var Edraw = EdrawerOpen
    var Sdraw = SdrawerOpen
    var showTargets by rememberSaveable { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow
    val notification: Boolean = true



    val scope = rememberCoroutineScope()
    HomeHeader(
        activeProvider = activeProvider,
        notification = notification,
        onEDrawerClick = {
            onEDrawerChange(!Edraw)

        },
        onSDrawerClick = {

            onSDrawerChange(!Sdraw)
        },
        showTargets = showTargets,
        onShowTargetsChange = {
            showTargets = !showTargets
        },
        EdrawerOpen = Edraw,
        SdrawerOpen = Sdraw,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Box(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f)
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .border(1.dp, Color(0xFFFC4C02), RoundedCornerShape(20.dp))
        ) {
            Text(
                text = "STRAVA Extension \nin development",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 20.dp, end = 20.dp),
                color = MaterialTheme.colorScheme.tertiaryFixed,
                fontFamily = boldonse,
                fontSize = 20.sp
            )
        }
    }
}