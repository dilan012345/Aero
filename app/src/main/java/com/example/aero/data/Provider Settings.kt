package com.example.aero.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import android.content.Context
object ProviderSettings {

    private lateinit var settings: SharedPreferencesSettings

    fun initialise(context: Context) {
        settings = SharedPreferencesSettings(
            context.getSharedPreferences(
                "provider_settings",
                Context.MODE_PRIVATE
            )
        )
    }

    fun saveProvider(provider: ProviderType) {
        settings.putString(
            "active_provider",
            provider.name
        )
    }

    fun getProvider(): ProviderType {
        return ProviderType.valueOf(
            settings.getString(
                "active_provider",
                ProviderType.GOOGLE_HEALTH.name
            )
        )
    }
}