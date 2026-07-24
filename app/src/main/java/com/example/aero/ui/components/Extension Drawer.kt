package com.example.aero.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomDrawer(modifier: Modifier, content: @Composable BoxScope.() -> Unit){
    val bordercol: Color = Color(0x00ffffff)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )
            .border(3.dp, bordercol, shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomStart = 5.dp,
                bottomEnd = 5.dp ))
            .background(Color(0xFF1d1c1a))

            .then(other = modifier)
    ){
        content()
    }
}
