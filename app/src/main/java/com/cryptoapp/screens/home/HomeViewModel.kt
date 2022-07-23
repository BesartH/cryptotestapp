package com.cryptoapp.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cryptoapp.models.Coins
import com.cryptoapp.models.CoinsItem
import com.cryptoapp.repositories.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val coinsRepository: CoinsRepository): ViewModel() {

//    Option 1: Get coins list using viewModelScope and MutableState

//    val list: MutableState<Coins> = mutableStateOf(Coins())
//
//    fun getCoinsList(): MutableState<Coins>{
//
//        viewModelScope.launch {
//            list.value = coinsRepository.getCoins()
//        }
//
//        return list
//    }

//  Option 2: Get coins list using a suspend function
    suspend fun getCoinsList(): Coins?{
        return coinsRepository.getCoins()
    }

//    Search coins by name
    fun searchList(list: Coins, query: String): List<CoinsItem> {

        val newList = list.filter { coinsItem ->
//            coinsItem.name == query
            coinsItem.name.contains(query)
        }

        return newList
    }
}