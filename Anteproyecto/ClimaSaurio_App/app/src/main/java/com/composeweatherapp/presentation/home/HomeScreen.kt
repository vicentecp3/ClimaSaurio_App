package com.composeweatherapp.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composeweatherapp.R
import com.composeweatherapp.core.utils.AppStrings
import com.composeweatherapp.domain.model.Forecast
import com.composeweatherapp.core.helpers.EpochConverter
import com.composeweatherapp.core.helpers.SetError
import com.composeweatherapp.presentation.component.*
import com.composeweatherapp.core.utils.ErrorCardConsts
import com.composeweatherapp.core.utils.ExceptionTitles

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: HomeViewModel, onNavigateToSearchCityScreen: () -> Unit) {
    val homeCurrentWeatherState by viewModel.homeForecastState.collectAsState()
    val activity = (LocalContext.current as? Activity)

    Scaffold(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        MenuIcon { onNavigateToSearchCityScreen() }
        WeatherSection(homeCurrentWeatherState) { activity?.finish() }
    }
}

@Composable
private fun BackgroundImage() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xBA4CAF50),
                        Color(0xBE8BC34A)
                    )
                )
            )
    ) {}
}

@Composable
private fun WeatherSection(currentWeatherState: HomeForecastState, errorCardOnClick: () -> Unit) {
    when (currentWeatherState) {
        is HomeForecastState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
            }
        }
        is HomeForecastState.Success -> {
            if (currentWeatherState.forecast != null) {
                CurrentWeatherSection(currentWeatherState.forecast)
                DetailsSection(currentWeatherState.forecast)
            }
        }
        is HomeForecastState.Error -> {
            ErrorCard(
                modifier = Modifier.fillMaxSize(),
                errorTitle = currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR,
                errorDescription = SetError.setErrorCard(
                    currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR
                ),
                errorButtonText = ErrorCardConsts.BUTTON_TEXT,
                errorCardOnClick,
                cardModifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 4 + 48.dp)
                    .padding(horizontal = 64.dp)
            )
        }
    }
}

@Composable
private fun CurrentWeatherSection(todayWeather: Forecast) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = todayWeather.cityDtoData.cityName,
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(18.dp)),
            painter = painterResource(id = R.drawable.personajebasico),
            contentDescription = "Personaje basico"
        )
        Text(
            text = "${todayWeather.weatherList[0].weatherData.temp.toInt()}${AppStrings.degree}",
            style = MaterialTheme.typography.h1.copy(color = Color.White), // Cambiar el color del texto a negro
        )

        // Frases graciosas dependiendo de la temperatura y las condiciones climáticas
        val temperature = todayWeather.weatherList[0].weatherData.temp
        val weatherDescription = todayWeather.weatherList[0].weatherStatus[0].description
        val randomFunnyPhrase = when {
            temperature < 10 -> listOf(
                "Hace mucho frío, mejor abrígate bien.",
                "¿Volvemos a la Edad de Hielo?",
                "El frío está así de feo porque ni él se quiere quedar."
            ).random()
            temperature in 10.0..20.0 -> listOf(
                "¡Temperatura agradable para un día tranquilo!",
                "Está en el punto justo, ni frío ni calor.",
                "El clima está como la sopa de mamá, ¡perfecto!"
            ).random()
            temperature >= 25 -> listOf(
                "¡Hace calor! ¿Alguien dijo playa?",
                "¡Qué calor! ¿Nos vamos a la piscina?",
                "El calor está tan fuerte que hasta los pájaros están usando abanicos."
            ).random()
            else -> listOf(
                "Llueve a cántaros, mejor quédate en casa con una taza de chocolate caliente.",
                "Hoy el paraguas es tu mejor amigo.",
                "El clima está tan confundido que hasta las nubes tienen dudas."
            ).random()
        }

        Text(
            text = randomFunnyPhrase,
            style = MaterialTheme.typography.body1.copy(color = Color.Black), // Cambiar el color del texto a negro
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}




@Composable
private fun DetailsSection(forecast: Forecast) {
    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 2)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ForecastSection(forecast)
            WeatherDetailSection(forecast)
        }
    }
}

@Composable
private fun ForecastSection(forecastData: Forecast) {
    ForecastTitle(text = "Pronóstico por hora")
    ForecastLazyRow(forecasts = forecastData.weatherList.take(8))
    ForecastTitle(text = "Pronóstico diario")
    ForecastLazyRow(forecasts = forecastData.weatherList.takeLast(32))
}

@Composable
private fun WeatherDetailSection(currentWeather: Forecast) {
    CurrentWeatherDetailRow(
        title1 = AppStrings.temp,
        value1 = "${currentWeather.weatherList[0].weatherData.temp}${AppStrings.degree}",
        title2 = AppStrings.feels_like,
        value2 = "${currentWeather.weatherList[0].weatherData.feelsLike}${AppStrings.degree}"
    )
    CurrentWeatherDetailRow(
        title1 = AppStrings.cloudiness,
        value1 = "${currentWeather.weatherList[0].cloudiness.cloudiness}%",
        title2 = AppStrings.humidity,
        value2 = "${currentWeather.weatherList[0].weatherData.humidity}%"
    )
    CurrentWeatherDetailRow(
        title1 = AppStrings.sunrise,
        value1 = "${EpochConverter.readTimestamp(currentWeather.cityDtoData.sunrise)} AM",
        title2 = AppStrings.sunset,
        value2 = "${EpochConverter.readTimestamp(currentWeather.cityDtoData.sunset)} PM"
    )
    CurrentWeatherDetailRow(
        title1 = AppStrings.wind,
        value1 = "${currentWeather.weatherList[0].wind.speed}${AppStrings.metric}",
        title2 = AppStrings.pressure,
        value2 = "${currentWeather.weatherList[0].weatherData.pressure}"
    )
}

@Composable
private fun MenuIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 24.dp, end = 24.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}
