package com.example.aero.data

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.compose.ui.graphics.Color


data class DebugLog(
    val message: String,
    val color: Color
)
object DebugLogger {

    private val _logs = MutableStateFlow<List<DebugLog>>(emptyList())
    val logs = _logs.asStateFlow()


    fun log(
        message: String,
        color: Color = Color.White
    ) {
        Log.d("DEBUG_TAG", message)

        _logs.value += DebugLog(
                    message,
                    color
                )
    }
}