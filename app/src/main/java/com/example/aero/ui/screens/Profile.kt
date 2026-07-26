package com.example.aero.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.R
import com.example.aero.data.Provider
import com.example.aero.data.ProviderType
import com.example.aero.ui.components.Background
import com.example.aero.ui.components.Card
import com.example.aero.ui.theme.coolvetica
import com.example.aero.ui.theme.googlesans
import com.example.aero.ui.theme.roboto
import com.example.aero.ui.theme.robotosb
import com.example.aero.ui.theme.samsungsharpsans
import com.example.aero.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

enum class SettingType {
    DARK_MODE,
    NOTIFICATIONS,
    BIOMETRICS,
    AUTO_UPDATE,
    CLOUD_SYNC,
    AUTO_CORRECT,
    EXPERIMENTAL,
    DEVELOPER_MODE
}

class Profile(private val activeProvider: Provider)
    : Screen{
    @Composable
    override fun Content(){
        val settingsViewModel: SettingsViewModel = koinViewModel()
        val darkTheme by settingsViewModel.darkTheme.collectAsState()
        Background {
            var weight by remember { mutableStateOf("") }
            var age by remember { mutableStateOf("") }
            var height by remember { mutableStateOf("") }
            var isMale by rememberSaveable { mutableStateOf(true) }
            val navigator = LocalNavigator.currentOrThrow
            val extension: List<Color> = when (activeProvider.type) {
                ProviderType.GOOGLE_HEALTH -> listOf(

                    Color(0xFFFF5722),
                    Color(0xAAE24A75),
                    Color.Transparent
                )

                ProviderType.SAMSUNG_HEALTH -> listOf(
                    Color(0xFF0B5048),
                    Color(0xFF72BB67),
                    Color.Transparent
                )

                ProviderType.STRAVA -> listOf(
                    Color(0xFFFC4C02),
                    Color(0xFFFF8C42),
                    Color.Transparent
                )

                else -> listOf(
                    Color(0xFF0B5048),
                    Color(0xFF72BB67),
                    Color.Transparent
                )
            }










            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = extension
                        )
                    )
            )



            data class SettingItem(
                val type: SettingType,
                val title: String,


            )

            val settingsList = remember {
                mutableStateListOf(
                    SettingItem(SettingType.DARK_MODE, "Dark Mode (Light mode \nnot fully supported)"),
                    SettingItem(SettingType.NOTIFICATIONS, "Enable Notifications"),
                    SettingItem(SettingType.EXPERIMENTAL, "Show experimental features"),
                    SettingItem(SettingType.DEVELOPER_MODE, "Developer Mode")
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(900.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color.Transparent,
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

                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)


                    ){
                        Text(
                            text = when (activeProvider.type) {
                                ProviderType.GOOGLE_HEALTH -> "Google Health"
                                ProviderType.SAMSUNG_HEALTH -> "Samsung Health"
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
                                ProviderType.STRAVA -> roboto
                                else -> coolvetica
                            },
                            color = Color.White
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



                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)

                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 0.dp)
                                .clip(CircleShape)
                                .size(250.dp)
                                .background(color = MaterialTheme.colorScheme.background)
                        ) {

                            val colorScheme = MaterialTheme.colorScheme
                            Canvas(
                                modifier = Modifier
                                    .size(245.dp)
                                    .align(Alignment.Center)
                            ) {
                                val strokeWidth = 5.dp.toPx()

                                drawArc(
                                    color = colorScheme.tertiary,
                                    startAngle = 270f,
                                    sweepAngle = 70f,
                                    useCenter = false,
                                    style = Stroke(
                                        width = strokeWidth,
                                        cap = StrokeCap.Round
                                    )
                                )



                                drawArc(
                                    color = colorScheme.tertiary,
                                    startAngle = 90f,
                                    sweepAngle = 160f,
                                    useCenter = false,
                                    style = Stroke(
                                        width = strokeWidth,
                                        cap = StrokeCap.Round
                                    )
                                )

                                drawArc(
                                    color = colorScheme.tertiary,
                                    startAngle = 0f,
                                    sweepAngle = 70f,
                                    useCenter = false,
                                    style = Stroke(
                                        width = strokeWidth,
                                        cap = StrokeCap.Round
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)

                                    .clip(CircleShape)
                                    .size(240.dp)
                                    .background(color = MaterialTheme.colorScheme.surface)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.round_person_2_24),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(170.dp),
                                    contentDescription = "Profile",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }
                }

                item{
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ){
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Weight (Kg)") },
                            modifier = Modifier.align(Alignment.TopCenter),
                            singleLine = true,
                            shape = CircleShape,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,

                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,

                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                unfocusedLabelColor = Color.White,

                                cursorColor = MaterialTheme.colorScheme.secondary,

                                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            label = { Text("Height (Cm)") },
                            modifier = Modifier.align(Alignment.TopCenter)
                                .padding(top = 60.dp),
                            singleLine = true,
                            shape = CircleShape,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,

                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,

                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                unfocusedLabelColor = Color.White,

                                cursorColor = MaterialTheme.colorScheme.secondary,

                                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                        OutlinedTextField(
                            value = age,
                            onValueChange = { age = it },
                            label = { Text("Age (Years)") },
                            modifier = Modifier.align(Alignment.TopCenter)
                                .padding(top = 120.dp),
                            singleLine = true,
                            shape = CircleShape,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,

                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,

                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                unfocusedLabelColor = Color.White,

                                cursorColor = MaterialTheme.colorScheme.secondary,

                                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 190.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            SingleChoiceSegmentedButtonRow {
                                SegmentedButton(
                                    selected = isMale,
                                    onClick = { isMale = true },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = 0,
                                        count = 2
                                    )
                                ) {
                                    Text("Male")
                                }

                                SegmentedButton(
                                    selected = !isMale,
                                    onClick = { isMale = false },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = 1,
                                        count = 2
                                    )
                                ) {
                                    Text("Female")
                                }
                            }
                        }
                        val bmr = if (isMale) {
                            10 * (weight.toDoubleOrNull() ?: 0.0) +
                                    6.25 * (height.toDoubleOrNull() ?: 0.0) -
                                    5 * (age.toIntOrNull() ?: 0) + 5
                        } else {
                            10 * (weight.toDoubleOrNull() ?: 0.0) +
                                    6.25 * (height.toDoubleOrNull() ?: 0.0) -
                                    5 * (age.toIntOrNull() ?: 0) - 161
                        }
                        Box(
                            Modifier.align(Alignment.BottomCenter)
                                .fillMaxWidth(0.4f)
                                .padding(bottom = 20.dp)
                                .height(30.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(MaterialTheme.colorScheme.surfaceBright)


                        ) {
                            Text(
                                text = "Your BMR: " + bmr.toInt().toString() +"Kcal",
                                modifier = Modifier.align(Alignment.Center)
                                    ,
                                color = Color.White,
                                fontFamily = roboto,
                                fontSize = 12.sp
                            )
                        }
                    }
                }





                items(settingsList.size) { index ->
                    val setting = settingsList[index]
                    val title = setting.title
                    val shape = when (index) {
                        0 -> RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 10.dp, bottomStart = 10.dp)  // first item
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
                            .fillMaxWidth()
                            .clip(shape)

                            .background(MaterialTheme.colorScheme.surface)

                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            color =  MaterialTheme.colorScheme.secondary,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 20.dp, end = 40.dp)
                                .align(Alignment.CenterStart),
                            fontFamily = googlesans
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
                                when(setting.type) {
                                    SettingType.DARK_MODE ->
                                        settingsViewModel.updateDarkTheme(checked)

                                    else -> {}
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.background,      // circle when ON
                                checkedTrackColor = MaterialTheme.colorScheme.tertiary, // track when ON
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