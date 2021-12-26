package com.ozu.cs394.cryptocoins.room

import androidx.room.*
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel

@Dao
interface CoinsDAO {
    @Query("SELECT * FROM Coins")
    suspend fun getAllFavoriteCoins():List<CoinResponseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun makeFavoriteCoin(coin: CoinResponseModel)

    @Delete
    suspend fun deleteCoin(coin: CoinResponseModel)

    @Query("SELECT * FROM Coins WHERE id = :coinId")
    suspend fun getCoin(coinId: String): CoinResponseModel


}