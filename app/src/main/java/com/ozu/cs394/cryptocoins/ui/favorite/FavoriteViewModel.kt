package com.ozu.cs394.cryptocoins.ui.favorite

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel
import com.ozu.cs394.cryptocoins.room.CoinsDAOImpl
import com.ozu.cs394.cryptocoins.room.CoinsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel : ViewModel() {
    private val _favoriteCoinsLiveData = MutableLiveData<List<CoinResponseModel>>()
    val favoriteCoinsLiveData: LiveData<List<CoinResponseModel>> = _favoriteCoinsLiveData

    fun getAllFavoriteCoins(context:Context){
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO){
                    val db = CoinsDAOImpl(CoinsDatabase.getInstance(context))
                    Log.e("DB",db.getAllFavoriteCoins().toString())
                    _favoriteCoinsLiveData.postValue(db.getAllFavoriteCoins())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }
}