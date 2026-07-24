package com.example.aero.data


class GoogleHealthProvider : Provider {
    override val type = ProviderType.GOOGLE_HEALTH
    override suspend fun getSteps(): Int {
        return 6210 // Health Connect code later
    }

    override suspend fun getCalories(): Int {
        return 316
    }
}