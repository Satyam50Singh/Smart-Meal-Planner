package com.satya.smartmealplanner.utils

import com.satya.smartmealplanner.data.preferences.PreferenceKeys
import com.satya.smartmealplanner.data.preferences.SharedPreferencesManager
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceIdProvider @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    fun getDeviceId(): String {
        val deviceId = sharedPreferencesManager.getString(PreferenceKeys.DEVICE_ID, "")
        if (deviceId.isNotEmpty()) return deviceId
        val newDeviceId = UUID.randomUUID().toString()
        sharedPreferencesManager.putString(PreferenceKeys.DEVICE_ID, newDeviceId)
        return newDeviceId
    }
}