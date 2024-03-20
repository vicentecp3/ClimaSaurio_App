package th.ch.weatherapp.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherResult (
    @SerializedName("coord") var coord: Coord? = Coord(),
    @SerializedName("weather") var weather: ArrayList<Weather>? = arrayListOf(),
    @SerializedName("base") var base: String? = null,
    @SerializedName("main") var main: Main? = null,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("clouds") var Clouds: Clouds? = Clouds(),
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cod") var cod: Int? = null,
    @SerializedName("snow") var snow: Snow? = Snow(),
    @SerializedName("hourlyTemperature") var hourlyTemperature: List<Double>? = null // Agrega este campo para almacenar la temperatura por hora

)


