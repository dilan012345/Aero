package com.example.aero.data

import android.content.Context

object ProviderManager {

    fun getActiveProvider(context: Context): Provider {
        return when (ProviderSettings.getProvider()) {
            ProviderType.GOOGLE_HEALTH -> GoogleHealthProvider(context)
            ProviderType.SAMSUNG_HEALTH -> SamsungHealthProvider(context)
            ProviderType.STRAVA -> StravaProvider()
            ProviderType.UNIFIED -> UnifiedProvider(context)
        }
    }
}