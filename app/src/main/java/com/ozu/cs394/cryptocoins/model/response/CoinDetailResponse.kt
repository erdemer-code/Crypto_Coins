package com.ozu.cs394.cryptocoins.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinDetailResponse(
    val data: List<CoinDetailData>
):Parcelable

@Parcelize
data class CoinDetailData(
    val id:Int?,
    val name: String?,
    val symbol: String?,
    val price: Double?,
    val timeSeries: List<TimeSeriesData>
):Parcelable


