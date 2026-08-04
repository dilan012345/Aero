package com.example.aero.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.example.aero.R
import kotlin.random.Random

val googlesans = FontFamily(
    Font(
        resId = R.font.googlesans,
        weight = FontWeight.Normal
    ),

    )
val recentgrotesk = FontFamily(
    Font(
        resId = R.font.recentgrotesk,
        weight = FontWeight.Normal
    )
)
fun robotoFlex(
    opsz: Float = 14f,
    wght: Float = 400f,
    grad: Float = 0f,
    wdth: Float = 100f,
    slnt: Float = 0f,
    xtra: Float = 468f,
    xopq: Float = 96f,
    yopq: Float = 79f,
    ytas: Float = 750f,
    ytde: Float = -203f,
    ytfi: Float = 738f,
    ytlc: Float = 514f,
    ytuc: Float = 712f
): FontFamily {

    return FontFamily(
        Font(
            resId = R.font.robotoflex,
            variationSettings = FontVariation.Settings(
                FontVariation.Setting("opsz", opsz),
                FontVariation.Setting("wght", wght),
                FontVariation.Setting("GRAD", grad),
                FontVariation.Setting("wdth", wdth),
                FontVariation.Setting("slnt", slnt),
                FontVariation.Setting("XTRA", xtra),
                FontVariation.Setting("XOPQ", xopq),
                FontVariation.Setting("YOPQ", yopq),
                FontVariation.Setting("YTAS", ytas),
                FontVariation.Setting("YTDE", ytde),
                FontVariation.Setting("YTFI", ytfi),
                FontVariation.Setting("YTLC", ytlc),
                FontVariation.Setting("YTUC", ytuc)
            )
        )
    )
}


val coolvetica = FontFamily(
    Font(
        resId = R.font.coolvetica,
        weight = FontWeight.Normal
    )
)
val boldonse = FontFamily(
    Font(
        resId = R.font.boldonse,
        weight = FontWeight.Normal
    )
)

val robotosb = FontFamily(
    Font(
        resId = R.font.robotosb,
        weight = FontWeight.Normal
    )
)
val samsungsans = FontFamily(
    Font(
        resId = R.font.samsungsans,
        weight = FontWeight.Normal
    )
)

val samsungsharpsans = FontFamily(
    Font(
        resId = R.font.samsungsharpsans,
        weight = FontWeight.Normal
    )
)
val roboto = FontFamily(
    Font(
        resId = R.font.roboto,
        weight = FontWeight.Normal
    )
)
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = recentgrotesk,
        fontWeight = FontWeight.Normal
    ),
    bodyLarge = TextStyle(
        fontFamily = googlesans,
        fontWeight = FontWeight.Normal
    )
)
