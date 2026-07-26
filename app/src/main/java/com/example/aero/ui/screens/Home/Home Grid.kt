package com.example.aero.ui.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.aero.ui.components.Card
import com.example.aero.ui.screens.Calories.Calories
import com.example.aero.ui.screens.Steps.Steps
import com.example.aero.ui.theme.googlesans
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon

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

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)

                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {
                                navigator.push(Steps())
                            }

                    ) {
                        Text(
                            text = steps.toString(),
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 15.dp),
                            fontSize = 30.sp,
                            fontFamily = googlesans,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Box(
                            Modifier.align(Alignment.BottomCenter)
                                .fillMaxWidth(0.4f)
                                .padding(bottom = 10.dp)
                                .height(30.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(MaterialTheme.colorScheme.surfaceBright)


                        ) {
                            Text(
                                text = "Steps",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 13.sp,
                                fontFamily = googlesans,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)

                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {
                                navigator.push(Calories())
                            }

                    ) {
                        Text(
                            text = cal.toString(),
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 15.dp),
                            fontSize = 30.sp,
                            fontFamily = googlesans,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Box(
                            Modifier.align(Alignment.BottomCenter)
                                .fillMaxWidth(0.6f)
                                .padding(bottom = 10.dp)
                                .height(30.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(MaterialTheme.colorScheme.surfaceBright)


                        ) {
                            Text(
                                text = "Total Calories",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 13.sp,
                                fontFamily = googlesans,
                                color = Color.White
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
