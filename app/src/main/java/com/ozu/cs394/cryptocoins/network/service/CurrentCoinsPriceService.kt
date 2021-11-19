package com.ozu.cs394.cryptocoins.network.service

import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentCoinsPriceService {
   @GET("currencies/ticker")
   suspend fun getCurrentCoinPrice(
       @Query("key") apiKey:String,
       @Query("ids") coinsList: String,
       @Query("convert") convertedPrice: String
   ): Response<List<CoinResponseModel>>


}