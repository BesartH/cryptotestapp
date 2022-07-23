package com.cryptoapp.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.cryptoapp.api_interface.CoinsApi
import com.cryptoapp.models.Coins
import com.cryptoapp.models.coinmodel.CoinObject
import com.cryptoapp.repositories.data.CoinResult
import javax.inject.Inject

class CoinsRepository @Inject constructor(private val coinsApi: CoinsApi) {
    private val list: MutableState<Coins> = mutableStateOf(Coins())
    private val coinObject = CoinResult<CoinObject, Exception>()

    suspend fun getCoins(): Coins?{
        try{
            list.value = coinsApi.getCoins()
        }catch (e: Exception){
            return null
        }

        return list.value
    }

    suspend fun getCoin(coinId: String): CoinResult<CoinObject, Exception> {
        try {
            coinObject.coin = coinsApi.getCoin(coinId = coinId)
        }catch (e: Exception){
            e.printStackTrace()
            coinObject.error = e
        }

        return coinObject
    }
}