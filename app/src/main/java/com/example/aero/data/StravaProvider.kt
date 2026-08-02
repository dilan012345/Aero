package com.example.aero.data

class StravaProvider : Provider {
    override val type = ProviderType.STRAVA
    override suspend fun sync() {

    }

    override suspend fun getSteps(): Int {
        return 50 // Health Connect code later
    }

    override suspend fun getCalories(): Int {
        return 0
    }
    override suspend fun getSleep(): Double {
        return 0.0
    }
}