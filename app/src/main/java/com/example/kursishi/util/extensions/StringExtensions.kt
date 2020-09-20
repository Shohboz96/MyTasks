package com.example.kursishi.util.extensions


import java.text.SimpleDateFormat
import java.util.*


fun String.toLongDate(pattern: String = "dd MM yyyy HH:mm"): Long =
    SimpleDateFormat(pattern, Locale.getDefault()).parse(this).time

fun String.toDate(): Date =
    SimpleDateFormat("dd MM yyyy HH:mm", Locale.getDefault()).parse(this)



