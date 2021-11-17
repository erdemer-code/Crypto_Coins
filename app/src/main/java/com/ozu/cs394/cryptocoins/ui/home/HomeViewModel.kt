package com.ozu.cs394.cryptocoins.ui.home

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
    private val _currentCoinsPriceLiveData = MutableLiveData<List<CoinResponseModel>>()
    private val networkHelper = NetworkHelper()
    val currentCoinsPriceLiveData: LiveData<List<CoinResponseModel>> = _currentCoinsPriceLiveData


    fun getCurrentCoinsPrice(apiKey:String,coinsList: List<String>,convertedPrice: String){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    networkHelper.currentCoinsPriceService?.getCurrentCoinPrice(apiKey, coinsList, convertedPrice)
                }
                if (response!!.isSuccessful)
                    _currentCoinsPriceLiveData.value = response.body()

            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}