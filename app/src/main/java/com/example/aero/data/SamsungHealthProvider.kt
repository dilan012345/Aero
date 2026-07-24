package com.example.aero.data


class SamsungHealthProvider : Provider {
    override val type = ProviderType.SAMSUNG_HEALTH
    override suspend fun getSteps(): Int {
        return 100 // Samsung Health code later
    }

    override suspend fun getCalories(): Int {
        return 0
    }
}