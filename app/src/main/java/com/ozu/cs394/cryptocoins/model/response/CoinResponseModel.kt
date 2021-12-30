package com.ozu.cs394.cryptocoins.model.response

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
@Entity(tableName = "Coins")
data class CoinResponseModel(
    @PrimaryKey
    val id: String,
    val currency: String?,
    val symbol: String?,
    val name: String?,
    val logo_url: String?,
    val price: String?,
    val price_date: String?,
    @Embedded
    @SerializedName("1d")
    val day1: IntervalModel?,
/*    @Embedded
    @SerializedName("7d")
    val day7: IntervalModel?,
    @Embedded
    @SerializedName("30")
    val day30: IntervalModel?,
    @Embedded
    @SerializedName("365d")
    val day365: IntervalModel?,
    @Embedded
    val ytd: IntervalModel?*/
    var isFavorite: Boolean? = false

):Parcelable {
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
    get() = String.format("%.2f",price?.toDouble(),Locale.ENGLISH)

}
