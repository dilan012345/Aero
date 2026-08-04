package com.example.aero.ui.components.extensions.unifiedui.steps

import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Shader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.data.DebugLogger
import com.example.aero.data.GoogleHealthProvider
import com.example.aero.data.ProviderManager
import com.example.aero.data.ProviderSettings
import com.example.aero.data.ProviderType
import com.example.aero.data.SamsungHealthProvider
import com.example.aero.data.StravaProvider
import com.example.aero.data.UnifiedProvider
import com.example.aero.ui.components.extensions.unifiedui.home.UnifiedStepsCard
import com.example.aero.ui.components.extensions.unifiedui.home.UnifiedWaterCard
import com.example.aero.ui.components.major.Background
import com.example.aero.ui.components.major.Card
import com.example.aero.ui.components.old.Bar
import com.example.aero.ui.components.old.BarChart

import com.example.aero.ui.theme.robotosb
import kotlin.math.roundToInt
import kotlin.random.Random
import com.example.aero.R
import com.example.aero.ui.components.minor.InfoRow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

import com.example.aero.ui.theme.robotoFlex
import java.io.File

class UnifiedStepsPage(): Screen {
    @Composable
    override fun Content() {
        DebugLogger.log(
            "Steps Recomposed",
            Color.Green
        )
        var showDialog by remember { mutableStateOf(false) }
        val haptic = LocalHapticFeedback.current
        val providerType = ProviderSettings.getProvider()
        val context = LocalContext.current

        val activeProvider = remember(context) {
            ProviderManager.getActiveProvider(context)
        }
        var steps by remember {
            mutableFloatStateOf(1500f)
        }
        var sambias by remember {
            mutableFloatStateOf(30f)
        }
        var unifiedbias by remember {
            mutableFloatStateOf(60f)
        }
        var googbias by remember {
            mutableFloatStateOf(10f)
        }
        val totalbias = sambias + googbias + unifiedbias
        val samsungPercent = sambias / totalbias * 100
        val googlePercent = googbias / totalbias * 100
        val unifiedPercent = unifiedbias / totalbias * 100

        val sliderColors = SliderDefaults.colors(
            thumbColor = Color(0xFF598FB9),
            activeTrackColor = Color(0xFF598FB9),
            inactiveTrackColor = Color(0xFF598FB9).copy(alpha = 0.25f)
        )

        val gradient = activeProvider.type.gradient

        Background() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = gradient
                        )
                    )
                // Not used - too resource intense
//                    .graphicsLayer {
//                        compositingStrategy = CompositingStrategy.Offscreen
//                    }
//                    .drawWithContent {
//                    drawContent()
//                        drawImage(
//                            image = noiseImage,
//                            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
//                            alpha = 0.3f,
//                            blendMode = BlendMode.Screen
//                        )
//                }
            ) {
                val context = LocalContext.current
                val resources = context.resources
                val noiseImage = ImageBitmap.imageResource(
                    R.drawable.noise_tile
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.verticalGradient(colors = gradient)
                        )
                        .drawWithCache {
                            val shader = ImageShader(
                                image = noiseImage,
                                tileModeX = TileMode.Repeated,
                                tileModeY = TileMode.Repeated
                            )

                            val brush = ShaderBrush(shader)

                            onDrawWithContent {
                                drawContent()

                                drawRect(
                                    brush = brush,
                                    alpha = 0.15f,
                                    blendMode = BlendMode.Screen
                                )
                            }
                        }
                )


            }
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


            }
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            LazyColumn(

                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Steps",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 20.dp),
                            color = MaterialTheme.colorScheme.tertiaryFixed,
                            fontSize = 30.sp,
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
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }

                item {

                    UnifiedStepsCard(
                        screenWidth = screenWidth,
                        activeProvider = activeProvider
                    )

                }
                item {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(180.dp)
                    ) {




                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Target",
                                color = MaterialTheme.colorScheme.tertiaryFixed,
                                fontSize = 15.sp,
                                fontFamily = robotosb
                            )

                            Slider(
                                value = steps,
                                onValueChange = {
                                    if (steps != it) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    steps = it
                                },
                                modifier = Modifier.fillMaxWidth(0.8f),
                                colors = sliderColors,
                                track = { state ->
                                    SliderDefaults.Track(
                                        sliderState = state,
                                        colors = sliderColors
                                    )
                                },
                                steps = 35,
                                valueRange = 0f..18000f
                            )

                            Box(
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(Color(0xFF2D2D2D))
                            ) {
                                Text(
                                    text = steps.roundToInt().toString() + " Steps",
                                    modifier = Modifier.align(Alignment.Center),
                                    color = MaterialTheme.colorScheme.tertiaryFixed,
                                    fontSize = 15.sp,
                                    fontFamily = robotosb
                                )
                            }

                        }
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
                item{

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(330.dp)
                    ) {
                        Box(
                            Modifier.fillMaxSize(0.8f)
                                .align(Alignment.Center)

                        ) {
                            Text(
                                text = "Bias",
                                color = MaterialTheme.colorScheme.tertiaryFixed,
                                fontSize = 15.sp,
                                fontFamily = robotosb
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxSize(0.8f)
                                .align(Alignment.Center)
                                .padding(top = 30.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(45.dp)
                                    .height(200.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(
                                            Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(
                                                topStart = 15.dp,
                                                bottomStart = 15.dp,
                                                topEnd = 5.dp,
                                                bottomEnd = 5.dp,
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.samsunghealth),
                                        modifier = Modifier.fillMaxWidth(0.7f),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(
                                            Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(
                                                topStart = 15.dp,
                                                bottomStart = 15.dp,
                                                topEnd = 5.dp,
                                                bottomEnd = 5.dp,
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.googlehealth),
                                        modifier = Modifier.fillMaxWidth(0.7f),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(
                                            Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(
                                                topStart = 15.dp,
                                                bottomStart = 15.dp,
                                                topEnd = 5.dp,
                                                bottomEnd = 5.dp,
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.unified),
                                        modifier = Modifier.fillMaxWidth(0.7f),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(200.dp)
                                ,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(
                                            Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(
                                                topEnd = 15.dp,
                                                bottomEnd = 15.dp,
                                                bottomStart = 5.dp,
                                                topStart = 5.dp
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = sambias.roundToInt().toString() + "%",
                                        modifier = Modifier.align(Alignment.Center),
                                        color = MaterialTheme.colorScheme.tertiaryFixed,
                                        fontSize = 15.sp,
                                        fontFamily = robotosb
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(
                                            Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(
                                                topEnd = 15.dp,
                                                bottomEnd = 15.dp,
                                                bottomStart = 5.dp,
                                                topStart = 5.dp
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = googbias.roundToInt().toString() + "%",
                                        modifier = Modifier.align(Alignment.Center),
                                        color = MaterialTheme.colorScheme.tertiaryFixed,
                                        fontSize = 15.sp,
                                        fontFamily = robotosb
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(
                                            Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(
                                                topEnd = 15.dp,
                                                bottomEnd = 15.dp,
                                                bottomStart = 5.dp,
                                                topStart = 5.dp
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = unifiedbias.roundToInt().toString() + "%",
                                        modifier = Modifier.align(Alignment.Center),
                                        color = MaterialTheme.colorScheme.tertiaryFixed,
                                        fontSize = 15.sp,
                                        fontFamily = robotosb
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 12.dp)
                                    .height(200.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Slider(
                                    value = sambias,
                                    onValueChange = {
                                        if (sambias != it) {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        }
                                        sambias = it
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFF67BB94),
                                        activeTrackColor = Color(0xFF67BB94),
                                        inactiveTrackColor = Color(0xFF67BB94).copy(alpha = 0.25f),
                                        activeTickColor = Color(0xFF457E65),
                                        inactiveTickColor = MaterialTheme.colorScheme.tertiary
                                    ),
                                    track = { state ->
                                        SliderDefaults.Track(
                                            sliderState = state,
                                            colors = SliderDefaults.colors(
                                                thumbColor = Color(0xFF67BB94),
                                                activeTrackColor = Color(0xFF67BB94),
                                                inactiveTrackColor = Color(0xFF67BB94).copy(alpha = 0.25f),
                                                activeTickColor = Color(0xFF457E65),
                                                inactiveTickColor = MaterialTheme.colorScheme.tertiary
                                            )
                                        )
                                    },
                                    steps = 3,
                                    valueRange = 0f..4f
                                )
                                Slider(
                                    value = googbias,
                                    onValueChange = {
                                        if (googbias != it) {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        }
                                        googbias = it
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFFCD545B),
                                        activeTrackColor = Color(0xFFCD545B),
                                        inactiveTrackColor = Color(0xFFCD545B).copy(alpha = 0.25f),
                                        activeTickColor = Color(0xFF803538),
                                        inactiveTickColor = MaterialTheme.colorScheme.tertiary
                                    ),
                                    track = { state ->
                                        SliderDefaults.Track(
                                            sliderState = state,
                                            colors = SliderDefaults.colors(
                                                thumbColor = Color(0xFFCD545B),
                                                activeTrackColor = Color(0xFFCD545B),
                                                inactiveTrackColor = Color(0xFFCD545B).copy(alpha = 0.25f),
                                                activeTickColor = Color(0xFF803538),
                                                inactiveTickColor = MaterialTheme.colorScheme.tertiary
                                            )
                                        )
                                    },
                                    steps = 3,
                                    valueRange = 0f..4f
                                )
                                Slider(
                                    value = unifiedbias,
                                    onValueChange = {
                                        if (unifiedbias != it) {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        }
                                        unifiedbias = it
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = sliderColors,
                                    track = { state ->
                                        SliderDefaults.Track(
                                            sliderState = state,
                                            colors = sliderColors
                                        )
                                    },
                                    steps = 3,
                                    valueRange = 0f..4f
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 15.dp, bottom = 15.dp)
                                .size(25.dp)
                                .background(Color(0xFF2D2D2D), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = {
                                    showDialog = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QuestionMark,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                        }
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
                item{
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(330.dp)
                    ) {}
                }
                //use spacedby 10.dp but cannot be bothered

            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                containerColor = MaterialTheme.colorScheme.tertiary,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                textContentColor = MaterialTheme.colorScheme.onSurface,
                title = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Step Accuracy",
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

                            ),
                            fontSize = 25.sp
                        )

                        HorizontalDivider(
                            thickness = 2.dp,
                            color = Color(0xFF598FB9).copy(alpha = 0.5f)
                        )
                    }
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    SpanStyle(fontWeight = FontWeight.Bold)
                                ) {
                                    append("Steps measured using your phone are as accurate as its hardware.")
                                }
                            }
                        )

                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    SpanStyle(fontWeight = FontWeight.Bold)
                                ) {
                                    append("The main differences come from how movement is interpreted and filtered:")
                                }
                            }
                        )

//                        Column(
//                            verticalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            HorizontalDivider(
//                                thickness = 0.5.dp,
//                                color = Color(0xFF598FB9).copy(alpha = 0.5f)
//                            )
//                            InfoRow(
//
//                                title = "Motion filtering",
//                                description = "Reduces noise from everyday movements that may resemble walking."
//                            )
//                            HorizontalDivider(
//                                thickness = 0.5.dp,
//                                color = Color(0xFF598FB9).copy(alpha = 0.5f)
//                            )
//
//                            InfoRow(
//                                title = "False-step removal",
//                                description = "Detects and ignores movements that are unlikely to be real steps."
//                            )
//                            HorizontalDivider(
//                                thickness = 0.5.dp,
//                                color = Color(0xFF598FB9).copy(alpha = 0.5f)
//                            )
//
//                            InfoRow(
//                                title = "Pause detection",
//                                description = "Identifies when walking stops to avoid overcounting."
//                            )
//                            HorizontalDivider(
//                                thickness = 0.5.dp,
//                                color = Color(0xFF598FB9).copy(alpha = 0.5f)
//                            )
//
//                            InfoRow(
//                                title = "Phone movement detection",
//                                description = "Accounts for carrying styles, vibrations, and changes in phone position."
//                            )
//                            HorizontalDivider(
//                                thickness = 2.dp,
//                                color = Color(0xFF598FB9).copy(alpha = 0.5f)
//                            )
//                        }

                        Text(
                            "Different software, such as Samsung Health, applies different levels of processing. As well as this, Google Fit and occasionally Samsung Health can fail to track steps for an entier day, or randomly add thousands. Adjusting weighting helps improve precision.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                confirmButton = {
                    FilledTonalButton(
                        onClick = { showDialog = false },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Color(0xFF598FB9).copy(alpha = 0.25f),
                            contentColor = Color(0xFFFFFFFF)
                        )
                    ) {
                        Text(
                            text = "Got it",
                            fontFamily = robotoFlex(
                                wght = 650f,
                                wdth = 105f,
                                opsz = 14f
                            )
                        )
                    }
                }
            )
        }

    }
}
//card clicks clickable navigates to new steps page which prevents pop back - Cannot see simple fix so will leave it for now