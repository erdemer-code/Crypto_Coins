package com.ozu.cs394.cryptocoins.model.response

data class IntervalModel(
    val volume:String?,
    val price_change:String?,
    val price_change_pct:String?,
    val volume_change:String?,
    val volume_change_pct:String?,
    val market_cap_change: String?,
    val market_cap_change_pct: String
) {
    val formattedPriceChange:String
    get() = String.format("%.2f",price_change_pct!!.toDouble() * 100)
}
