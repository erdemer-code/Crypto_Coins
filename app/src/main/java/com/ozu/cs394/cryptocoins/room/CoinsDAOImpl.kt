package com.ozu.cs394.cryptocoins.room

import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel

class CoinsDAOImpl(private val coinsDatabase: CoinsDatabase):CoinsDAO {
    override suspend fun getAllFavoriteCoins(): List<CoinResponseModel> = coinsDatabase.coinsDAO().getAllFavoriteCoins()

    override suspend fun makeFavoriteCoin(coin: CoinResponseModel) = coinsDatabase.coinsDAO().makeFavoriteCoin(coin)

    override suspend fun deleteCoin(coin: CoinResponseModel) = coinsDatabase.coinsDAO().deleteCoin(coin)
    override suspend fun getCoin(coinId: String): CoinResponseModel = coinsDatabase.coinsDAO().getCoin(coinId)




}