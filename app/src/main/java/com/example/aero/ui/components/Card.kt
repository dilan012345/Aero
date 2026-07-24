package com.example.aero.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Card(modifier: Modifier, content: @Composable BoxScope.() -> Unit){
    Box(
        modifier = Modifier
            .then(other = modifier)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)



    ){
        content()
    }
}