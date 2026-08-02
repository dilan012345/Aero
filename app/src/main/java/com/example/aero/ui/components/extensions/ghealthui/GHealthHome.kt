package com.example.aero.ui.components.extensions.ghealthui

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.aero.ui.components.major.Background
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.Provider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.screens.old.HomeGrid
import com.example.aero.ui.components.major.HomeHeader

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GHealthHome(
    activeProvider: Provider,
    EdrawerOpen: Boolean,
    onEDrawerChange: (Boolean) -> Unit,
    onSDrawerChange: (Boolean) -> Unit,
    SdrawerOpen: Boolean,

){
    Background() {
            val context = LocalContext.current
            var Edraw = EdrawerOpen
            var Sdraw = SdrawerOpen
            val navigator = LocalNavigator.currentOrThrow
            val notification: Boolean = true
            val providers = listOf(
                GoogleHealthProvider(context),
                SamsungHealthProvider(context),
                StravaProvider(),
                UnifiedProvider(context)
            )
            var selectedProvider by remember {
                mutableStateOf(
                    ProviderSettings.getProvider()
                )
            }
            var showTargets by remember { mutableStateOf(false) }




            Log.d("DRAWER", Edraw.toString() )
            HomeHeader(
                activeProvider = activeProvider,
                notification = notification,
                onEDrawerClick = {
                    Edraw = !Edraw
                    onEDrawerChange(Edraw)

                },
                onSDrawerClick = {
                    Sdraw = !Sdraw
                    onSDrawerChange(Sdraw)
                },
                showTargets = showTargets,
                onShowTargetsChange = {
                    showTargets = !showTargets
                },
                EdrawerOpen = Edraw,
                SdrawerOpen = Sdraw,
            )
            Log.d("TARGETS", showTargets.toString())

            HomeGrid(
                activeProvider = activeProvider,
                target = showTargets
            )



        }
}