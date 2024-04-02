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

        // Obtener la temperatura en Celsius
        val temperatureCelsius = todayWeather.weatherList[0].weatherData.temp
        // Imprimir en la consola para verificar los datos
        println("Temperatura: $temperatureCelsius")

        val lluviaFuerte = temperatureCelsius  < 10
        val lluviaLigera = temperatureCelsius >= 10 && temperatureCelsius < 25
        val muchoCalor = temperatureCelsius >= 25


        // Nombre de la imagen según la temperatura
        val imageResId = when {
            lluviaFuerte -> R.drawable.lluviafuerte
            lluviaLigera -> R.drawable.lluvialigera
            muchoCalor -> R.drawable.muchocalor
            else -> R.drawable.personajebasico // Imagen por defecto si no coincide con ninguna
        }

        Image(
            modifier = Modifier
                .size(170.dp)
                .clip(RoundedCornerShape(18.dp)),
            painter = painterResource(id = imageResId),
            contentDescription = "Avatar"
        )

        Text(
            text = "${temperatureCelsius.toInt()}${AppStrings.degree}",
            style = MaterialTheme.typography.h1.copy(color = Color.White),
        )

        // Frases divertidas, aptas para un público más general
        val funnyPhrases = listOf(
            "¿Por qué el clima nunca gana en una pelea? ¡Porque siempre se nubla!",
            "¿Cuál es el clima más tímido? ¡El neblinoso!",
            "¿Qué hace el sol cuando está aburrido? ¡Se pone naranja!",
            "¿Por qué la lluvia nunca llega tarde? ¡Porque siempre cae en el momento justo!",
            "¿Qué hace el viento cuando se divierte? ¡Hace volar las hojas en un torbellino de risa!",
            "¿Cuál es el clima más romántico? ¡El que te hace querer abrazar a alguien!",
            "¿Qué hace una nube cuando está triste? ¡Llora lágrimas de lluvia!",
            "¿Cuál es el clima favorito de los artistas? ¡El que pinta el cielo de colores!",
            "¿Por qué el clima siempre tiene la última palabra? ¡Porque siempre hace un pronóstico!"
        )

        val randomFunnyPhrase = funnyPhrases.random()

        Text(
            text = randomFunnyPhrase,
            style = MaterialTheme.typography.body1.copy(color = Color.Black),
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
