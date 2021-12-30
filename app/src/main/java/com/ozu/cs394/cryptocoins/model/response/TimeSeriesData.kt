package com.ozu.cs394.cryptocoins.model.response

import android.os.Parcelable
import android.text.format.DateFormat.format
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class TimeSeriesData(
    val asset_id: Int?,
    val close: Double?,
    val high: Double?,
    val low: Double?,
    val open: Double?,
    val time: Long
):Parcelable{
    val convertedTime:String
        get() {
            val c = Calendar.getInstance()
            c.timeInMillis = time.toInt() * 1000L
            val d = c.time
            val sdf = SimpleDateFormat("dd-MM-yyyy/HH:MM", Locale.getDefault())
            return sdf.format(d)
        }

}

