package com.example.aero.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.aero.healthConnect.HealthConnectManager
import android.util.Log

class SamsungHealthProvider(context: Context) : Provider {

    private val manager = HealthConnectManager(context)

    var steps by mutableStateOf(0)
    var cal by mutableStateOf(0)
    var sleep by mutableStateOf(0.0)

    override val type = ProviderType.SAMSUNG_HEALTH

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
        val newSteps = manager.readSteps(
            packagename = "com.sec.android.app.shealth"
        )

        val newCal = manager.readCalories(
            packagename = "com.sec.android.app.shealth"
        )

        val newSleep = manager.readSleep(
            packagename = "com.sec.android.app.shealth"
        )

        Log.d("SAMSUNG","Samsung sync: steps=$newSteps cal=$newCal sleep=$newSleep")

        steps = newSteps.toInt()
        cal = newCal.toInt()
        sleep = newSleep
    }
}