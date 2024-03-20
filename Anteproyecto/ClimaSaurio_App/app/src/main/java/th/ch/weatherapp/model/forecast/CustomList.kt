package th.ch.weatherapp.model.forecast

import android.opengl.Visibility
import com.google.gson.annotations.SerializedName
import th.ch.weatherapp.model.weather.Clouds
import th.ch.weatherapp.model.weather.Main
import th.ch.weatherapp.model.weather.Sys
import th.ch.weatherapp.model.weather.Weather
import th.ch.weatherapp.model.weather.Wind

data class CustomList(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("weather") var weather: ArrayList<Weather>? = arrayListOf(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("sys") var Sys: Sys? = Sys(),
    @SerializedName("dt_txt") var dtTxt: String? = null,
    )
