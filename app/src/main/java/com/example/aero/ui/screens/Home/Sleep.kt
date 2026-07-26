package com.example.aero.ui.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aero.R
import com.example.aero.data.Provider
import com.example.aero.ui.components.Card
import com.example.aero.ui.components.WiggleProgressCircle
import com.example.aero.ui.theme.googlesans
import androidx.compose.ui.draw.clip
import com.example.aero.ui.components.CompositeBar

@Composable
fun Sleep(
    activeProvider: Provider,
    targetbool: Boolean

){
    var sleep by remember { mutableIntStateOf(0) }
    LaunchedEffect(activeProvider) {
        sleep = activeProvider.getSleep().toInt()
    }
    var target:Int = 10
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)

    ) {
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(400.dp)

        ) {
            Box(
                modifier = Modifier
                    .size(350.dp)
                    .align(Alignment.Center)
                    .offset(y = -30.dp)
            ) {
                WiggleProgressCircle(
                    value = sleep,
                    target = target
                )
                Icon(
                    painter = painterResource(R.drawable.round_dark_mode_24),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 70.dp)
                        .size(30.dp),
                    contentDescription = "moon",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Box(
                Modifier.align(Alignment.TopCenter)
                    .fillMaxWidth(0.3f)
                    .padding(top = 10.dp)
                    .height(30.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)


            ) {


                Text(
                    text = "Sleep",
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontSize = 13.sp,
                    fontFamily = googlesans,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight()
                    .align(Alignment.Center)
            ) {
                if(targetbool){
                Text(
                    text = "Target: " + target +"h",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 110.dp),
                    fontSize = 13.sp,
                    fontFamily = googlesans,
                    color = Color.White
                )}
                CompositeBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 70.dp)
                    ,
                    amounts = listOf(
                        7f, // Light
                        3f, // Deep
                        1f  // REM
                    ),
                    colors = listOf(
                        Color(0xFFC49CEA),
                        Color(0xFFB968E3),
                        Color(0xFF6520F5),
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(30.dp)
                        .offset(y = -30.dp)
                        .align(Alignment.BottomCenter)
                        ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color(0xFFC49CEA), CircleShape)
                        )
                        Text(
                            text = "Light",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color(0xFFB968E3), CircleShape)
                        )
                        Text(
                            text = "Deep",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color(0xFF6520F5), CircleShape)
                        )
                        Text(
                            text = "REM",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }
            }




        }
    }
}