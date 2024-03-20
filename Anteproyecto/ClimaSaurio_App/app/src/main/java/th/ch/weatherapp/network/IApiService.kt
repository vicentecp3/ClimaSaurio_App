package th.ch.weatherapp.network

import retrofit2.http.GET
import retrofit2.http.Query
import th.ch.weatherapp.constant.Const.Companion.openWeatherMapApiKey
import th.ch.weatherapp.model.forecast.ForecastResult
import th.ch.weatherapp.model.weather.WeatherResult

interface IApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appID: String = openWeatherMapApiKey,

    ): WeatherResult

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appID: String = openWeatherMapApiKey,

        ): ForecastResult
}