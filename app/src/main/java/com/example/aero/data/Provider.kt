package com.example.aero.data

interface Provider {
    val type: Any

    suspend fun getSteps(): Int
    suspend fun getCalories(): Int

    suspend fun getSleep(): Double
}