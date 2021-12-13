package com.ozu.cs394.cryptocoins.network.service

import com.ozu.cs394.cryptocoins.model.response.CoinDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsDetailService {
    @GET("/v2")
    suspend fun getCoinsDetailPrice(
        @Query("data") data:String,
        @Query("key") apiKey:String,
        @Query("symbol") symbol:String,
        @Query("data_points") dataPoints:String,
        @Query("interval") interval:String
    ):Response<CoinDetailResponse>
}