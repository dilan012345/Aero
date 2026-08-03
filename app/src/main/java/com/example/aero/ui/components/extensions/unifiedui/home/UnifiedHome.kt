package com.example.aero.ui.components.extensions.unifiedui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialShapes.Companion.Arrow
import androidx.compose.material3.MaterialShapes.Companion.Circle
import androidx.compose.material3.MaterialShapes.Companion.Fan
import androidx.compose.material3.MaterialShapes.Companion.Oval
import androidx.compose.material3.MaterialShapes.Companion.Pill
import androidx.compose.material3.MaterialShapes.Companion.SemiCircle
import androidx.compose.material3.MaterialShapes.Companion.Slanted
import androidx.compose.material3.MaterialShapes.Companion.Square
import androidx.compose.material3.MaterialShapes.Companion.Triangle
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.Provider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.components.major.HomeHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@ExperimentalMaterial3ExpressiveApi
@Composable
fun UnifiedHome(
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
    var selectedProvider by remember {
        mutableStateOf(
            ProviderSettings.getProvider()
        )
    }

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current
    var isRefreshing by remember { mutableStateOf(false) }

    val pullState = rememberPullToRefreshState()
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
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var syncComplete by remember { mutableStateOf(false) }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                syncComplete = false

                providers.forEach { it.sync() }

                syncComplete = true

                delay(2000)

                isRefreshing = false
                syncComplete = false
            }
        },
        state = pullState,
        modifier = Modifier.fillMaxSize(),
        indicator = {
            if (isRefreshing) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 30.dp)
                        .graphicsLayer {
                            rotationZ = 180f
                            transformOrigin = TransformOrigin.Center
                        },
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator(
                        modifier = Modifier.graphicsLayer {
                            translationY = (pullState.distanceFraction - 1f) * 100f
                        },
                        color = MaterialTheme.colorScheme.background,

                    )
                }
            }
        },

    ) {

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
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

            }
            item {

                    UnifiedStepsCard(
                        screenWidth = screenWidth,
                        activeProvider = activeProvider
                    )


            }
            item {

                    UnifiedSleepCard(
                        screenWidth = screenWidth,
                        activeProvider = activeProvider,
                    )

            }
            item {

                    UnifiedWaterCard(
                        screenWidth = screenWidth,
                    )

            }
            item {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()

                )
            }
            //at 400.dp starts to jump - will fix later, content padding and spacer do not fix the issue

        }
    }

}