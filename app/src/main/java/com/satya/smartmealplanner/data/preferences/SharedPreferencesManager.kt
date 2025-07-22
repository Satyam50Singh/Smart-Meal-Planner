package com.satya.smartmealplanner.data.preferences

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesManager(context: Context)  {

    private val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        prefs.edit { putString(key, value) }
    }

    fun getString(key: String, defaultValue: String): String {
        return (prefs.getString(key, defaultValue) ?: defaultValue)
    }

}