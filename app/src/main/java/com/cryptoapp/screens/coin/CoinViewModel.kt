package com.cryptoapp.screens.coin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cryptoapp.models.coinmodel.CoinObject
import com.cryptoapp.repositories.CoinsRepository
import com.cryptoapp.repositories.data.CoinResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(private val coinsRepository: CoinsRepository): ViewModel() {
    private val coinResult: MutableState<CoinResult<CoinObject, Exception>> = mutableStateOf(CoinResult(CoinObject(listOf(), listOf(), listOf())))

    suspend fun getCoinById(coinId: String): MutableState<CoinResult<CoinObject, Exception>>{
        coinResult.value = coinsRepository.getCoin(coinId = coinId)

        return coinResult
    }
}