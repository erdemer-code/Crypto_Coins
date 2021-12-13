package com.ozu.cs394.cryptocoins.model.response

import android.text.format.DateFormat.format
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


data class TimeSeriesData(
    val asset_id: Int?,
    val close: Double?,
    val high: Double?,
    val low: Double?,
    val open: Double?,
    val time: Long
){
    val convertedTime:String
        get() {
            val c = Calendar.getInstance()
            c.timeInMillis = time.toInt() * 1000L
            val d = c.time
            val sdf = SimpleDateFormat("dd-MM-yyyy/HH:MM", Locale.getDefault())
            return sdf.format(d)
        }

}

