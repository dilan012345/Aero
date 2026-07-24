package com.example.aero.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import kotlin.math.ceil


data class Bar(
    val label: String,
    val value: Float
)

@Composable
fun BarChart(
    bars: List<Bar>,
    modifier: Modifier = Modifier,
    color: Color
) {

    Canvas(modifier = modifier) {

        val paint = android.graphics.Paint().apply {
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = 30f
            setColor(Color.White.toArgb())
        }

        val spacing = 5.dp.toPx()

        // Space for Y labels only
        val yAxisWidth = 30.dp.toPx()

        // Bar padding
        val startPadding = 10.dp.toPx()
        val endPadding = 20.dp.toPx()

        val rawMax = bars.maxOf { it.value }

        // Dynamic Y-axis step
        val step = when {
            rawMax <= 10000 -> 1000
            rawMax <= 30000 -> 2000
            rawMax <= 60000 -> 5000
            else -> 10000
        }

        // Round max to nearest step
        val maxValue = (ceil(rawMax / step) * step).toFloat()


        // Chart area
        val chartStart = yAxisWidth + startPadding
        val chartWidth = size.width - chartStart - endPadding

        val barWidth =
            (chartWidth - spacing * (bars.size - 1)) / bars.size


        // Y-axis labels
        for (value in 0..maxValue.toInt() step step) {

            val y =
                size.height - (value.toFloat() / maxValue) * size.height

            drawContext.canvas.nativeCanvas.drawText(
                "${value / 1000}k",
                yAxisWidth / 2 + 5,
                y + 10,
                paint
            )
        }


        // Bars
        bars.forEachIndexed { index, bar ->

            val barX =
                chartStart + index * (barWidth + spacing)

            val barCenter =
                barX + barWidth / 2


            val height =
                (bar.value / maxValue) * size.height


            drawRoundRect(
                color = color,
                topLeft = Offset(
                    barX,
                    size.height - height
                ),
                size = Size(
                    barWidth,
                    height
                ),
                cornerRadius = CornerRadius(50f, 50f)
            )


            // X-axis labels
            drawContext.canvas.nativeCanvas.drawText(
                bar.label,
                barCenter,
                size.height + 40,
                paint
            )
        }
    }
}