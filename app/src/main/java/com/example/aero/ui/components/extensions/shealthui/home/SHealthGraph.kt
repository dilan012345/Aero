package com.example.aero.ui.components.extensions.shealthui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyBarChartSmall(
    primary:Color,
    values: List<Float> = listOf(0.6f, 0.9f, 0.4f, 0.75f, 1f, 0.55f, 0.3f)
) {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        days.forEachIndexed { index, day ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .width(15.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(values[index])
                            .clip(CircleShape)
                            .background(primary)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = day,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}