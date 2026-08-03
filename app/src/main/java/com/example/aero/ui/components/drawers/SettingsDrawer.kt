package com.example.aero.ui.components.drawers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aero.R
import com.example.aero.data.Provider
import com.example.aero.data.ProviderType

import com.example.aero.ui.theme.boldonse
import com.example.aero.ui.theme.coolvetica
import com.example.aero.ui.theme.googlesans
import com.example.aero.ui.theme.roboto
import com.example.aero.ui.theme.samsungsharpsans
import com.example.aero.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

enum class SettingType {
    DARK_MODE,
    NOTIFICATIONS,
    EXPERIMENTAL,
    DEVELOPER_MODE
}
@Composable
fun SettingsDrawer(
    draw: Boolean,
    activeProvider: Provider,
    onClose: () -> Unit,
    onProviderSelected: (ProviderType) -> Unit,


    ) {
    val settingsViewModel: SettingsViewModel = koinViewModel()
    val darkTheme by settingsViewModel.darkTheme.collectAsState()
    val primary = when (activeProvider.type) {

        ProviderType.GOOGLE_HEALTH ->
            Color(0xFFFF5722)


        ProviderType.SAMSUNG_HEALTH ->
            Color(0xFF67BB94)


        ProviderType.STRAVA ->
            Color(0xFFFC4C02)

        ProviderType.UNIFIED ->
            Color(0xFFA6A6D3)


        else ->
            Color(0xFF0B5048)

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
                    data class SettingItem(
                        val type: SettingType,
                        val title: String,


                        )

                    val settingsList = remember {
                        mutableStateListOf(
                            SettingItem(
                                SettingType.DARK_MODE,
                                "Dark Mode"
                            ),
                            SettingItem(SettingType.NOTIFICATIONS, "Enable Notifications"),
                            SettingItem(SettingType.EXPERIMENTAL, "Show experimental features"),
                            SettingItem(SettingType.DEVELOPER_MODE, "Developer Mode")
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(900.dp)

                            .graphicsLayer {
                                compositingStrategy = CompositingStrategy.Offscreen
                            }
                            .drawWithCache {
                                val gradient = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.05f to Color.Transparent,
                                        0.1f to Color(0x14000000),
                                        0.2f to Color.Black,
                                        0.92f to Color.Black,
                                        1.0f to Color.Transparent
                                    )
                                )

                                onDrawWithContent {
                                    drawContent()
                                    drawRect(
                                        brush = gradient,
                                        blendMode = BlendMode.DstIn
                                    )
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)


                            ) {
                                Text(
                                    text = when (activeProvider.type) {
                                        ProviderType.GOOGLE_HEALTH -> "Settings"
                                        ProviderType.SAMSUNG_HEALTH -> "Settings"
                                        ProviderType.STRAVA -> ""
                                        else -> "Unified"
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(top = 55.dp),
                                    fontSize = 23.sp,
                                    fontFamily = when (activeProvider.type) {
                                        ProviderType.GOOGLE_HEALTH -> googlesans
                                        ProviderType.SAMSUNG_HEALTH -> samsungsharpsans
                                        ProviderType.UNIFIED -> boldonse
                                        else -> coolvetica
                                    },
                                    color = MaterialTheme.colorScheme.tertiaryFixed
                                )
                                if (activeProvider.type == ProviderType.STRAVA) {
                                    Image(
                                        painter = painterResource(R.drawable.stravatext),
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                            .width(180.dp)
                                            .height(60.dp)
                                            .offset(y = 40.dp),
                                        contentDescription = "Strava logo",

                                        )
                                } else {

                                }
                            }
                        }


                        items(settingsList.size) { index ->
                            val setting = settingsList[index]
                            val title = setting.title
                            val shape = when (index) {
                                0 -> RoundedCornerShape(
                                    topStart = 20.dp,
                                    topEnd = 20.dp,
                                    bottomEnd = 10.dp,
                                    bottomStart = 10.dp
                                )  // first item
                                settingsList.size - 1 -> RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp,
                                    topEnd = 10.dp,
                                    topStart = 10.dp
                                ) // last item
                                else -> RoundedCornerShape(10.dp) // middle items
                            }

                            Box(
                                modifier = Modifier
                                    .height(70.dp)
                                    .fillMaxWidth(0.95f)
                                    .clip(shape)
                                    .border(1.dp, primary, shape)

                                    .background(MaterialTheme.colorScheme.tertiary)

                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 15.sp,
                                    modifier = Modifier
                                        .padding(start = 20.dp, end = 40.dp)
                                        .align(Alignment.CenterStart),
                                    fontFamily = roboto
                                )
                                Switch(
                                    modifier = Modifier
                                        .padding(end = 40.dp)
                                        .align(Alignment.CenterEnd),
                                    checked = when (setting.type) {
                                        SettingType.DARK_MODE -> darkTheme
                                        else -> false
                                    },
                                    onCheckedChange = { checked ->
                                        when (setting.type) {
                                            SettingType.DARK_MODE ->
                                                settingsViewModel.updateDarkTheme(checked)

                                            else -> {}
                                        }
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.background,      // circle when ON
                                        checkedTrackColor = primary, // track when ON
                                        uncheckedThumbColor = Color.Gray,     // circle when OFF
                                        uncheckedTrackColor = MaterialTheme.colorScheme.background // track when OFF

                                    )
                                )
                            }
                        }

                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(500.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}