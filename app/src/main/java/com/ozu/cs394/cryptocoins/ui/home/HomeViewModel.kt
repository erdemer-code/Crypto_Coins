package com.ozu.cs394.cryptocoins.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel
import com.ozu.cs394.cryptocoins.network.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val networkHelper = NetworkHelper()

    private val _currentCoinsPriceLiveData = MutableLiveData<List<CoinResponseModel>>()
    val currentCoinsPriceLiveData: LiveData<List<CoinResponseModel>> = _currentCoinsPriceLiveData

    private val _homeLoadingLiveData = MutableLiveData<Boolean>()
    val homeLoadingLiveData:LiveData<Boolean> = _homeLoadingLiveData


    fun getCurrentCoinsPrice(apiKey:String,coinsList: List<String>,convertedPrice: String){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    networkHelper.currentCoinsPriceService?.getCurrentCoinPrice(apiKey, convertListToString(coinsList), convertedPrice)
                }
                if (response!!.isSuccessful) {
                    _currentCoinsPriceLiveData.value = response.body()
                    _homeLoadingLiveData.value = true
                } else {
                    _homeLoadingLiveData.value = false
                }

            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun convertListToString(list: List<String>):String{
        var str = ""
        list.forEach {
            str += "${it},"
        }
        return str
    }
}