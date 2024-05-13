package com.nedash.pressure.blood.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

const val DATE_PATTERN = "yyyy.MM.dd"
const val TIME_PATTERN = "HH:mm"

@SuppressLint("SimpleDateFormat")
fun convertLongToDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat(DATE_PATTERN)
    return format.format(date)
}

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat(TIME_PATTERN)
    return format.format(date)
}

@SuppressLint("SimpleDateFormat")
fun convertDateToLong(date: String): Long {
    val df = SimpleDateFormat(DATE_PATTERN)
    return df.parse(date)!!.time
}

@SuppressLint("SimpleDateFormat")
fun convertTimeToLong(time: String): Long {
    val df = SimpleDateFormat(TIME_PATTERN)
    return df.parse(time)!!.time
}