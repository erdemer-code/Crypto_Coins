package com.ozu.cs394.cryptocoins.network

import com.ozu.cs394.cryptocoins.BuildConfig
import com.ozu.cs394.cryptocoins.network.service.CurrentCoinsPriceService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HomePageNetworkHelper {

    var currentCoinsPriceService: CurrentCoinsPriceService? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.NOMICS_API_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // TODO: Add related network services here!

        currentCoinsPriceService = retrofit.create(CurrentCoinsPriceService::class.java)
    }

    private fun getClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(45, TimeUnit.SECONDS)
        httpClient.readTimeout(45, TimeUnit.SECONDS)
        httpClient.writeTimeout(45, TimeUnit.SECONDS)
        httpClient.addInterceptor(createHttpLoginInterceptor(BuildConfig.DEBUG))
        return httpClient.build()

    }

    private fun createHttpLoginInterceptor(debug: Boolean): HttpLoggingInterceptor {
        val httpLoginInterceptor = HttpLoggingInterceptor()
        if (debug) httpLoginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else httpLoginInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return httpLoginInterceptor

    }

}