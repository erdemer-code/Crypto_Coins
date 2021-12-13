package com.ozu.cs394.cryptocoins.ui.coin_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozu.cs394.cryptocoins.model.response.CoinDetailResponse
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel
import com.ozu.cs394.cryptocoins.model.response.TimeSeriesData
import com.ozu.cs394.cryptocoins.network.DetailPageNetworkHelper
import com.ozu.cs394.cryptocoins.network.HomePageNetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinDetailViewModel : ViewModel() {
    private val networkHelper = DetailPageNetworkHelper()

    private val _coinDetailPriceLiveData = MutableLiveData<List<TimeSeriesData>>()
    val coinDetailPriceLiveData: LiveData<List<TimeSeriesData>> = _coinDetailPriceLiveData

    private val _detailLoadingLiveData = MutableLiveData<Boolean>()
    val detailLoadingLiveData: LiveData<Boolean> = _detailLoadingLiveData


    fun getCoinDetailPrices(data:String,apiKey:String,symbol:String,dataPoints:String,interval:String){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    networkHelper.coinsDetailService?.getCoinsDetailPrice(data, apiKey, symbol, dataPoints, interval)
                }
                if (response?.isSuccessful == true){
                    _coinDetailPriceLiveData.value = response.body()?.data?.get(0)?.timeSeries
                    _detailLoadingLiveData.value = true
                } else{
                    _detailLoadingLiveData.value = false
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

}