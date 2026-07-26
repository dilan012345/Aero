package com.example.aero.data

class UnifiedProvider : Provider {
    override val type = ProviderType.UNIFIED
    override suspend fun getSteps(): Int {
        return 10101 // Health Connect code later
    }

    override suspend fun getCalories(): Int {
        return 1
    }

    override suspend fun getSleep(): Double {
        return 9.5
    }
}