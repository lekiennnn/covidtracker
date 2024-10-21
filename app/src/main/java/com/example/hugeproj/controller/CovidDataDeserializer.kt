package com.example.hugeproj.controller

import com.example.hugeproj.model.GlobalCovidData
import com.example.hugeproj.model.GlobalCovidDataApiResponse
import com.google.gson.*
import java.lang.reflect.Type

class CovidDataDeserializer : JsonDeserializer<GlobalCovidDataApiResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): GlobalCovidDataApiResponse {
        val jsonObject = json?.asJsonObject

        return if (jsonObject != null && jsonObject.has("data")) {
            val dataElement = jsonObject.get("data")

            // Check if data is an object or an array
            val globalCovidData: GlobalCovidData? = if (dataElement.isJsonObject) {
                context?.deserialize(dataElement, GlobalCovidData::class.java)
            } else {
                null  // If empty array, return null
            }

            GlobalCovidDataApiResponse(globalCovidData)
        } else {
            GlobalCovidDataApiResponse(null)  // No data available
        }
    }
}
