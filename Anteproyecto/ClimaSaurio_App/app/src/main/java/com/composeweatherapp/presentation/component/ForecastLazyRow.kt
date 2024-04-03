package com.composeweatherapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeweatherapp.core.helpers.HourConverter
import com.composeweatherapp.domain.model.ForecastWeather
import com.composeweatherapp.core.utils.WeatherType

@Composable
fun ForecastLazyRow(forecasts: List<ForecastWeather>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 8.dp)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF0077FF), // Azul más oscuro
                            Color(0xFF4D9EFF)  // Azul más claro
                        )
                    )
                )
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(forecasts) {
                if (forecasts.size == 8) {
                    WeatherCard(
                        time = HourConverter.convertHour(it.date.substring(11, 13)),
                        weatherIcon = WeatherType.setWeatherType(
                            it.weatherStatus[0].mainDescription,
                            it.weatherStatus[0].description,
                            HourConverter.convertHour(it.date.substring(11, 13)),
                        ),
                        degree = "${it.weatherData.temp.toInt()}°"
                    )
                } else {
                    WeatherCard(
                        date = it.date.substring(5, 10).replace('-', '/'),
                        time = HourConverter.convertHour(it.date.substring(11, 13)),
                        weatherIcon = WeatherType.setWeatherType(
                            it.weatherStatus[0].mainDescription,
                            it.weatherStatus[0].description,
                            HourConverter.convertHour(it.date.substring(11, 13)),
                        ),
                        degree = "${it.weatherData.temp.toInt()}°"
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCard(date: String? = null, time: String, weatherIcon: Int, degree: String) {
    Card(
        modifier = Modifier
            .padding(8.dp) // Añade un poco de espacio alrededor de la tarjeta
            .border(0.8.dp, Color.Black, shape = MaterialTheme.shapes.large) // Añade borde negro
            .clip(MaterialTheme.shapes.large) // Clip la tarjeta con la forma predeterminada de Material Design
            .shadow(100.dp), // Añade sombra a la tarjeta
        backgroundColor = MaterialTheme.colors.primary // Fondo de la tarjeta
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if (date != null) {
                    Text(text = date, style = MaterialTheme.typography.h3.copy(fontSize = 18.sp), color = Color.Black)
                }
                Text(text = time, style = MaterialTheme.typography.h3.copy(fontSize = 18.sp), color = Color.Black)
            }
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(text = degree, style = MaterialTheme.typography.h3.copy(fontSize = 24.sp), color = Color.Black)
        }
    }
}
