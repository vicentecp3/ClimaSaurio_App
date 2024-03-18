package com.example.climasaurio

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.climasaurio.ui.theme.ClimaSaurioTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap


const val MY_PERMISSIONS_REQUEST_INTERNET = 123

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClimaSaurioTheme {
                MainScreen()
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.INTERNET),
                MY_PERMISSIONS_REQUEST_INTERNET)
        }
    }
}

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Any,
    val name: String
)

data class Weather(
    val description: String
)


interface OpenWeatherMapService {
    @GET("/weather")
    open fun getCurrentWeather(@QueryMap options: MutableMap<String, String>): Call<List<Weather>>

    /*suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("apikey") apiKey: String
    ): WeatherResponse*/
}

object RetrofitClient {
    //private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val openWeatherMapService: OpenWeatherMapService by lazy {
        retrofit.create(OpenWeatherMapService::class.java)
    }
}

@Composable
fun WeatherScreen(weatherDescription: String, temperature: Float, humidity: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Descripción del clima: $weatherDescription",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Temperatura: $temperature",
            style = MaterialTheme.typography.body1,
            fontSize = 16.sp
        )
        Text(
            text = "Humedad: $humidity",
            style = MaterialTheme.typography.body1,
            fontSize = 16.sp
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen() {
    var weatherDescription by remember { mutableStateOf("Loading...") }
    var temperature by remember { mutableStateOf(0f) }
    var humidity by remember { mutableStateOf(0) }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

   CoroutineScope(Dispatchers.IO).launch {
       try {
           /*val apiKey = "832c7b8ce75003423541ed874879bdd4" // Reemplaza con tu clave API
           val response = RetrofitClient.openWeatherMapService.getCurrentWeather("Madrid", apiKey)
           weatherDescription = response.weather.firstOrNull()?.description ?: "No data"
           temperature = response.main.temp
           humidity = response.main.humidity*/
           val data: MutableMap<String, String> = HashMap()
           data["lat"] = "29.1422"
           data["lon"] = "-13.5066"
           data["appid"] = "832c7b8ce75003423541ed874879bdd4"
           val response = RetrofitClient.openWeatherMapService.getCurrentWeather(data).execute()
           Log.e("MESSAGE", response.message())
           if (response.isSuccessful) {
               val response2 = response.body() as WeatherResponse
               Log.e("SUCCESS", response2.weather.toString())
           } else {
               val response2 = response.errorBody() as WeatherResponse
               Log.e("ERROR", response2.name)
           }
       } catch (e: Exception) {
           e.printStackTrace()
           // Manejar errores
       }
    }

    WeatherScreen(weatherDescription, temperature, humidity)
}


@Preview(showBackground = true)
@Composable
fun PreviewWeatherScreen() {
    ClimaSaurioTheme {
        WeatherScreen("Sunny", 25.0f, 50)
    }
}