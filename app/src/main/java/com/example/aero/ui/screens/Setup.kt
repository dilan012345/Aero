package com.example.aero.ui.screens

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.PermissionController
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.R
import com.example.aero.ui.components.Background
import com.example.aero.ui.components.Card
import com.example.aero.ui.theme.recentgrotesk
import com.example.aero.ui.theme.robotosb
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.healthConnect.HealthConnectManager
import com.example.aero.ui.theme.googlesans
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import com.example.aero.ui.screens.Steps.Steps
import kotlinx.coroutines.launch

private val permissions = setOf(
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getReadPermission(ActiveCaloriesBurnedRecord::class),
    HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class)

)

class Setup: Screen{
    @Composable
    override fun Content(){
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = PermissionController.createRequestPermissionResultContract()
        ) { grantedPermissions ->
            println("Granted: $grantedPermissions")
            if (grantedPermissions.containsAll(permissions)) {
                println("Health Connect permissions granted")
            } else {
                println("Permissions missing")
            }
        }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val manager = HealthConnectManager(context)
        if (manager.isAvailable()) {

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
                                Color(0xFF363535),
                                Color(0xFF1F1F1F),
                                Color.Transparent
                            )
                        )
                    )
            )

            Text(
                text = "Aero: Setup",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp),
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = googlesans,

            )
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 120.dp)
                    .fillMaxWidth(0.9f)
                    .height(650.dp)


            ) {

                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {
                                println("clicked")
                                println("Requesting: $permissions")
                                scope.launch {
                                    val granted = manager.healthConnectClient
                                        .permissionController
                                        .getGrantedPermissions()

                                    if (!granted.containsAll(permissions)) {
                                        permissionLauncher.launch(permissions)
                                    }
                                }
                            }

                    ) {

                        Box(
                            Modifier.align(Alignment.Center)
                                .fillMaxWidth(0.4f)
                                .padding(bottom = 10.dp)
                                .height(30.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(MaterialTheme.colorScheme.surfaceBright)


                        ) {
                            Text(
                                text = "Permissions",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 13.sp,
                                fontFamily = googlesans,
                                color = Color.White
                            )
                        }
                    }


            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable(
                        onClick = {
                            navigator.pop()
                        }
                    )



            ){
                Icon(
                    painter = painterResource(R.drawable.round_person_2_24),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp),
                    contentDescription = "Profile",
                    tint = Color.White
                )

            }





























        }
    }
}