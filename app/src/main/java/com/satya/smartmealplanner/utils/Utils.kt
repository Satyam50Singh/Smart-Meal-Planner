package com.satya.smartmealplanner.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

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

}