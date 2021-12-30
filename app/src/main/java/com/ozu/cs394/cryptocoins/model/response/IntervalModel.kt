package com.ozu.cs394.cryptocoins.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class IntervalModel(
    val volume:String?,
    val price_change:String?,
    val price_change_pct:String?,
    val volume_change:String?,
    val volume_change_pct:String?,
    val market_cap_change: String?,
    val market_cap_change_pct: String?
):Parcelable {
    val formattedPriceChange:String
    get() = String.format("%.2f",price_change_pct!!.toDouble() * 100,Locale.ENGLISH)
}
