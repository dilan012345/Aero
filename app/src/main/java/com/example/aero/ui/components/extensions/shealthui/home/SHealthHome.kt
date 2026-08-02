package com.example.aero.ui.components.extensions.shealthui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.Provider
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.components.major.HomeHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SHealthHome(
    activeProvider: Provider,
    EdrawerOpen: Boolean,
    onEDrawerChange: (Boolean) -> Unit,
    onSDrawerChange: (Boolean) -> Unit,
    SdrawerOpen: Boolean,
){
    val context = LocalContext.current
    var Edraw = EdrawerOpen
    var Sdraw = SdrawerOpen
    var showTargets by rememberSaveable { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow
    val notification: Boolean = true
    val providers = remember(context) {
        listOf(
            GoogleHealthProvider(context),
            SamsungHealthProvider(context),
            StravaProvider(),
            UnifiedProvider(context)
        )
    }

    val pullState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    var isRefreshing by remember { mutableStateOf(false) }
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current
    val primary = remember(activeProvider.type) {
        when (activeProvider.type) {
            ProviderType.GOOGLE_HEALTH ->
                Color(0xFFFF5722)

            ProviderType.SAMSUNG_HEALTH ->
                Color(0xFF67BB94)

            ProviderType.STRAVA ->
                Color(0xFFFC4C02)

            ProviderType.UNIFIED ->
                Color(0xFF598FB9)

            else ->
                Color(0xFF0B5048)
        }
    }
    var syncComplete by remember { mutableStateOf(false) }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                syncComplete = false

                providers.forEach { it.sync() }

                syncComplete = true

                delay(1300)

                isRefreshing = false
                syncComplete = false
            }
        },
        state = pullState,
        modifier = Modifier.fillMaxSize(),
        indicator = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                PullToRefreshDefaults.Indicator(
                    state = pullState,
                    isRefreshing = isRefreshing,
                    color = primary,
                    containerColor = MaterialTheme.colorScheme.tertiary
                )


            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
                HomeHeader(
                    activeProvider = activeProvider,
                    notification = notification,
                    onEDrawerClick = {
                        onEDrawerChange(!Edraw)

                    },
                    onSDrawerClick = {

                        onSDrawerChange(!Sdraw)
                    },
                    showTargets = showTargets,
                    onShowTargetsChange = {
                        showTargets = !showTargets
                    },
                    EdrawerOpen = Edraw,
                    SdrawerOpen = Sdraw,
                )

            }
            item {
                Box {
                    sHealthStepsCard(
                        screenWidth = screenWidth,
                        activeProvider = activeProvider
                    )
                }

            }
            item {
                Box {
                    sHealthSleepCard(
                        screenWidth = screenWidth,
                        activeProvider = activeProvider,
                    )
                }
            }
            item {
                Box {
                    sHealthWaterCard(
                        screenWidth = screenWidth,
                        activeProvider = activeProvider,
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )
            }

        }
    }

}