package com.example.aero.ui.screens.old

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aero.data.Provider
import com.example.aero.ui.components.major.Card

import com.example.aero.ui.theme.googlesans
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes.Companion.Cookie12Sided
import androidx.compose.material3.toShape
import com.example.aero.data.DebugLogger
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.aero.data.AppDataStoreKeys
import com.example.aero.data.appDataStore
import com.example.aero.ui.components.extensions.ghealthui.StepGraph

@ExperimentalMaterial3ExpressiveApi
@Composable
fun HomeGrid(
    activeProvider: Provider,
    target: Boolean
) {

    val navigator = LocalNavigator.currentOrThrow
    var steps by remember { mutableIntStateOf(0) }
    LaunchedEffect(activeProvider) {
        steps = activeProvider.getSteps()
    }

    var cal by remember { mutableIntStateOf(0) }
    LaunchedEffect(activeProvider) {
        cal = activeProvider.getCalories()
    }
    var debug by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.appDataStore.data.collect { preferences ->
            debug = preferences[AppDataStoreKeys.DEBUG] ?: false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        LazyVerticalGrid(

            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(0.9f)
                .padding(top = 180.dp)
                .fillMaxHeight(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (debug) {
                item(span = { GridItemSpan(2) }) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth(0.9f)
                            .height(500.dp)
                    ) {

                        val logs by DebugLogger.logs.collectAsState()

                        val listState = rememberLazyListState()

                        LaunchedEffect(logs.size) {
                            if (logs.isNotEmpty()) {
                                listState.animateScrollToItem(logs.size - 1)
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            state = listState
                        ) {
                            items(logs) { log ->

                                Text(
                                    text = log.message,
                                    color = log.color,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)

                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(130.dp)


                    ) {
                        Box(
                            Modifier.align(Alignment.TopCenter)
                                .width(80.dp)
                                .padding(top = 10.dp)
                                .height(80.dp)
                                .clip(Cookie12Sided.toShape())
                                .background(MaterialTheme.colorScheme.surfaceBright)



                        ) {
                            Text(
                                text = steps.toString(),
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 30.sp,
                                fontFamily = googlesans,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Box(
                            Modifier.align(Alignment.BottomCenter)
                                .fillMaxWidth(0.4f)
                                .padding(bottom = 10.dp)
                                .height(25.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceBright)


                        ) {
                            Text(
                                text = "Steps",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 13.sp,
                                fontFamily = googlesans,
                                color = MaterialTheme.colorScheme.tertiaryFixed
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)

                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(130.dp)
                            .clickable {
                                navigator.push(Calories())
                            }

                    ) {
                        Box(
                            Modifier.align(Alignment.TopCenter)
                                .width(80.dp)
                                .padding(top = 10.dp)
                                .height(80.dp)
                                .clip(Cookie12Sided.toShape())
                                .background(MaterialTheme.colorScheme.surfaceBright)



                        ) {
                            Text(
                                text = cal.toString(),
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 30.sp,
                                fontFamily = googlesans,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Box(
                            Modifier.align(Alignment.BottomCenter)
                                .fillMaxWidth(0.6f)
                                .padding(bottom = 10.dp)
                                .height(25.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceBright)


                        ) {
                            Text(
                                text = "Total Calories",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 13.sp,
                                fontFamily = googlesans,
                                color = MaterialTheme.colorScheme.tertiaryFixed
                            )
                        }
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {
                StepGraph(
                    activeProvider
                )
            }
            item(span = { GridItemSpan(2) }) {
                Sleep(
                    activeProvider,
                    target
                )
            }
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Transparent)
                ) {

                }
            }
        }
    }
}
