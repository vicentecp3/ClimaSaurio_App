package th.ch.weatherapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import th.ch.weatherapp.R
import th.ch.weatherapp.constant.Const.Companion.LOADING
import th.ch.weatherapp.model.weather.WeatherResult
import th.ch.weatherapp.utils.Utils.Companion.timestampToHumanDate
import java.util.Calendar

@Composable
fun WeatherSection(weatherResponse: WeatherResult) {

    // Title Section
    var title = ""
    if (!weatherResponse.name.isNullOrEmpty()) {
        weatherResponse.name?.let {
            title = it
        }
    } else {
        weatherResponse.coord?.let {
            title = "${it.lat}/${it.lon}"
        }
    }

    // Subtitle Section
    var subTitle = ""
    val dateVal = (weatherResponse.dt ?: 0)
    subTitle = if (dateVal == 0) LOADING
    else timestampToHumanDate(dateVal.toLong(), "dd-MM-yyyy")

    // Icon Section
    var icon = ""
    var description = ""
    weatherResponse.weather?.let { weatherList ->
        if (weatherList.isNotEmpty()) {
            description = weatherList[0].description ?: LOADING
            icon = weatherList[0].icon ?: LOADING
        }
    }
    // Current Temperature
    var currentTemp = weatherResponse.main?.temp?.let { "$it°" } ?: ""

    WeatherTitleSection(
        text = title,
        subText = subTitle,
        fontSize = 40.sp,
    )

    Image(
        modifier = Modifier.size(250.dp),
        painter = painterResource(id = R.drawable.personajebasico),
        contentDescription = ""
    )

    WeatherTitleSection(
        text = currentTemp,
        subText = description,
        fontSize = 50.sp,
    )

    // RECTÁNGULOS
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        repeat(4) { index ->
            Spacer(modifier = Modifier.width(8.dp))
            // Each box shows the weather forecast for the next hour
            Box(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val nextHour = getNextHour(currentTime, index + 0)
                    WeatherTitleSection(text = nextHour, subText = "", fontSize = 20.sp)
                    Image(
                        modifier = Modifier.size(45.dp),
                        painter = painterResource(id = R.drawable.solpuro),
                        contentDescription = "SolPuro"
                    )
                    // Temperature for 11 am
                    var TempHora = weatherResponse.main?.temp?.let { "$it°" } ?: ""
                    WeatherTitleSection(text = TempHora, subText = description, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

    // Large rounded rectangle
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 24.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
    )

    // Primer rectángulo
    Row {
        Box(
            modifier = Modifier
                .padding(end = 10.dp, top = 24.dp)
                .size(170.dp)
                .background(color = Color.White, shape = RoundedCornerShape(7.dp))
        ) {
            val currentTemp = weatherResponse.main?.humidity?.let { "$it%" } ?: ""
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Alinea los elementos al centro horizontalmente
                modifier = Modifier.padding(36.dp)
            ) {
                Text(
                    text = "Humedad",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Image(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.humedasicono),
                    contentDescription = "SolPuro"
                )
                Text(
                    text = currentTemp,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }

        // Segundo rectángulo
        Box(
            modifier = Modifier
                .padding(start = 11.dp, top = 24.dp)
                .size(170.dp)
                .background(color = Color.White, shape = RoundedCornerShape(7.dp))
        ) {
            val windSpeed = weatherResponse.wind?.speed?.let { "${it} Km/h" } ?: ""
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Alinea los elementos al centro horizontalmente
                modifier = Modifier.padding(36.dp)
            ) {
                Text(
                    text = "Viento",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Image(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.vientoicono),
                    contentDescription = "SolPuro"
                )
                Text(
                    text = windSpeed,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}
@Composable
fun WeatherTitleSection(text: String, subText: String, fontSize: TextUnit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, fontSize = fontSize, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

fun getNextHour(currentHour: Int, nextHourOffset: Int): String {
    val nextHour = (currentHour + nextHourOffset) % 24
    val tipo = if
                       (nextHour < 12) "am" else "pm"

    // Formatear la hora como cadena
    val hourString = if (nextHour == 0 || nextHour == 12) {
        "12" // Tratar la medianoche como 12:00 am y el mediodía como 12:00 pm
    } else if (nextHour < 10) {
        " $nextHour" // Agregar un espacio al principio para horas menores de 10
    } else {
        nextHour.toString()
    }
    return "$hourString $tipo"
}