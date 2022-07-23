package com.cryptoapp.api_interface

import com.cryptoapp.models.Coins
import com.cryptoapp.models.coinmodel.CoinObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface CoinsApi {
    @GET("coins/list")
    suspend fun getCoins(): Coins

    @GET("coins/{coinId}/market_chart")
    suspend fun getCoin(@Path("coinId") coinId: String, @Query("vs_currency") currency: String = "eur", @Query("days") days: String = "3"): CoinObject
}