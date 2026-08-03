package com.example.aero.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.components.major.Background
import com.example.aero.ui.components.drawers.ExtensionDraw
import com.example.aero.ui.components.drawers.SettingsDrawer
import com.example.aero.ui.components.extensions.ghealthui.GHealthHome
import com.example.aero.ui.components.extensions.shealthui.home.SHealthHome
import com.example.aero.ui.components.extensions.stravaui.StravaHome
import com.example.aero.ui.components.extensions.unifiedui.home.UnifiedHome

class `Global Home`(): Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    override fun Content() {
        Background {
            val context = LocalContext.current

            val providers = remember(context) {
                listOf(
                    GoogleHealthProvider(context),
                    SamsungHealthProvider(context),
                    StravaProvider(),
                    UnifiedProvider(context)
                )
            }
            //creates all providers

            var selectedProvider by remember {
                mutableStateOf(
                    ProviderSettings.getProvider()
                )
            }
            //

            val activeProvider = providers.first {
                it.type == selectedProvider
            }
            var Edraw by rememberSaveable { mutableStateOf(false) }
            var Sdraw by rememberSaveable { mutableStateOf(false) }
            when (activeProvider.type) {
                ProviderType.GOOGLE_HEALTH -> {
                    GHealthHome(
                        activeProvider = activeProvider,
                        EdrawerOpen = Edraw,
                        onEDrawerChange = { Edraw = it },
                        onSDrawerChange = { Sdraw = it },
                        SdrawerOpen = Sdraw

                    )
                }

                ProviderType.SAMSUNG_HEALTH -> {
                    SHealthHome(
                        activeProvider = activeProvider,
                        EdrawerOpen = Edraw,
                        onEDrawerChange = { Edraw = it },
                        onSDrawerChange = { Sdraw = it },
                        SdrawerOpen = Sdraw
                    )
                }

                ProviderType.UNIFIED -> {
                    UnifiedHome(
                        activeProvider = activeProvider,
                        EdrawerOpen = Edraw,
                        onEDrawerChange = { Edraw = it },
                        onSDrawerChange = { Sdraw = it },
                        SdrawerOpen = Sdraw
                    )
                }

                ProviderType.STRAVA -> {
                    StravaHome(
                        activeProvider = activeProvider,
                        EdrawerOpen = Edraw,
                        onEDrawerChange = { Edraw = it },
                        onSDrawerChange = { Sdraw = it },
                        SdrawerOpen = Sdraw
                    )
                }
            }
            val navigator = LocalNavigator.currentOrThrow
            ExtensionDraw(
                draw = Edraw,
                activeProvider = activeProvider,
                onClose = {
                    Edraw = false
                },
                onProviderSelected = {
                    selectedProvider = it
                },
                onConnectClick = {
                    navigator.push(Setup())
                }
            )
            SettingsDrawer(
                draw = Sdraw,
                activeProvider = activeProvider,
                onClose = {
                    Sdraw = false
                },
                onProviderSelected = {
                    selectedProvider = it
                },

                )
        }
    }
}