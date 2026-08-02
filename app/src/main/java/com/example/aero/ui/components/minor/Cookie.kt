package com.example.aero.ui.components.minor

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes.Companion.Cookie9Sided
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.Morph
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialShapes.Companion.Square
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.graphics.shapes.toPath

class MorphShape(
    private val morph: Morph,
    private val progress: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val path = morph
            .toPath(progress)
            .asComposePath()

        val matrix = Matrix().apply {
            scale(
                size.minDimension / 0.97f,
                size.minDimension / 0.97f
            )

        }

        path.transform(matrix)

        return Outline.Generic(path)
    }
}
@ExperimentalMaterial3ExpressiveApi
@Composable
fun Cookie9tosquare(onDrawerClick: () -> Unit,
            drawerOpen: Boolean,
    Content: @Composable BoxScope.() -> Unit
){

    val morph = remember {
        Morph(
            Cookie9Sided,
            Square
        )
    }


    val progress by animateFloatAsState(
        targetValue = if (drawerOpen) 1f else 0f,
        animationSpec = tween(350),
        label = "morph"
    )
    Log.d("MORPH", "drawerOpen: $drawerOpen")
    Box(
        modifier = Modifier

            .size(50.dp)
            .clip(MorphShape(morph, progress))
            .background(MaterialTheme.colorScheme.surface)
            .border(2.dp,MaterialTheme.colorScheme.tertiary,MorphShape(morph, progress))
            .clickable {
                onDrawerClick()
            }
    ){
        Box(
            Modifier.fillMaxSize()
                .offset(x = (0.5).dp,y = (0.2).dp)
        ) {
            Content()
        }
    }




}