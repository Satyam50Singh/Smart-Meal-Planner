package com.satya.smartmealplanner.domain.preferences

interface PreferenceRepository {
    suspend fun saveString(key: String, value: String)
    suspend fun getString(key: String, defaultValue: String): String
}