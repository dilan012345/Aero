package com.example.aero.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.aero.R
import com.example.aero.ui.components.Background
import com.example.aero.ui.components.Card
import com.example.aero.ui.components.Stamp
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource

class Home: Screen {

    val extension: List<Color> = listOf(
        Color(0xFF4285f4),
        Color(0xFF34A853),
        Color(0xFFFBBC05),
        Color(0xFFEA4335),

        Color.Transparent
    )
    @Composable
    override fun Content() {
        Background(){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = extension
                        )
                    )
            )


            LazyVerticalGrid(

                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 140.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ){
                items (count = 2){
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
                                .background(MaterialTheme.colorScheme.surface)
                        ) {

                        }
                    }
                }
                item (span = { GridItemSpan(2) }){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)


                    ) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(MaterialTheme.colorScheme.surface)
                        ) {

                        }
                    }
                }
                item (span = { GridItemSpan(2) }){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)

                    ) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(350.dp)
                                .background(MaterialTheme.colorScheme.surface)
                        ) {

                        }
                    }
                }
                item (span = { GridItemSpan(2) }){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Transparent)
                    ){

                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .align(Alignment.Center)
            ){
                Stamp(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(50.dp),
                    content = {
                        Icon(
                            painter = painterResource(R.drawable.googlehealth),
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Google Health",
                            tint = Color.Unspecified // forces black otherwise
                        )
                    }

                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(50.dp)
                        .clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.surface)

                )

            }










        }
    }
}

