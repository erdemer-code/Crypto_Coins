package com.ozu.cs394.cryptocoins.model.response

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


data class CoinResponseModel(
    val id: String?,
    val currency: String?,
    val symbol: String?,
    val name: String?,
    val logo_url: String?,
    val price: String?,
    val price_date: String?,
    @SerializedName("1d")
    val day1: IntervalModel?,
    @SerializedName("7d")
    val day7: IntervalModel?,
    @SerializedName("30")
    val day30: IntervalModel?,
    @SerializedName("365d")
    val day365: IntervalModel?,
    val ytd: IntervalModel?

) {
    val convertedPriceDate: String?
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val inputFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
            val date = LocalDate.parse(price_date, inputFormatter)
            return outputFormatter.format(date)
        }
    val roundedPrice: String
    get() = String.format("%.2f",price?.toDouble())

}
