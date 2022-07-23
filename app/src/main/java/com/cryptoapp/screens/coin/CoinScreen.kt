package com.cryptoapp.screens.coin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cryptoapp.R
import com.cryptoapp.models.coinmodel.CoinObject
import com.cryptoapp.repositories.data.CoinResult
import java.lang.Exception

@Composable
fun CoinScreen(coinId: String?, coinName: String?, coinViewModel: CoinViewModel = hiltViewModel()){

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            val coinResult = produceState(initialValue = CoinResult<CoinObject, Exception>(CoinObject(listOf(), listOf(), listOf()))){
                value = coinViewModel.getCoinById(coinId = coinId.toString()).value
            }
            
            Header(coinId, coinName)

            if(coinResult.value.coin != null && !coinResult.value.coin!!.prices.isEmpty()){

                LazyColumn{
                    item {
                        Prices(coinResult.value.coin!!.prices)

                        MarketCaps(coinResult.value.coin!!.market_caps)
                    }
                }
            }else if(coinResult.value.error == null){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 30.dp)
                    )
                }
            }else{
                Toast.makeText(LocalContext.current, "Something went wrong!", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun Header(coinId: String?, coinName: String?) {
    Text(
        text = coinName.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 20.dp, end = 10.dp),
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center
    )
    Text(
        text = stringResource(id = R.string.id)+coinId.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 20.dp),
        style = MaterialTheme.typography.subtitle1,
        fontStyle = FontStyle.Italic,
        color = Color.LightGray,
        textAlign = TextAlign.Center
    )

    Text(
        text = stringResource(id = R.string.last_24_label),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 15.dp, end = 10.dp, bottom = 15.dp),
        style = MaterialTheme.typography.caption,
        fontStyle = FontStyle.Normal,
        color = Color.Black,
        textAlign = TextAlign.Center
    )

    Divider(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp))
}

@Composable
fun MarketCaps(marketCaps: List<List<Double>>) {
    Text(
        text = stringResource(id = R.string.market_caps),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        style = MaterialTheme.typography.h6
    )

    ListContainer(data = marketCaps)
}

@Composable
fun Prices(prices: List<List<Double>>) {

    Text(
        text = stringResource(id = R.string.latest_prices),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        style = MaterialTheme.typography.h6
    )

    ListContainer(data = prices)
}

@Composable
fun ListContainer(data: List<List<Double>>){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(250.dp),
        elevation = 4.dp
    ) {
        LazyColumn{
            items(data){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = data[0][0].toString(),
                        color = Color(0xffCE98C00)
                    )
                    Text(
                        text = data[0][1].toString(),
                        color = Color(0xffCE98C00)
                    )
                }
            }
        }
    }
}
