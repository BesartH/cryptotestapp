package com.cryptoapp.repositories.data

data class CoinResult<CoinObject, Exception> (
    var coin: CoinObject? = null,
    var error: Exception? = null
)