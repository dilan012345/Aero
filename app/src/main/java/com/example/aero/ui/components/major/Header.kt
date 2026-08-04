package com.example.aero.ui.components.major

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aero.R
import com.example.aero.data.ProviderType
import com.example.aero.data.Provider
import com.example.aero.ui.theme.coolvetica
import com.example.aero.ui.theme.googlesans
import com.example.aero.ui.theme.samsungsharpsans
import kotlin.random.Random
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.AppDataStoreKeys
import com.example.aero.data.appDataStore
import com.example.aero.ui.components.minor.Cookie9tosquare


import com.example.aero.ui.screens.Setup

import com.example.aero.ui.theme.boldonse
import com.example.aero.ui.theme.robotoFlex


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeHeader(
    activeProvider: Provider,
    notification: Boolean,
    onEDrawerClick: () -> Unit,
    onSDrawerClick: () -> Unit,
    showTargets: Boolean,
    onShowTargetsChange: () -> Unit,
    EdrawerOpen: Boolean,
    SdrawerOpen: Boolean
) {
    var debug by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.appDataStore.data.collect { preferences ->
            debug = preferences[AppDataStoreKeys.DEBUG] ?: false
        }
    }
    var advanced by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        context.appDataStore.data.collect { preferences ->
            advanced= preferences[AppDataStoreKeys.ADVANCED] ?: false
        }
    }
    val navigator = LocalNavigator.currentOrThrow
    var extension = when (activeProvider.type) {

        ProviderType.GOOGLE_HEALTH -> listOf(
            Color(0xFFFF5722),
            Color(0xAAE24A75),
            Color.Transparent
        )

        ProviderType.SAMSUNG_HEALTH -> listOf(
            Color(0xFF0B2C50),
            Color(0xFF67BB94),
            Color.Transparent
        )

        ProviderType.STRAVA -> listOf(
            Color(0xFFFC4C02),
            Color(0xFFFF8C42),
            Color.Transparent
        )
        ProviderType.UNIFIED -> listOf(
            Color(0xFF708F96),
            Color(0xFF598FB9),
            Color.Transparent
        )


        else -> listOf(
            Color(0xFF0B5048),
            Color(0xFF72BB67),
            Color.Transparent
        )
    }
    if (debug or advanced) {
        extension = listOf(
            Color(0xFF623960),
            Color(0xFF341634),
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
                    start = 20.dp,
                    top = when (activeProvider.type) {
                        ProviderType.UNIFIED -> 40.dp
                        else -> 55.dp
                    }
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
                ProviderType.UNIFIED ->
                    boldonse

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
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.TopCenter)
        ) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

            val width05 = screenWidth * 0.05f
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(y = 40.dp,x = -width05 ),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Cookie9tosquare(
                    onDrawerClick = onEDrawerClick,
                    drawerOpen = EdrawerOpen,

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
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        contentDescription = null,
                        tint = when (activeProvider.type) {
                            ProviderType.UNIFIED ->
                                MaterialTheme.colorScheme.tertiaryFixed

                            else ->
                                Color.Unspecified
                        }
                    )
                }

                Cookie9tosquare(
                    onDrawerClick = onSDrawerClick,
                    drawerOpen = SdrawerOpen,

                    ) {


                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = "Settings",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(30.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

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
                .background(MaterialTheme.colorScheme.surface)

        )



    }
}