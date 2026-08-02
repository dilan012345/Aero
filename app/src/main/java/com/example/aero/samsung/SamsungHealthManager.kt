package com.example.aero.samsung


import android.app.Activity
import android.content.Context
import com.samsung.android.sdk.health.data.HealthDataService
import com.samsung.android.sdk.health.data.HealthDataStore
import com.samsung.android.sdk.health.data.permission.AccessType
import com.samsung.android.sdk.health.data.permission.Permission
import com.samsung.android.sdk.health.data.request.DataType
import com.samsung.android.sdk.health.data.request.DataTypes


class SamsungHealthManager(
    private val context: Context
) {

    private lateinit var healthDataStore: HealthDataStore

    fun connect() {
        healthDataStore = HealthDataService.getStore(context)
    }

    suspend fun requestPermissions(activity: Activity) {

        if (!::healthDataStore.isInitialized) {
            connect()
        }

        val permissions = setOf(
            Permission.of(
                DataTypes.STEPS,
                AccessType.READ

            )
        )

        healthDataStore.requestPermissions(
            permissions,
            activity
        )
    }
}