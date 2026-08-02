package com.example.aero.data

import android.content.Context
import android.util.Log
import com.example.aero.healthConnect.HealthConnectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.example.aero.data.DebugLogger
import com.example.aero.data.Provider
import com.example.aero.data.ProviderType
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class GoogleHealthProvider(
    context: Context
) : Provider {

    private val manager = HealthConnectManager(context)

    private var steps by mutableStateOf(0)
    private var cal by mutableStateOf(0)
    private var sleep by mutableStateOf(0.0)

    override val type = ProviderType.GOOGLE_HEALTH

    override suspend fun getSteps(): Int {
        return steps
    }

    override suspend fun getCalories(): Int {
        return cal
    }

    override suspend fun getSleep(): Double {
        return sleep
    }

    override suspend fun sync() {
        steps = manager.readSteps(
            packagename = "com.google.android.apps.fitness"
        ).toInt()

        cal = manager.readCalories(
            packagename = "com.google.android.apps.fitness"
        ).toInt()

        sleep = manager.readSleep(
            packagename = "com.google.android.apps.fitness"
        )

        Log.d("GOOGLE","Google synced: $steps steps, $cal calories, $sleep sleep")
    }
}