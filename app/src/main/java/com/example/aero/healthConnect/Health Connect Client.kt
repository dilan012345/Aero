package com.example.aero.healthConnect

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.records.metadata.DataOrigin
class HealthConnectManager(private val context: Context) {

    val healthConnectClient = HealthConnectClient.getOrCreate(context)

    fun isAvailable(): Boolean {
        val status = HealthConnectClient.getSdkStatus(context)

        return status == HealthConnectClient.SDK_AVAILABLE
    }
    val startOfDay = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
    suspend fun readSteps(packagename:String): Long {

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startOfDay,
                    Instant.now()
                )
            )
        )
        val packageManager = context.packageManager

        for (record in response.records) {

            val steps = record.count
            val packageName = record.metadata.dataOrigin.packageName


           println("Source: $packageName Steps: ${record.count} " +
                    "Start: ${record.startTime} End: ${record.endTime}")
           println("Source: " + packageName + " Steps: " + steps)
        }
        val googleFitPackage = "com.google.android.apps.fitness"
        val samsungHealthPackage = "com.sec.android.app.shealth"


        val googleFitAgg = healthConnectClient.aggregate(
            AggregateRequest(
                metrics = setOf(StepsRecord.COUNT_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(startOfDay, Instant.now()),
                dataOriginFilter = setOf(DataOrigin(googleFitPackage))
            )
        )
        val googleFitSteps = googleFitAgg[StepsRecord.COUNT_TOTAL] ?: 0L


        val samsungHealthSteps = response.records
            .filter { it.metadata.dataOrigin.packageName == samsungHealthPackage }
            .sumOf { it.count }



        if (packagename == "com.sec.android.app.shealth"){
            return samsungHealthSteps
        }
        else if(packagename == "com.google.android.apps.fitness"){
            return googleFitSteps
        }
        else
            return 0

    }



    suspend fun readCalories(packagename:String): Double {
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = TotalCaloriesBurnedRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startOfDay,
                    Instant.now()
                )
            )
        )
        for (record in response.records) {
            //println(
                //"Source: ${record.metadata.dataOrigin.packageName}, " +
                       // "Calories: ${record.energy.inKilocalories}"
            //)
        }
        val calories = response.records

            .filter {
                it.metadata.dataOrigin.packageName == packagename
            }
            .sumOf {
                it.energy.inKilocalories
            }
        //println("calories "+ calories)
        return calories

    }









}