package com.example.aero.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.R
import com.example.aero.ui.components.Background
import com.example.aero.ui.components.Card
import com.example.aero.ui.theme.recentgrotesk
import com.example.aero.ui.theme.robotosb
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
class Setup: Screen{
    @Composable
    override fun Content(){
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
                fontFamily = robotosb,

            )
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 150.dp)
                    .fillMaxWidth(0.9f)
                    .height(250.dp)


            ) {
                Text(
                    text = "Aero utilises Health Connect API alongside its own telemetry calculations to calculate footsteps, it then merges them with other extensions, for example, Google Health to determine more reliable data",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 0.dp),
                    fontSize = 23.sp,
                    fontFamily = robotosb,
                    color = Color.White,

                )
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