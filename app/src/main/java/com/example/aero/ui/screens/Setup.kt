package com.example.aero.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.R
import com.example.aero.ui.components.major.Background
import com.example.aero.ui.components.major.Card
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.healthConnect.HealthConnectManager
import com.example.aero.ui.theme.googlesans
import androidx.health.connect.client.permission.HealthPermission.Companion.getReadPermission
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import com.example.aero.data.DebugLogger
import com.example.aero.samsung.SamsungHealthManager
import com.example.aero.ui.theme.boldonse
import kotlinx.coroutines.launch
import kotlin.random.Random


object HealthPermissions {
    val permissions = setOf(
        getReadPermission(StepsRecord::class),
        getReadPermission(ActiveCaloriesBurnedRecord::class),
        getReadPermission(TotalCaloriesBurnedRecord::class),
        getReadPermission(ExerciseSessionRecord::class),
        getReadPermission(SleepSessionRecord::class))
}
class Setup: Screen{
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    override fun Content(){
        DebugLogger.log(
            "SETUP Recomposed",
            Color.Green
        )

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = PermissionController.createRequestPermissionResultContract()
        ) { grantedPermissions ->
            println("Granted: $grantedPermissions")
            if (grantedPermissions.containsAll(HealthPermissions.permissions)) {
                DebugLogger.log("PERMISSIONS GRANTED$grantedPermissions",Color.Yellow)

            } else {
                println("Permissions missing")
            }
        }

        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val manager = HealthConnectManager(context)

        val activity = context as Activity

        val samsungManager = remember {
            SamsungHealthManager(context)
        }

        Background(){
            val navigator = LocalNavigator.currentOrThrow

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF623960),
                                Color(0xFF341634),
                                Color.Transparent
                            )
                        )
                    )
            ){
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


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(
                    top = 30.dp,
                    bottom = 30.dp
                )
            ) {
                item{
                    Text(
                        text = "Aero: Connections",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 25.dp),
                        color = MaterialTheme.colorScheme.tertiaryFixed,
                        fontSize = 30.sp,
                        fontFamily = boldonse,

                        )
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ){
                        Text(
                            text = "Click the card to bring up permission menu. Read notices first!",
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                                .fillMaxWidth(0.85f),
                            fontSize = 13.sp,
                            fontFamily = googlesans,
                            color = MaterialTheme.colorScheme.tertiaryFixed
                        )
                    }
                }
                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(330.dp)
                            .clickable {

                                println("clicked")
                                println("Requesting: $HealthPermissions.permissions")

                                scope.launch {
                                    scope.launch {

                                        val granted = manager.healthConnectClient
                                            .permissionController
                                            .getGrantedPermissions()

                                        if (granted.containsAll(HealthPermissions.permissions)) {

                                            val intent = Intent(
                                                HealthConnectClient.ACTION_HEALTH_CONNECT_SETTINGS
                                            )

                                            context.startActivity(intent)

                                        } else {

                                            permissionLauncher.launch(
                                                HealthPermissions.permissions
                                            )
                                        }
                                    }
                                }
                            }
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .padding(top = 30.dp)
                                    .fillMaxWidth(0.6f)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "Health Connect Permissions",
                                    fontSize = 13.sp,
                                    fontFamily = googlesans,
                                    color = MaterialTheme.colorScheme.tertiaryFixed
                                )
                            }


                            Box(
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth(0.4f)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright),
                                contentAlignment = Alignment.Center
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.googlehealth),
                                        modifier = Modifier.size(30.dp),
                                        contentDescription = "Google Health",
                                        tint = Color.Unspecified
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.samsunghealth),
                                        modifier = Modifier.size(30.dp),
                                        contentDescription = "Samsung Health",
                                        tint = Color.Unspecified
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.strava),
                                        modifier = Modifier.size(30.dp),
                                        contentDescription = "Strava",
                                        tint = Color.Unspecified
                                    )
                                }
                            }


                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(top = 20.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(MaterialTheme.colorScheme.surfaceBright)
                                        .border(
                                            1.dp,
                                            Color(0xFF67BB94),
                                            RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = "Samsung Health does not write all data to health connect, enable both API SDK permissions and Health connect permissions to access all data.",
                                        modifier = Modifier
                                            .fillMaxWidth(0.85f),
                                        fontSize = 13.sp,
                                        fontFamily = googlesans,
                                        color = MaterialTheme.colorScheme.tertiaryFixed
                                    )
                                }


                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(MaterialTheme.colorScheme.surfaceBright)
                                        .border(
                                            1.dp,
                                            Color(0xFFFC4C02),
                                            RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = "Strava planned to be supported but not currently implemented. Check github to see latest info",
                                        modifier = Modifier
                                            .fillMaxWidth(0.85f),
                                        fontSize = 13.sp,
                                        fontFamily = googlesans,
                                        color = MaterialTheme.colorScheme.tertiaryFixed
                                    )
                                }
                            }
                        }
                    }
                }


                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clickable {

                                scope.launch {
                                    samsungManager.connect()
                                    samsungManager.requestPermissions(activity)
                                }

                            }
                    ) {

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .padding(top = 40.dp)
                                    .fillMaxWidth(0.6f)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "Samsung SDK API Permissions",
                                    fontSize = 13.sp,
                                    fontFamily = googlesans,
                                    color = MaterialTheme.colorScheme.tertiaryFixed
                                )
                            }


                            Box(
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth(0.4f)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.samsunghealth),
                                    modifier = Modifier.size(30.dp),
                                    contentDescription = "Samsung Health",
                                    tint = Color.Unspecified
                                )
                            }


                            Spacer(
                                modifier = Modifier.height(30.dp)
                            )


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(MaterialTheme.colorScheme.surfaceBright)
                                    .border(
                                        1.dp,
                                        Color(0xFF67BB94),
                                        RoundedCornerShape(20.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "Samsung Health, via health connect does not send calorie data, instead use both their official API to receive calorie info and health connect to receive other data",
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f),
                                    fontSize = 13.sp,
                                    fontFamily = googlesans,
                                    color = MaterialTheme.colorScheme.tertiaryFixed
                                )
                            }
                        }
                    }
                }
            }




























        }
    }
}