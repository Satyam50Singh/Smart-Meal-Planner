package com.satya.smartmealplanner.data.preferences

import com.satya.smartmealplanner.domain.preferences.PreferenceRepository

class PreferenceRepositoryImpl(
    private val sharedPreferencesManager: SharedPreferencesManager
): PreferenceRepository  {
    override suspend fun saveString(key: String, value: String) {
        sharedPreferencesManager.putString(key, value)
    }

    override suspend fun getString(key: String, defaultValue: String): String {
        return sharedPreferencesManager.getString(key, defaultValue)
    }

}