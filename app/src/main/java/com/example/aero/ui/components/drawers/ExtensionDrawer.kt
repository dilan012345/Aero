package com.example.aero.ui.components.drawers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aero.R
import com.example.aero.data.Provider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.ui.theme.googlesans

@Composable
fun Modifier.providerCardStyle(
    providerType: ProviderType,
    selected: Boolean
): Modifier {
    val shape = RoundedCornerShape(20.dp)

    val providerColor = when (providerType) {
        ProviderType.GOOGLE_HEALTH -> Color(0xFF6799BB)
        ProviderType.SAMSUNG_HEALTH -> Color(0xFF67BB94)
        ProviderType.STRAVA -> Color(0xFFFC4C02)
        ProviderType.UNIFIED -> Color(0xFFA6A6D3)
    }

    return this
        .clip(shape)
        .background(
            if (selected) {
                providerColor.copy(alpha = 0.15f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
        .border(
            BorderStroke(
                2.dp,
                if (selected) {
                    providerColor
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
            shape
        )
}
@Composable
fun ExtensionDraw(
    draw: Boolean,
    activeProvider: Provider,
    onClose: () -> Unit,
    onProviderSelected: (ProviderType) -> Unit,
    onConnectClick: () -> Unit,

) {

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
                        .align(Alignment.BottomCenter),
                    activeProvider = activeProvider
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
                                    .providerCardStyle(
                                        providerType = ProviderType.GOOGLE_HEALTH,
                                        selected = activeProvider.type == ProviderType.GOOGLE_HEALTH
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
                                        .providerCardStyle(
                                            providerType = ProviderType.GOOGLE_HEALTH,
                                            selected = activeProvider.type == ProviderType.GOOGLE_HEALTH
                                        )


                                ) {
                                    Text(
                                        text = "Google Health",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = MaterialTheme.colorScheme.tertiaryFixed
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
                                    .providerCardStyle(
                                        providerType = ProviderType.SAMSUNG_HEALTH,
                                        selected = activeProvider.type == ProviderType.SAMSUNG_HEALTH
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
                                        .providerCardStyle(
                                            providerType = ProviderType.SAMSUNG_HEALTH,
                                            selected = activeProvider.type == ProviderType.SAMSUNG_HEALTH
                                        )


                                ) {
                                    Text(
                                        text = "Samsung Health",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = MaterialTheme.colorScheme.tertiaryFixed
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
                                    .providerCardStyle(
                                        providerType = ProviderType.STRAVA,
                                        selected = activeProvider.type == ProviderType.STRAVA
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
                                        .providerCardStyle(
                                            providerType = ProviderType.STRAVA,
                                            selected = activeProvider.type == ProviderType.STRAVA
                                        )


                                ) {
                                    Text(
                                        text = "Strava",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = MaterialTheme.colorScheme.tertiaryFixed
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
                                    .providerCardStyle(
                                        providerType = ProviderType.UNIFIED,
                                        selected = activeProvider.type == ProviderType.UNIFIED
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
                                        .providerCardStyle(
                                            providerType = ProviderType.UNIFIED,
                                            selected = activeProvider.type == ProviderType.UNIFIED
                                        )


                                ) {
                                    Text(
                                        text = "Unified",
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        fontSize = 15.sp,
                                        fontFamily = googlesans,
                                        color = MaterialTheme.colorScheme.tertiaryFixed
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
                }

            }
















        }
    }
}