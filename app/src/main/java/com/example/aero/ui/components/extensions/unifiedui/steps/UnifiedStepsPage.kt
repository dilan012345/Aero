package com.example.aero.ui.components.extensions.unifiedui.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.data.DebugLogger
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.components.major.Background
import com.example.aero.ui.theme.boldonse

class UnifiedStepsPage(): Screen {
    @Composable
    override fun Content() {
        DebugLogger.log(
            "Steps Recomposed",
            Color.Green
        )
        val providerType = ProviderSettings.getProvider()
        val context = LocalContext.current
        val activeProvider = when(providerType) {
            ProviderType.GOOGLE_HEALTH -> GoogleHealthProvider(context)
            ProviderType.SAMSUNG_HEALTH -> SamsungHealthProvider(context)
            ProviderType.STRAVA -> StravaProvider()
            ProviderType.UNIFIED -> UnifiedProvider(context)
        }
        val extension = when (activeProvider.type) {

            ProviderType.GOOGLE_HEALTH -> listOf(
                Color(0xFFFF5722),
                Color(0xAAE24A75),
                Color.Transparent
            )

            ProviderType.SAMSUNG_HEALTH -> listOf(
                Color(0xFF0B2C50),
                Color(0xFF67BB94),
                Color.Transparent
            )

            ProviderType.STRAVA -> listOf(
                Color(0xFFFC4C02),
                Color(0xFFFF8C42),
                Color.Transparent
            )
            ProviderType.UNIFIED -> listOf(
                Color(0xFF708F96),
                Color(0xFF598FB9),
                Color.Transparent
            )


            else -> listOf(
                Color(0xFF0B5048),
                Color(0xFF72BB67),
                Color.Transparent
            )
        }
        Background() {
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = extension
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 20.dp, top = 50.dp)
                        .width(130.dp)
                        .height(60.dp)
//                        .clip(RoundedCornerShape(20.dp))
//                        .background(
//                            MaterialTheme.colorScheme.tertiary
//                        )
                ) {

                            Text(
                                text = "Steps",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.tertiaryFixed,
                                fontSize = 30.sp,
                                fontFamily = boldonse,

                                )


                }

            }
        }
    }
}