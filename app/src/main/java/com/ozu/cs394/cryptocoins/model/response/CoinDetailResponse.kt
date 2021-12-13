package com.ozu.cs394.cryptocoins.model.response

data class CoinDetailResponse(
    val data: List<CoinDetailData>
)

data class CoinDetailData(
    val id:Int?,
    val name: String?,
    val symbol: String?,
    val price: Double?,
    val timeSeries: List<TimeSeriesData>
)


