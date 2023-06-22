package com.example.everfit.extensions

import com.example.everfit.data.model.DateWorkout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal fun Calendar.getDayIndex(todayTimestamp: Long): DateWorkout.DateIndex {
    val cal = Calendar.getInstance().apply {
        timeInMillis = todayTimestamp
    }
    val simpleDateFormat = SimpleDateFormat("YYYYmmDD", Locale.ENGLISH)
    val dateFormat = simpleDateFormat.format(time)
    val otherDateFormat = simpleDateFormat.format(cal.time)
    return when {
        dateFormat > otherDateFormat -> DateWorkout.DateIndex.FUTURE

        dateFormat < otherDateFormat -> DateWorkout.DateIndex.PAST

        else -> DateWorkout.DateIndex.TODAY
    }
}