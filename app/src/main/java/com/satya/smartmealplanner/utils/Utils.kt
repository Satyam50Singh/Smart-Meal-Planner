package com.satya.smartmealplanner.utils

import android.content.Context
import android.provider.Settings
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.UUID

object Utils {
    fun getCurrentDate(): String {
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    }

    fun getNextWeekStartDate(): String {
        val date = Date.from(
            LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()
        )
       return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
    }

    fun getCurrentDay(): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
    }

    fun getDeviceId(context: Context): String {
        return UUID.randomUUID().toString()
    }
}