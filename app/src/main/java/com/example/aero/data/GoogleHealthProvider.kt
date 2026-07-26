package com.example.aero.data

import android.content.Context
import android.util.Log
import com.example.aero.healthConnect.HealthConnectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
class GoogleHealthProvider(
    context: Context
) : Provider {

    private val manager = HealthConnectManager(context)

    override val type = ProviderType.GOOGLE_HEALTH

    override suspend fun getSteps(): Int {
        val steps = manager.readSteps(packagename = "com.google.android.apps.fitness")
        println("goog Steps today: $steps")
        return steps.toInt()
    }



    override suspend fun getCalories(): Int {
        val cal = manager.readCalories(packagename = "com.google.android.apps.fitness")
        println("Steps today: $cal")
        return cal.toInt()
    }

    override suspend fun getSleep(): Double {
        return 10.0
    }
}