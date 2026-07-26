package com.example.aero.data

import android.content.Context
import com.example.aero.healthConnect.HealthConnectManager


class SamsungHealthProvider(context: Context) : Provider {

    private val manager = HealthConnectManager(context)

    override val type = ProviderType.SAMSUNG_HEALTH

    override suspend fun getSteps(): Int {
        val steps = manager.readSteps(packagename = "com.sec.android.app.shealth")
        println("Steps today: $steps")
        return steps.toInt()
    }

    override suspend fun getCalories(): Int {
            val cal = manager.readCalories(packagename = "com.sec.android.app.shealth")
            println("Steps today: $cal")
            return cal.toInt()
    }

    override suspend fun getSleep(): Double {
        return 9.0
    }
}