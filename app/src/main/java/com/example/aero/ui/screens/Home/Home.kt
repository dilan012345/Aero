package com.example.aero.ui.screens.Home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.R
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.components.Background
import com.example.aero.ui.components.Bar
import com.example.aero.ui.components.BarChart
import com.example.aero.ui.components.BottomDrawer
import com.example.aero.ui.components.Card
import com.example.aero.ui.components.Stamp
import com.example.aero.ui.components.WiggleProgressCircle
import com.example.aero.ui.screens.Profile
import com.example.aero.ui.screens.Setup
import com.example.aero.ui.screens.Home.HomeHeader
import com.example.aero.ui.theme.coolvetica
import com.example.aero.ui.theme.googlesans
import com.example.aero.ui.theme.roboto
import com.example.aero.ui.theme.robotosb
import com.example.aero.ui.theme.samsungsharpsans
import kotlin.random.Random

class Home(): Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        Background() {

            var draw by remember { mutableStateOf(false) }
            val navigator = LocalNavigator.currentOrThrow
            val notification: Boolean = true
            val providers = listOf(
                GoogleHealthProvider(context),
                SamsungHealthProvider(context),
                StravaProvider(),
                UnifiedProvider()
            )
            var selectedProvider by remember {
                mutableStateOf(
                    ProviderSettings.getProvider()
                )
            }
            var showTargets by remember { mutableStateOf(false) }

            val activeProvider = providers.first {
                it.type == selectedProvider
            }


            HomeHeader(
                activeProvider = activeProvider,
                notification = notification,
                onDrawerClick = {
                    draw = !draw


                },
                onProfileClick = {
                    navigator.push(Profile(activeProvider))
                },
                showTargets = showTargets,
                onShowTargetsChange = {
                    showTargets = !showTargets
                }
            )
            Log.d("TARGETS", showTargets.toString())

            HomeGrid(
                activeProvider = activeProvider,
                target = showTargets
            )


            Drawer(
                draw = draw,
                activeProvider = activeProvider,
                onClose = {
                    draw = false
                },
                onProviderSelected = {
                    selectedProvider = it
                },
                onConnectClick = {
                    navigator.push(Setup())
                }
            )









        }
    }
}