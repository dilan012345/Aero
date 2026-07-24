package com.example.aero.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.R
import com.example.aero.ui.components.Background
import com.example.aero.ui.components.Card
import com.example.aero.ui.components.Stamp
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.unit.sp
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.ui.components.Bar
import com.example.aero.ui.components.BarChart
import com.example.aero.ui.components.BottomDrawer
import com.example.aero.ui.theme.coolvetica
import com.example.aero.ui.theme.googlesans
import com.example.aero.ui.theme.recentgrotesk
import com.example.aero.ui.theme.roboto
import com.example.aero.ui.theme.robotosb
import com.example.aero.ui.theme.samsungsharpsans
import com.example.aero.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

class Home(): Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        Background(){
            var draw by remember { mutableStateOf(false) }
            val navigator = LocalNavigator.currentOrThrow
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val fivePercent = screenWidth * 0.05f
            val notification: Boolean = true
            val providers = listOf(
                GoogleHealthProvider(),
                SamsungHealthProvider(),
                StravaProvider()
            )
            var selectedProvider by rememberSaveable {
                mutableStateOf(ProviderType.GOOGLE_HEALTH)
            }

            val activeProvider = providers.first {
                it.type == selectedProvider
            }
            var steps by remember { mutableIntStateOf(0) }
            LaunchedEffect(activeProvider) {
                steps = activeProvider.getSteps()
            }

            var cal by remember { mutableIntStateOf(0) }
            LaunchedEffect(activeProvider) {
                cal = activeProvider.getCalories()
            }

            val extension: List<Color> = when (activeProvider.type) {
                ProviderType.GOOGLE_HEALTH -> listOf(

                    Color(0xFF2C40C2),
                    Color(0xAA4A90E2),
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

                else -> listOf(
                    Color(0xFF0B5048),
                    Color(0xFF72BB67),
                    Color.Transparent
                )
            }










            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = extension
                        )
                    )
            ){
                // Noise layer
                val noisePoints = remember {
                    List(15000) {
                        Offset(
                            kotlin.random.Random.nextFloat(),
                            kotlin.random.Random.nextFloat()
                        )
                    }
                }

                Canvas(
                    modifier = Modifier.matchParentSize()
                ) {
                    noisePoints.forEach {
                        val y = it.y * size.height

                        val intensity = 1f - (it.y) // 1 at top, 0 at bottom

                        drawCircle(
                            color = if (kotlin.random.Random.nextBoolean()) {
                                Color.White.copy(alpha = 0.18f * intensity)
                            } else {
                                Color.Black.copy(alpha = 0.18f * intensity)
                            },
                            radius = 2f,
                            center = Offset(
                                it.x * size.width,
                                y
                            )
                        )
                    }
                }
            }
            Text(
                text = when (activeProvider.type) {
                    ProviderType.GOOGLE_HEALTH -> "Google Health"
                    ProviderType.SAMSUNG_HEALTH -> "Samsung Health"
                    ProviderType.STRAVA -> ""
                    else -> "Unified"
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 55.dp, start = 20.dp),
                fontSize = 23.sp,
                fontFamily = when (activeProvider.type) {
                    ProviderType.GOOGLE_HEALTH -> googlesans
                    ProviderType.SAMSUNG_HEALTH -> samsungsharpsans
                    ProviderType.STRAVA -> roboto
                    else -> coolvetica
                },
                color = Color.White
            )
            if (activeProvider.type == ProviderType.STRAVA) {
                Image(
                    painter = painterResource(R.drawable.stravatext),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .width(180.dp)
                        .height(60.dp)
                        .offset(y=40.dp),
                    contentDescription = "Strava logo",

                )
            } else {

            }









            LazyVerticalGrid(

                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 140.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ){
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)

                    ) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(100.dp)

                        ) {
                            Text(
                                text = steps.toString(),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 15.dp),
                                fontSize = 30.sp,
                                fontFamily = googlesans,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Box(
                                Modifier.align(Alignment.BottomCenter)
                                    .fillMaxWidth(0.4f)
                                    .padding(bottom = 10.dp)
                                    .height(30.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright)


                            ){
                                Text(
                                    text = "Steps" ,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    fontSize = 13.sp,
                                    fontFamily = googlesans,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)

                    ) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(100.dp)

                        ) {
                            Text(
                                text = cal.toString(),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 15.dp),
                                fontSize = 30.sp,
                                fontFamily = googlesans,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Box(
                                Modifier.align(Alignment.BottomCenter)
                                    .fillMaxWidth(0.5f)
                                    .padding(bottom = 10.dp)
                                    .height(30.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright)


                            ){
                                Text(
                                    text = "Calories" ,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    fontSize = 13.sp,
                                    fontFamily = googlesans,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                item (span = { GridItemSpan(2) }){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)


                    ) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(400.dp)

                        ) {
                            Box(
                                Modifier.align(Alignment.TopCenter)
                                    .fillMaxWidth(0.35f)
                                    .padding(top = 10.dp)
                                    .height(30.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright)


                            ){
                                Text(
                                    text = "This week" ,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    fontSize = 13.sp,
                                    fontFamily = googlesans,
                                    color = Color.White
                                )
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
                                        Color(0xFF72BB67)


                                }

                            )
                        }
                    }
                }
                item (span = { GridItemSpan(2) }){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)

                    ) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(350.dp)

                        ) {


                        }
                    }
                }
                item (span = { GridItemSpan(2) }){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Transparent)
                    ){

                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .align(Alignment.Center)
            ){
                Stamp(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 60.dp)
                        .size(50.dp),
                    content = {

                        IconButton(
                            onClick = { draw = !draw },
                            modifier = Modifier
                        ) {
                            Icon(
                                painter = painterResource(
                                    when (activeProvider.type) {
                                    ProviderType.GOOGLE_HEALTH -> R.drawable.googlehealth
                                    ProviderType.SAMSUNG_HEALTH -> R.drawable.samsunghealth
                                    ProviderType.STRAVA -> R.drawable.strava
                                    else -> R.drawable.ic_launcher_background
                                }
                                ),
                                modifier = Modifier.size(30.dp),
                                contentDescription = "Google Health",
                                tint = Color.Unspecified // forces black otherwise
                            )
                        }

                    }

                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable(
                            onClick = {
                                navigator.push(Profile(activeProvider))
                            }
                        )



                ){
                    Icon(
                        painter = painterResource(R.drawable.round_person_2_24),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(30.dp),
                        contentDescription = "Profile",
                        tint =  MaterialTheme.colorScheme.secondary
                    )

                }

                if (notification) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 2.dp, end = 2.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .offset(x = 15.dp, y = 5.dp)


                    ) {}
                }
                else{

                }




            }




            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = draw,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xD5101010))
                            .clickable(
                                onClick = { draw = !draw }
                            )
                    )
                }

                AnimatedVisibility(
                    visible = draw,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    )
                ) {

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth(0.5f)
                            .clip(CircleShape)
                            .height(50.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .offset(y = 50.dp)
                    )
                    BottomDrawer(modifier = Modifier){


                        LazyVerticalGrid(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth(0.9f)
                                .height(400.dp),
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(top = 120.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),

                        ) {

                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if(activeProvider.type == ProviderType.GOOGLE_HEALTH){Color(0xFF4c453F)} else {
                                            MaterialTheme.colorScheme.surface
                                        }
                                        )
                                        .border(2.dp,if(activeProvider.type == ProviderType.GOOGLE_HEALTH){MaterialTheme.colorScheme.tertiary} else {
                                            MaterialTheme.colorScheme.surface
                                        }, shape = RoundedCornerShape(20.dp))

                                ) {
                                    IconButton(
                                    onClick = {
                                        selectedProvider = ProviderType.GOOGLE_HEALTH
                                              },
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.googlehealth),
                                        modifier = Modifier.size(70.dp),
                                        contentDescription = "Google Health",
                                        tint = Color.Unspecified // forces black otherwise
                                    )
                                }}
                            }
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if(activeProvider.type == ProviderType.SAMSUNG_HEALTH){Color(0xFF4c453F)} else {
                                            MaterialTheme.colorScheme.surface
                                        }
                                        )
                                        .border(2.dp,if(activeProvider.type == ProviderType.SAMSUNG_HEALTH){MaterialTheme.colorScheme.tertiary} else {
                                            MaterialTheme.colorScheme.surface
                                        }, shape = RoundedCornerShape(20.dp))

                                ) {
                                    IconButton(
                                        onClick = {
                                            selectedProvider = ProviderType.SAMSUNG_HEALTH
                                                  },
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.samsunghealth),
                                            modifier = Modifier.size(70.dp),
                                            contentDescription = "Google Health",
                                            tint = Color.Unspecified // forces black otherwise
                                        )
                                    }
                                }
                            }
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if(activeProvider.type == ProviderType.STRAVA){Color(0xFF4c453F)} else {
                                            MaterialTheme.colorScheme.surface
                                        }
                                        )
                                        .border(2.dp,if(activeProvider.type == ProviderType.STRAVA){MaterialTheme.colorScheme.tertiary} else {
                                            MaterialTheme.colorScheme.surface
                                        }, shape = RoundedCornerShape(20.dp))

                                ) {
                                    IconButton(
                                        onClick = {
                                            selectedProvider = ProviderType.STRAVA
                                        },
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.strava),
                                            modifier = Modifier.size(70.dp),
                                            contentDescription = "Google Health",
                                            tint = Color.Unspecified // forces black otherwise
                                        )
                                    }
                                }
                            }


                            item(span = { GridItemSpan(2) }){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 30.dp,
                                        topEnd = 30.dp,
                                        bottomStart = 5.dp,
                                        bottomEnd = 5.dp
                                    )
                                )
                                .background(Color(0xFF282623))
                        )
                        Icon(
                            painter = painterResource(R.drawable.close),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 30.dp, start = fivePercent)
                                .size(25.dp)
                                .clickable(
                                    onClick = { draw = !draw }
                                ),
                            contentDescription = "Google Health",
                            tint = Color.Unspecified // forces black otherwise
                        )


                        Text(
                            text = "Extensions",
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 30.dp),
                            fontSize = 23.sp,
                            fontFamily = roboto,
                            color = Color.White
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .padding(top = 75.dp),
                            horizontalArrangement =  Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(screenWidth * 0.28f)
                                    .height(5.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF4c453F))
                            )

                            Box(
                                modifier = Modifier
                                    .width(screenWidth * 0.4f)
                                    .height(5.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.tertiary)
                            )

                            Box(
                                modifier = Modifier
                                    .width(screenWidth * 0.28f)
                                    .height(5.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF4c453F))
                            )
                        }
                    }

                    Box(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        ExtendedFloatingActionButton(
                            onClick = {navigator.push(Setup())},
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .width(150.dp)
                                .height(50.dp)
                                .offset(y = (-70).dp)
                                .clip(RoundedCornerShape(50)),
                            containerColor = MaterialTheme.colorScheme.surface
                        ) {



                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .padding(top = 13.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Icon(
                                    painter = painterResource(R.drawable.outline_atr_24),

                                    contentDescription = "atr",
                                    modifier = Modifier,
                                    tint = MaterialTheme.colorScheme.tertiary,
                                )

                                Text(
                                    text = "Connect",
                                    fontSize = 20.sp,
                                    fontFamily = robotosb,
                                    color = MaterialTheme.colorScheme.tertiary

                                )
                            }
                            Text(
                                text = "Setup",
                                fontSize = 20.sp,
                                fontFamily = robotosb
                            )
                        }
                    }
                }
            }








        }
    }
}

