package com.example.kursishi.util.extensions

import com.example.kursishi.data.models.Times
import java.util.*

fun Calendar.checkDate(time: Times): Boolean {
    return time.year.toInt() == this[Calendar.YEAR] && time.month.toInt() == this[Calendar.MONTH] && time.day.toInt() == this[Calendar.DAY_OF_MONTH]
}

fun Calendar.checkTime(time: Times): Boolean {
    return time.hour.toInt() < this[Calendar.HOUR_OF_DAY] || time.minute.toInt() < this[Calendar.MINUTE]
}