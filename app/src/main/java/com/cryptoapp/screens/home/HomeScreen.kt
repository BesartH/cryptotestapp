package com.cryptoapp.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cryptoapp.R
import com.cryptoapp.models.Coins
import com.cryptoapp.models.CoinsItem
import com.cryptoapp.navigation.Screens

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()){
    val list = remember { mutableStateOf(Coins()) }
    val filteredList = remember { mutableStateOf(emptyList<CoinsItem>()) }
    val isFiltering = remember { mutableStateOf(false) }
    val loadingFailed = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true){
        try {
            list.value = homeViewModel.getCoinsList()!!
            loadingFailed.value = false
        }catch (e: Exception){
            loadingFailed.value = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchForm(list, filteredList, isFiltering, homeViewModel)

        if(filteredList.value.isNotEmpty()){
            if(isFiltering.value){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 30.dp)
                    )
                }
            }else{
                LazyColumn{
                    items(filteredList.value){ item ->
                        Coin(navController = navController, coinsItem = item)
                    }
                }
            }
        }else{
            if(!loadingFailed.value && list.value.size == 0){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 30.dp)
                    )
                }
            }else if(!loadingFailed.value){
                LazyColumn{
                    items(list.value){ item ->
                        Coin(navController = navController, coinsItem = item)
                    }
                }
            }else{
                ShowAlert()
            }
        }
    }
}

@Composable
fun ShowAlert() {
    val showDialog = remember {
        mutableStateOf(true)
    }

    if(showDialog.value){
        AlertDialog(
            onDismissRequest = {
                showDialog.value =false
            },
            title = {
                Text(text = stringResource(id = R.string.error_title), fontSize = 20.sp)
            },
            text = {
                Text(text = stringResource(id = R.string.error_message), fontSize = 16.sp)
            },
            buttons = {
                Text(
                    text = stringResource(id = R.string.ok),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            showDialog.value = false
                        },
                    style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 18.sp, textAlign = TextAlign.Center)
                )
            },
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    list: MutableState<Coins>,
    filteredList: MutableState<List<CoinsItem>>,
    isFiltering: MutableState<Boolean>,
    homeViewModel: HomeViewModel
) {
    val query = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query.value,
        onValueChange = {query.value = it},
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
        label = { Text(text = stringResource(id = R.string.search))},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            keyboardController?.hide()

            isFiltering.value = true

            if(query.value.isNullOrEmpty()){
                filteredList.value = emptyList()
            }else{
                filteredList.value = homeViewModel.searchList(list = list.value, query = query.value)
            }

            isFiltering.value = false
        }
    )
}

@Composable
fun Coin(navController: NavController, coinsItem: CoinsItem){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate("${Screens.Coin.name}/${coinsItem.id}/${coinsItem.name}")
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = coinsItem.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = coinsItem.symbol,
                style = TextStyle(
                    color = Color.Red.copy(0.5f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text =  "${stringResource(id = R.string.id)} ${coinsItem.id}",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Right
                )
            )
        }
    }
}