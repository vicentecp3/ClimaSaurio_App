# WeatherApp_JetpackCompose

Welcome to WeatherApp_JetpackCompose, a simple weather application built with Jetpack Compose, Coil, Retrofit2, converter-gson, Awesome Font, and Play-services-locations. This app provides real-time weather information based on the user's location using the OpenWeatherMap API.

## About

WeatherApp_JetpackCompose is a modern Android application developed with Jetpack Compose, an Android UI toolkit that simplifies and accelerates UI development on Android. It leverages various libraries such as Coil for image loading, Retrofit2 for API communication, converter-gson for JSON serialization, Awesome Font for icons, and Play-services-locations for accessing the user's location.

## Adding Your API Token

To use this app, you'll need to obtain an API key from OpenWeatherMap. Follow these steps to add your API token to the project:

1. Visit [OpenWeatherMap](https://openweathermap.org/) and sign up for an account.
2. After signing in, navigate to the API Keys section to generate a new API key.
3. Copy the generated API key.
4. Open the project in Android Studio.
5. Locate the `Const.kt` file in the root directory (Should be on: WeatherApp_JetpackCompose/app/src/main/java/th/ch/weatherapp/constant/Const.kt).
6. Add the following line to the file, replacing `<YOUR_API_KEY>` with the actual API key:

   ```properties
   const val openWeatherMapApiKey = "YOUR_API_KEY_HERE";
   ```

   Save the file.

## Installation

Follow these steps to install WeatherApp_JetpackCompose on your device:

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/ThomasHartmannDev/WeatherApp_JetpackCompose.git
   ```

2. Open the project in Android Studio.

3. Build and run the app on your Android device or emulator.

4. You can build the app and install in your own device!

## Screenshots

<p float="left">
   <img src="/Screenshots/Screen_Recording_20240108_141454_WeatherApp-ezgif.com-video-to-gif-converter.gif" alt="Screenshot 1" width="200"/> &nbsp; &nbsp &nbsp; &nbsp
   <img src="/Screenshots/Screenshot_20240108_141249_WeatherApp.png" alt="Screenshot 2" width="200"/> &nbsp; &nbsp &nbsp; &nbsp
   <img src="/Screenshots/Screenshot_20240108_141318_WeatherApp.png" width="200"/>
</p>

## License

WeatherApp_JetpackCompose is licensed under the [MIT License](LICENSE). Feel free to use and modify the code for your own purposes.

---

Enjoy using WeatherApp_JetpackCompose!
