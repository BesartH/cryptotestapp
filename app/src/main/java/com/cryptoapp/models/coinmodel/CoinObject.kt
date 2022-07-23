package com.cryptoapp.models.coinmodel

data class CoinObject(
    val market_caps: List<List<Double>>,
    val prices: List<List<Double>>,
    val total_volumes: List<List<Double>>
)