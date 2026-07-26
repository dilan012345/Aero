package com.example.aero.ui.screens.Home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aero.R
import com.example.aero.data.ProviderType
import com.example.aero.data.Provider
import com.example.aero.ui.components.Stamp
import com.example.aero.ui.theme.coolvetica
import com.example.aero.ui.theme.googlesans
import com.example.aero.ui.theme.roboto
import com.example.aero.ui.theme.samsungsharpsans
import kotlin.random.Random
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.ui.screens.Setup

@Composable
fun HomeHeader(
    activeProvider: Provider,
    notification: Boolean,
    onDrawerClick: () -> Unit,
    onProfileClick: () -> Unit,
    showTargets: Boolean,
    onShowTargetsChange: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val extension = when (activeProvider.type) {

        ProviderType.GOOGLE_HEALTH -> listOf(
            Color(0xFFFF5722),
            Color(0xAAE24A75),
            Color.Transparent
        )

        ProviderType.SAMSUNG_HEALTH -> listOf(
            Color(0xFF0B5048),
            Color(0xFF72BB67),
            Color.Transparent
        )

        ProviderType.STRAVA -> listOf(
            Color(0xFFFC4C02),
            Color(0xFFFF8C42),
            Color.Transparent
        )
        ProviderType.UNIFIED -> listOf(
            Color(0xFFA6A6D3),
            Color(0xFFCAB5BA),
            Color.Transparent
        )

        else -> listOf(
            Color(0xFF0B5048),
            Color(0xFF72BB67),
            Color.Transparent
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = extension
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


        Text(
            text = when(activeProvider.type){

                ProviderType.GOOGLE_HEALTH ->
                    "Google Health"

                ProviderType.SAMSUNG_HEALTH ->
                    "Samsung Health"

                ProviderType.STRAVA ->
                    ""
                ProviderType.UNIFIED ->
                    "Unified"
                else ->
                    "DEBUG"
            },

            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 55.dp,
                    start = 20.dp
                ),

            fontSize = when(activeProvider.type){

                ProviderType.GOOGLE_HEALTH ->
                    23.sp

                ProviderType.SAMSUNG_HEALTH ->
                    23.sp

                else ->
                    29.sp
            },

            fontFamily = when(activeProvider.type){

                ProviderType.GOOGLE_HEALTH ->
                    googlesans

                ProviderType.SAMSUNG_HEALTH ->
                    samsungsharpsans

                else ->
                    coolvetica
            },

            color = Color.White
        )


        if(activeProvider.type == ProviderType.STRAVA){

            Image(

                painter = painterResource(
                    R.drawable.stravatext
                ),

                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 20.dp)
                    .width(180.dp)
                    .height(60.dp)
                    .offset(y = 40.dp),

                contentDescription = null
            )
        }



        // Extension button
        Box(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .align(Alignment.Center)
        ) {
            Stamp(

                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 60.dp)
                    .size(50.dp),


                content = {

                    IconButton(
                        modifier = Modifier
                            .size(50.dp),
                        onClick = {
                            Log.d("DRAWER", "CLICK FIRED")
                            onDrawerClick()
                        },
                    ) {

                        Icon(

                            painter = painterResource(

                                when (activeProvider.type) {

                                    ProviderType.GOOGLE_HEALTH ->
                                        R.drawable.googlehealth

                                    ProviderType.SAMSUNG_HEALTH ->
                                        R.drawable.samsunghealth

                                    ProviderType.STRAVA ->
                                        R.drawable.strava

                                    ProviderType.UNIFIED ->
                                        R.drawable.unified
                                    else ->
                                        R.drawable.ic_launcher_background
                                }
                            ),

                            modifier = Modifier.size(30.dp),

                            contentDescription = null,

                            tint = Color.Unspecified
                        )
                    }
                }
            )



        // Profile button

            Box(

                modifier = Modifier

                    .align(Alignment.TopEnd)

                    .size(50.dp)

                    .clip(CircleShape)

                    .background(
                        MaterialTheme.colorScheme.surface
                    )

                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.tertiary,
                        CircleShape
                    )

                    .clickable(
                        onClick = onProfileClick
                    )

            ) {

                Icon(

                    painter = painterResource(
                        R.drawable.round_person_2_24
                    ),

                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp),

                    contentDescription = null,

                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            if(notification){

                Box(

                    modifier = Modifier

                        .align(Alignment.TopEnd)

                        .padding(
                            top = 2.dp,
                            end = 2.dp
                        )

                        .size(10.dp)

                        .clip(CircleShape)

                        .background(Color.Red)

                        .offset(
                            x = 15.dp,
                            y = 5.dp
                        )
                )
            }
        }


        Toolbar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y= 110.dp)
                .fillMaxWidth()
                .height(50.dp),
            showTargets = showTargets,
            onTargetsClick = onShowTargetsChange,
            onConnectClick = {
                navigator.push(Setup())
            }

        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(0.9f)
                .offset(y = 165.dp)
                .height(2.dp)
                .background(MaterialTheme.colorScheme.surfaceBright)

        )


    }
}