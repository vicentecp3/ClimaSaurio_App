package th.ch.weatherapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import th.ch.weatherapp.model.MyLatLng
import th.ch.weatherapp.model.forecast.ForecastResult
import th.ch.weatherapp.model.weather.WeatherResult
import th.ch.weatherapp.network.RetrofitClient

enum class STATE {
    LOADING,
    SUCCESS,
    FAILED
}

class MainViewModel : ViewModel(){
    // Control state of View
    var state by mutableStateOf(STATE.LOADING)

    // Hold value from API for Weather Info
    var weatherResponse: WeatherResult by mutableStateOf(WeatherResult())

    // Hold value from API for Forecast Info
    var forecastResponse: ForecastResult by mutableStateOf(ForecastResult())
    var errorMessage: String by mutableStateOf("")

    fun getWeatherByLocation(latLng: MyLatLng){
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try {
                val apiResponse = apiService.getWeather(latLng.lat, latLng.lng)
                weatherResponse = apiResponse // Update State
                state = STATE.SUCCESS
            } catch (e: Exception){
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
        }
    }

    fun getForecastByLocation(latLng: MyLatLng){
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try {
                val apiResponse = apiService.getForecast(latLng.lat, latLng.lng)
                forecastResponse = apiResponse // Update State
                state = STATE.SUCCESS
            } catch (e: Exception){
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
        }
    }
}