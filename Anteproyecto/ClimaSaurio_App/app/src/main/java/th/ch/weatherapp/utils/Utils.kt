package th.ch.weatherapp.utils

import java.text.SimpleDateFormat

class Utils {
    companion object{
        fun timestampToHumanDate(timeStamp: Long, format: String): String{
            val sdf = SimpleDateFormat(format)
            return sdf.format(timeStamp * 1000)
        }

        fun buildIcon(icon:String, isBigsize:Boolean = true):String{
            return if(isBigsize){
                "https://openweathermap.org/img/wn/$icon@4x.png"
            } else {
                "https://openweathermap.org/img/wn/$icon.png"
            }
        }
    }
}