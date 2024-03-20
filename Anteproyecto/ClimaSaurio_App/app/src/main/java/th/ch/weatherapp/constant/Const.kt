package th.ch.weatherapp.constant

class Const {
    companion object {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            //android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            //android.Manifest.permission.INTERNET,
        )
        const val openWeatherMapApiKey = "832c7b8ce75003423541ed874879bdd4";

        const val colorBg1 = 0xff08203e;
        const val colorBg2 = 0xff557c93;
        const val cardColor = 0xFF333639;

        const val LOADING = "Loading..."
        const val NA = "N/A"
    }
}
