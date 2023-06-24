package com.sd.laborator.services

import com.sd.laborator.interfaces.LocationSearchInterface
import org.springframework.stereotype.Service
import java.net.URL
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale

@Service
class LocationSearchService : LocationSearchInterface {
    override fun getLocationId(locationName: String): Int {
        // definire API key
        val apiKey = "6cea3c160a0f0c764219bd567ad4cfba"

        // codificare parametru URL (deoarece poate conţine caractere speciale)
        val encodedLocationName = URLEncoder.encode(locationName, StandardCharsets.UTF_8.toString())

        // construire obiect de tip URL
        val locationSearchURL = URL("http://api.openweathermap.org/data/2.5/find?q=$encodedLocationName&appid=$apiKey")

        // preluare raspuns HTTP (se face cerere GET şi se preia conţinutul răspunsului sub formă de text)
        val rawResponse: String = locationSearchURL.readText()

        // parsare obiect JSON
        val responseRootObject = JSONObject(rawResponse)
        val responseContentObject = responseRootObject.getJSONArray("list").takeUnless { it.isEmpty }?.getJSONObject(0)
        return responseContentObject?.getInt("id") ?: -1
    }
}