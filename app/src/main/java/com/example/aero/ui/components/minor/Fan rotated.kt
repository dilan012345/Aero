package com.example.aero.ui.components.minor

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes.Companion.Pill
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun rememberRotatedFanShape(): Shape {
    val fanShape = Pill.toShape()

    return remember {
        RotatedFanShape(fanShape)
    }
}

class RotatedFanShape(
    private val fanShape: Shape
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val outline = fanShape.createOutline(
            size,
            layoutDirection,
            density
        )

        return when (outline) {
            is Outline.Generic -> {
                val path = Path().apply {
                    addPath(outline.path)

                    transform(
                        Matrix().apply {
                            translate(
                                size.width / 2,
                                size.height / 2
                            )

                            rotateZ(0f)

                            scale(
                                0.7f,
                                0.7f
                            )

                            translate(
                                -size.width / 2,
                                -size.height / 2
                            )
                        }
                    )
                }

                Outline.Generic(path)
            }

            else -> outline
        }
    }
}
