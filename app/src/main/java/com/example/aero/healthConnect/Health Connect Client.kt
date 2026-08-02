package com.example.aero.healthConnect

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.records.metadata.DataOrigin
import com.example.aero.data.DebugLogger
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.collections.filter
import android.util.Log
const val googleFitPackage = "com.google.android.apps.fitness"
const val samsungHealthPackage = "com.sec.android.app.shealth"

class HealthConnectManager(private val context: Context) {

    val healthConnectClient = HealthConnectClient.getOrCreate(context)

    fun isAvailable(): Boolean {
        val status = HealthConnectClient.getSdkStatus(context)
        return status == HealthConnectClient.SDK_AVAILABLE
    }

    private val startOfDay = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()


    private suspend fun hasPermission(permission: String): Boolean {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        return permission in granted
    }


    suspend fun readSteps(packagename: String): Long {

        if (!hasPermission(
                HealthPermission.getReadPermission(StepsRecord::class)
            )
        ) {
            DebugLogger.log(
                "STEPS Permission missing",
                Color.Red
            )
            return 0L
        }


        return try {

            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        startOfDay,
                        Instant.now()
                    )
                )
            )


            for (record in response.records) {

                val recordPackageName = record.metadata.dataOrigin.packageName
                val ignoredPackage =
                    "com.android.healthconnect.phone.j526ebb9ff6c43590965203a75fb0ea58"


//                if (recordPackageName == ignoredPackage) {
//
//                } else if (
//                    recordPackageName == googleFitPackage ||
//                    recordPackageName == samsungHealthPackage
//                ) {
//
//                    DebugLogger.log(
//                        "STEPS Source: $recordPackageName found",
//                        Color.Cyan
//                    )
//
//                } else {
//
//                    DebugLogger.log(
//                        "STEPS $recordPackageName: Refused connection",
//                        Color.Red
//                    )
//                }
            }


            val googleFitAgg = healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(StepsRecord.COUNT_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(
                        startOfDay,
                        Instant.now()
                    ),
                    dataOriginFilter = setOf(
                        DataOrigin(googleFitPackage)
                    )
                )
            )


            val googleFitSteps =
                googleFitAgg[StepsRecord.COUNT_TOTAL] ?: 0L


            val samsungHealthSteps = response.records
                .filter {
                    it.metadata.dataOrigin.packageName == samsungHealthPackage
                }
                .sumOf {
                    it.count
                }


            when (packagename) {

                samsungHealthPackage -> samsungHealthSteps

                googleFitPackage -> googleFitSteps

                else -> 0L
            }


        } catch (e: SecurityException) {

            DebugLogger.log(
                "STEPS Permission revoked",
                Color.Red
            )

            0L
        }
    }



    suspend fun readCalories(packagename: String): Double {


        if (!hasPermission(
                HealthPermission.getReadPermission(
                    TotalCaloriesBurnedRecord::class
                )
            )
        ) {
            DebugLogger.log(
                "CAL Permission missing",
                Color.Red
            )
            return 0.0
        }


        return try {

            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = TotalCaloriesBurnedRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        startOfDay,
                        Instant.now()
                    )
                )
            )


            if (response.records.isEmpty()) {

                DebugLogger.log(
                    "CAL No records found",
                    Color.Red
                )

            } else {

                for (record in response.records) {

                    val recordPackageName =
                        record.metadata.dataOrigin.packageName

                    val ignoredPackage =
                        "com.android.healthconnect.phone.j526ebb9ff6c43590965203a75fb0ea58"


//                    if (
//                        recordPackageName != ignoredPackage &&
//                        (
//                                recordPackageName == googleFitPackage ||
//                                        recordPackageName == samsungHealthPackage
//                                )
//                    ) {
//
//                        DebugLogger.log(
//                            "CAL Source: $recordPackageName found",
//                            Color.Cyan
//                        )
//
//                    } else {
//
//                        DebugLogger.log(
//                            "CAL $recordPackageName: Refused connection",
//                            Color.Red
//                        )
//                    }
                }
            }


            val calories = response.records
                .filter {
                    it.metadata.dataOrigin.packageName == packagename
                }
                .sumOf {
                    it.energy.inKilocalories
                }




            calories


        } catch (e: SecurityException) {

            DebugLogger.log(
                "CAL Permission revoked",
                Color.Red
            )

            0.0
        }
    }


    suspend fun readSleep(packagename: String): Double {
        if (!hasPermission(
                HealthPermission.getReadPermission(
                    SleepSessionRecord::class
                )
            )
        ) {
            DebugLogger.log(
                "Sleep Permission missing",
                Color.Red
            )
            return 0.0
        }


        return try {

            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = SleepSessionRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        Instant.now().minus(48, ChronoUnit.HOURS),
                        Instant.now()
                    )
                )
            )
            Log.d(
                "SleepDebug",
                "Sleep records found: ${response.records.size}"
            )


//            response.records.forEach { record ->
//
//                Log.d("SleepRecord", "----- SLEEP SESSION -----")
//                Log.d("SleepRecord", "Start: ${record.startTime}")
//                Log.d("SleepRecord", "End: ${record.endTime}")
//                Log.d("SleepRecord", "Source: ${record.metadata.dataOrigin.packageName}")
//
//                record.stages.forEach { stage ->
//                    Log.d(
//                        "SleepStage",
//                        "Stage: ${stage.stage}, Start: ${stage.startTime}, End: ${stage.endTime}"
//                    )
//                }
//
//            }

            val sleep = response.records
                .filter {
                    it.metadata.dataOrigin.packageName == packagename
                }
                .maxByOrNull {
                    it.endTime
                }
                ?.let {
                    ChronoUnit.MINUTES.between(
                        it.startTime,
                        it.endTime
                    ) / 60.0
                } ?: 0.0

            sleep

        } catch (e: SecurityException) {
            DebugLogger.log(
                "Sleep Permission revoked",
                Color.Red
            )
            0.0
        }
    }






}