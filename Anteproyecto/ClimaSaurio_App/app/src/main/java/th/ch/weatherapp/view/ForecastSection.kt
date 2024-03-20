package th.ch.weatherapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import th.ch.weatherapp.constant.Const.Companion.LOADING
import th.ch.weatherapp.constant.Const.Companion.NA
import th.ch.weatherapp.constant.Const.Companion.cardColor
import th.ch.weatherapp.model.forecast.ForecastResult
import th.ch.weatherapp.utils.Utils.Companion.buildIcon
import th.ch.weatherapp.utils.Utils.Companion.timestampToHumanDate

@Composable
fun ForecastSection(forecastResponse: ForecastResult) {
    return Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       forecastResponse.list?.let {
           listForecast ->
           if (listForecast.size > 0){
           }
       }
    }
}
