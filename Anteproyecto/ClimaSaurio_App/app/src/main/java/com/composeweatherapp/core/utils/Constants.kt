package com.composeweatherapp.core.utils

object NetworkService {
    const val BASE_URL: String = "https://api.openweathermap.org"
    const val API_KEY: String = "832c7b8ce75003423541ed874879bdd4"
    const val UNITS: String = "metric"
    const val FORECAST_END_POINT = "/data/2.5/forecast"
}

object Database {
    const val forecast_table = "forecast_data"
    const val database_name = "weather_data.db"
    const val city_table = "city_data"
    const val my_city_table = "my_city"
}

object Constants {
    const val UNKNOWN_ERROR = "An unknown error occurred."
    const val FILL_FIELD = "Please fill in the field."
    const val UNKNOWN_HOST = "Unable to resolve host \"api.openweathermap.org\": No address associated with hostname"
}

object ExceptionTitles {
    const val GPS_DISABLED = "GPS Disabled"
    const val NO_PERMISSION = "No Permission"
    const val NO_INTERNET_CONNECTION = "No Internet Connection"
    const val UNKNOWN_ERROR = "Unknown Error"
}

object ExceptionDescriptions {
    const val GPS_DISABLED_DESCR = "Your GPS seems to be disabled, please enable it."
    const val NO_PERMISSION_DESCR = "Allow otherwise location tracking won't work."
    const val NO_INTERNET_CONNECTION_DESCR = "Please check your internet connection."
    const val UNKNOWN_ERROR_DESCR = "Something went wrong."
}

object ErrorCardConsts {
    const val BUTTON_TEXT = "OK"
}

object AppStrings {

    // HomeScreen -> WeatherDetailSection
    const val temp = "🌡 TEMP"
    const val feels_like = "🌡 SENSACIÓN"
    const val cloudiness = "☁ ABUNDANCIA DE NUBES"
    const val humidity = "💧 HUMEDAD"
    const val sunrise = "🌇 AMANECER"
    const val sunset = "🌆 ATARDECER"
    const val wind = "🌬 VIENTO"
    const val metric = "KM"
    const val pressure = "⏲ PRESIÓN"
    const val degree = "°"

    // SearchCityScreen
    const val topbar_title = "Weather"

    // SearchCityScreen -> SearchField
    const val placeholder = "Search for a city"

    // SearchCityScreen -> CityWeatherList
    const val subtitle1 = "My Cities"
    const val subtitle2 = "Search Result"
    const val no_city = "You don't have any city"

    // SearchCityScreen -> SearchCityScreenContent
    const val error_title = "OOOOPS!!!"
}

object WeatherConditions {
    const val CLEAR_SKY = "clear sky"
    const val FEW_CLOUDS = "few clouds"
    const val SCATTERED_CLOUDS = "scattered clouds"
    const val BROKEN_CLOUDS = "broken clouds"
    const val SHOWER_RAIN = "shower rain"
    const val RAIN = "rain"
    const val THUNDERSTORM = "thunderstorm"
    const val SNOW = "snow"
    const val MIST = "mist"
}

object MainWeatherConditions {
    const val CLOUDS = "Clouds"
    const val SNOW = "Snow"
    const val RAIN = "Rain"
    const val THUNDERSTORM = "Thunderstorm"
    const val CLEAR = "Clear"
}