package com.example.aero.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.aero.R

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
