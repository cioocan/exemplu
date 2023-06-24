package com.sd.laborator.services

import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

// definire map care contine "blacklistul" -> mai bine zis whitelist: informatia meteo despre o anumita tara la care poate
// avea acces alta tara
val whiteList = hashMapOf<String,Array<String>>(
    "US" to arrayOf("GB","IT"),
    "GB" to arrayOf("RO","DE")
)

@Service
class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface{
    override fun getForecastData(locationId: Int): WeatherForecastData? {
        // definire API key
        val apiKey = "6cea3c160a0f0c764219bd567ad4cfba"

        // definire locatia default a utilizatorului
        val userCountry = Locale.getDefault().country

        // ID-ul locaţiei nu trebuie codificat, deoarece este numeric
        val forecastDataURL = URL("http://api.openweathermap.org/data/2.5/forecast?id=$locationId&appid=$apiKey")

        // preluare conţinut răspuns HTTP la o cerere GET către URL-ul de mai sus
        val rawResponse: String = forecastDataURL.readText()

        // parsare obiect JSON primit
        val responseRootObject = JSONObject(rawResponse)
        val weatherDataObject = responseRootObject.getJSONArray("list").getJSONObject(0)

        val city = weatherDataObject.getJSONObject("wind").getFloat("speed")
        val country = responseRootObject.getJSONObject("city").getString("country")

        val allowedCountries = whiteList[userCountry]

        if (allowedCountries != null) {
            if (country in allowedCountries)
                // construire şi returnare obiect POJO care încapsulează datele meteo
                return WeatherForecastData(
                    location = responseRootObject.getJSONObject("city").getString("name"),
                    date = timeService.getCurrentTime(),
                    weatherState = weatherDataObject.getJSONArray("weather").getJSONObject(0).getString("description"),
                    weatherStateIconURL = "openweathermap.org/img/wn/${weatherDataObject.getJSONArray("weather").getJSONObject(0).getString("icon")}.png",
                    windGust = weatherDataObject.getJSONObject("wind").getFloat("gust").roundToInt(),
                    windSpeed = weatherDataObject.getJSONObject("wind").getFloat("speed").roundToInt(),
                    minTemp = weatherDataObject.getJSONObject("main").getFloat("temp_min").roundToInt() - 273,
                    maxTemp = weatherDataObject.getJSONObject("main").getFloat("temp_max").roundToInt() - 273,
                    currentTemp = weatherDataObject.getJSONObject("main").getFloat("temp").roundToInt() - 273,
                    humidity = weatherDataObject.getJSONObject("main").getFloat("humidity").roundToInt()
                )
        }
        return null
    }
}