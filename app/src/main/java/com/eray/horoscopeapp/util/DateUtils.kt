package com.eray.horoscopeapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getMaxDateForBirthdaySpinners(): Date {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = 2010
        calendar[Calendar.MONTH] = Calendar.DECEMBER
        calendar[Calendar.DAY_OF_MONTH] = 31
        return calendar.time
    }

    fun get100YearAgo(): Date {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.add(Calendar.YEAR, -100)
        return currentCalendar.time
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateToString(date: Date, pattern: String): String? {
        return SimpleDateFormat(pattern).format(date)
    }

}