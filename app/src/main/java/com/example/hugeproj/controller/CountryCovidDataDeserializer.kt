package com.example.hugeproj.controller

import com.example.hugeproj.model.CountryCovidDataApiResponse
import com.example.hugeproj.model.CountryCovidData
import com.google.gson.*
import java.lang.reflect.Type

class CountryCovidDataDeserializer : JsonDeserializer<CountryCovidDataApiResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CountryCovidDataApiResponse {
        val jsonObject = json?.asJsonObject

        return if (jsonObject != null && jsonObject.has("data")) {
            val dataElement = jsonObject.get("data")

            // Check if data is an object or an array
            val countryCovidData: CountryCovidData? = if (dataElement.isJsonObject) {
                context?.deserialize(dataElement, CountryCovidData::class.java)
            } else {
                null  // If it's an empty array, return null
            }

            CountryCovidDataApiResponse(countryCovidData)
        } else {
            CountryCovidDataApiResponse(null)  // No data available
        }
    }
}

