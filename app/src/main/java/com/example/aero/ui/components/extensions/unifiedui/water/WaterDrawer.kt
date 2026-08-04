@file:Suppress("DEPRECATION")

package com.example.aero.ui.components.extensions.unifiedui.water

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.aero.ui.theme.robotoFlex
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterDrawer(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,

) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val view = LocalView.current



    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.tertiary,

        ) {


            val window = (LocalView.current.parent as? DialogWindowProvider)?.window

            LaunchedEffect(window) {
                window?.let {
                    WindowCompat.setDecorFitsSystemWindows(it, false)

                    it.navigationBarColor = android.graphics.Color.TRANSPARENT
                    it.setNavigationBarContrastEnforced(false)

                    WindowCompat.getInsetsController(it, it.decorView)
                        .isAppearanceLightNavigationBars = false
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Set Current Water",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    color = MaterialTheme.colorScheme.tertiaryFixed,
                    fontSize = 20.sp,
                    fontFamily = robotoFlex(
                        wght = 685f,
                        wdth = 113f,
                        opsz = 130f,
                        grad = 115f,
                        slnt = -1f,
                        xtra = 505f,
                        xopq = 86f,
                        yopq = 80f,
                        ytas = 817f,
                        ytde = -223f,
                        ytfi = 571f,
                        ytlc = 547f,
                        ytuc = 656f

                    )
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
                HorizontalDivider(thickness = 2.dp)

            }
        }
    }
}