package com.example.aero.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.content.ContextCompat.getSystemService
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate



class UnifiedProvider(
    private val context: Context
) : Provider {

    override val type = ProviderType.UNIFIED

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var stepsToday by mutableStateOf(0)

    private var cal by mutableStateOf(0)
    private var sleep by mutableStateOf(0.0)

    private val stepSensor =
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private val prefs =
        context.getSharedPreferences("steps", Context.MODE_PRIVATE)

    private val listener = object : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent) {
            val currentSteps = event.values[0].toLong()

            val savedDate = prefs.getString("date", "")
            val today = LocalDate.now().toString()

            if (savedDate != today) {
                prefs.edit()
                    .putString("date", today)
                    .putLong("baseline", currentSteps)
                    .apply()
            }

            val baseline = prefs.getLong("baseline", currentSteps)

            stepsToday = (currentSteps - baseline).toInt()

            Log.d("Aero", "Today's steps: $stepsToday")
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    init {
        stepSensor?.let {
            sensorManager.registerListener(
                listener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override suspend fun getSteps(): Int {
        return stepsToday
    }

    override suspend fun getCalories(): Int {
        return cal
    }

    override suspend fun getSleep(): Double {
        return sleep
    }

    override suspend fun sync() {
        // Sensor is already running.
        // Nothing to fetch.
        // The listener updates steps automatically.
    }
}