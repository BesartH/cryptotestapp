package com.cryptoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cryptoapp.screens.coin.CoinScreen
import com.cryptoapp.screens.home.HomeScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Home.name){
        composable(Screens.Home.name){
            HomeScreen(navController = navController)
        }

        composable(Screens.Coin.name+"/{coinId}/{coinName}", arguments = listOf(navArgument("coinId"){type = NavType.StringType}, navArgument("coinName"){type = NavType.StringType})){ navBackStack ->
//            navBackStack.arguments?.getString("coinId").let { id ->
//                CoinScreen(navController = navController, coinId = id.toString())
//            }

            CoinScreen(coinId = navBackStack.arguments?.getString("coinId"), coinName = navBackStack.arguments?.getString("coinName"))
        }
    }
}

enum class Screens{
    Home,
    Coin
}