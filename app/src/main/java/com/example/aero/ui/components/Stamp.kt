package com.example.aero.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Stamp(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    color: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = Color.DarkGray,
    borderWidth: Float = 6f,
    wobble: Float = 4f,
    points: Int = 16
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val radius = size.minDimension / 2f
            val center = center
            val path = Path()

            val steps = 100
            for (i in 0..steps) {
                val angle = (2 * Math.PI * i / steps).toFloat()
                val wave = sin(angle * points) * wobble
                val r = radius + wave

                val x = center.x + r * cos(angle)
                val y = center.y + r * sin(angle)

                if (i == 0) path.moveTo(x, y)
                else path.lineTo(x, y)
            }

            path.close()

            // Fill
            drawPath(
                path = path,
                color = color
            )

            // Border
            drawPath(
                path = path,
                color = borderColor,
                style = Stroke(width = borderWidth)
            )
        }

        // This is now centred inside the stamp
        content()
    }
}