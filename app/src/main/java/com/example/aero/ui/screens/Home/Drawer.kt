package com.example.aero.ui.screens.Home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aero.R
import com.example.aero.data.Provider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.ui.components.BottomDrawer
import com.example.aero.ui.screens.Setup
import com.example.aero.ui.theme.googlesans
import com.example.aero.ui.theme.roboto
import com.example.aero.ui.theme.robotosb

@Composable
fun Drawer(
    draw: Boolean,
    activeProvider: Provider,
    onClose: () -> Unit,
    onProviderSelected: (ProviderType) -> Unit,
    onConnectClick: () -> Unit
){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val fivePercent = screenWidth * 0.05f
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
                        onClick = onClose
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
                modifier = Modifier.fillMaxSize()
            ) {

                BottomDrawer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
                {


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
                                    .clip(
                                        androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )
                                    .background(
                                        if (activeProvider.type == ProviderType.GOOGLE_HEALTH) {
                                            Color(0xFF4c453F)
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        }
                                    )
                                    .border(
                                        2.dp,
                                        if (activeProvider.type == ProviderType.GOOGLE_HEALTH) {
                                            MaterialTheme.colorScheme.tertiary
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        },
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )

                            ) {
                                IconButton(
                                    onClick = {
                                        ProviderSettings.saveProvider(ProviderType.GOOGLE_HEALTH)
                                        onProviderSelected(ProviderType.GOOGLE_HEALTH)
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.googlehealth),
                                        modifier = Modifier.size(70.dp)
                                            .padding(bottom = 25.dp),
                                        contentDescription = "Google Health",
                                        tint = Color.Unspecified // forces black otherwise
                                    )
                                }
                                Box(
                                    Modifier.align(Alignment.BottomCenter)
                                        .fillMaxWidth(0.8f)
                                        .padding(bottom = 10.dp)
                                        .height(35.dp)
                                        .clip(RoundedCornerShape(50.dp))
                                        .background(MaterialTheme.colorScheme.surfaceBright)
                                        .border(
                                            2.dp,
                                            if (activeProvider.type == ProviderType.GOOGLE_HEALTH) {
                                                MaterialTheme.colorScheme.tertiary
                                            } else {
                                                MaterialTheme.colorScheme.surface
                                            },
                                            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                                20.dp
                                            )
                                        )


                                ) {
                                    Text(
                                        text = "Google Health",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(
                                        androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )
                                    .background(
                                        if (activeProvider.type == ProviderType.SAMSUNG_HEALTH) {
                                            Color(0xFF4c453F)
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        }
                                    )
                                    .border(
                                        2.dp,
                                        if (activeProvider.type == ProviderType.SAMSUNG_HEALTH) {
                                            MaterialTheme.colorScheme.tertiary
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        },
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )

                            ) {
                                IconButton(
                                    onClick = {
                                        ProviderSettings.saveProvider(ProviderType.SAMSUNG_HEALTH)
                                        onProviderSelected(ProviderType.SAMSUNG_HEALTH)
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.samsunghealth),
                                        modifier = Modifier.size(80.dp)
                                            .padding(bottom = 25.dp),
                                        contentDescription = "Samsung Health",
                                        tint = Color.Unspecified // forces black otherwise
                                    )
                                }
                                Box(
                                    Modifier.align(Alignment.BottomCenter)
                                        .fillMaxWidth(0.8f)
                                        .padding(bottom = 10.dp)
                                        .height(35.dp)
                                        .clip(RoundedCornerShape(50.dp))
                                        .background(MaterialTheme.colorScheme.surfaceBright)
                                        .border(
                                            2.dp,
                                            if (activeProvider.type == ProviderType.SAMSUNG_HEALTH) {
                                                MaterialTheme.colorScheme.tertiary
                                            } else {
                                                MaterialTheme.colorScheme.surface
                                            },
                                            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                                20.dp
                                            )
                                        )


                                ) {
                                    Text(
                                        text = "Samsung Health",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(
                                        androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )
                                    .background(
                                        if (activeProvider.type == ProviderType.STRAVA) {
                                            Color(0xFF4c453F)
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        }
                                    )
                                    .border(
                                        2.dp,
                                        if (activeProvider.type == ProviderType.STRAVA) {
                                            MaterialTheme.colorScheme.tertiary
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        },
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )

                            ) {
                                IconButton(
                                    onClick = {

                                        ProviderSettings.saveProvider(ProviderType.STRAVA)
                                        onProviderSelected(ProviderType.STRAVA)
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.strava),
                                        modifier = Modifier.size(70.dp)
                                            .padding(bottom = 25.dp),
                                        contentDescription = "Google Health",
                                        tint = Color.Unspecified // forces black otherwise
                                    )
                                }
                                Box(
                                    Modifier.align(Alignment.BottomCenter)
                                        .fillMaxWidth(0.8f)
                                        .padding(bottom = 10.dp)
                                        .height(35.dp)
                                        .clip(RoundedCornerShape(50.dp))
                                        .background(MaterialTheme.colorScheme.surfaceBright)
                                        .border(
                                            2.dp,
                                            if (activeProvider.type == ProviderType.STRAVA) {
                                                MaterialTheme.colorScheme.tertiary
                                            } else {
                                                MaterialTheme.colorScheme.surface
                                            },
                                            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                                20.dp
                                            )
                                        )


                                ) {
                                    Text(
                                        text = "Strava",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(
                                        androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )
                                    .background(
                                        if (activeProvider.type == ProviderType.UNIFIED) {
                                            Color(0xFF4c453F)
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        }
                                    )
                                    .border(
                                        2.dp,
                                        if (activeProvider.type == ProviderType.UNIFIED) {
                                            MaterialTheme.colorScheme.tertiary
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        },
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                            20.dp
                                        )
                                    )

                            ) {
                                IconButton(
                                    onClick = {
                                        ProviderSettings.saveProvider(ProviderType.UNIFIED)
                                        onProviderSelected(ProviderType.UNIFIED)
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 25.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.unified),
                                        modifier = Modifier.size(70.dp),
                                        contentDescription = "Google Health",
                                        tint = Color.Unspecified // forces black otherwise
                                    )
                                }
                                Box(
                                    Modifier.align(Alignment.BottomCenter)
                                        .fillMaxWidth(0.8f)
                                        .padding(bottom = 10.dp)
                                        .height(35.dp)
                                        .clip(RoundedCornerShape(50.dp))
                                        .background(MaterialTheme.colorScheme.surfaceBright)
                                        .border(
                                            2.dp,
                                            if (activeProvider.type == ProviderType.UNIFIED) {
                                                MaterialTheme.colorScheme.tertiary
                                            } else {
                                                MaterialTheme.colorScheme.surface
                                            },
                                            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                                20.dp
                                            )
                                        )


                                ) {
                                    Text(
                                        text = "Unified",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = Color.White
                                    )
                                }
                            }
                        }


                        item(span = { GridItemSpan(2) }) {
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
                    Box(
                        Modifier.align(Alignment.TopStart)
                            .width(55.dp)
                            .padding(top = 25.dp, start = fivePercent)
                            .height(35.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(MaterialTheme.colorScheme.surfaceBright)


                    ) {
                        Icon(
                            painter = painterResource(R.drawable.close),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding()
                                .size(25.dp)
                                .clickable(
                                    onClick =  onClose
                                ),
                            contentDescription = "Google Health",
                            tint = Color.Unspecified // forces black otherwise
                        )
                    }



                    Box(
                        Modifier.align(Alignment.TopCenter)
                            .fillMaxWidth(0.35f)
                            .padding(top = 25.dp)
                            .height(35.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(MaterialTheme.colorScheme.surfaceBright)


                    ) {
                        Text(
                            text = "Extensions",
                            modifier = Modifier
                                .align(Alignment.Center),
                            fontSize = 15.sp,
                            fontFamily = googlesans,
                            color = Color.White
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .padding(top = 75.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
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
                        .fillMaxHeight()


                ) {


                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(130.dp)
                            .height(40.dp)
                            .offset(y = (0).dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceBright)
                            .clickable {
                                onConnectClick()
                            }
                    ) {


                        Row(
                            modifier = Modifier

                                .fillMaxWidth()
                                .height(90.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.outline_atr_24),

                                contentDescription = "atr",
                                modifier = Modifier.Companion,
                                tint = Color.White,
                            )

                            Text(
                                text = "Connect",
                                fontSize = 15.sp,
                                fontFamily = roboto,
                                color = Color.White

                            )
                        }

                    }
                }
            }
        }
    }
}