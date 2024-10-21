package com.example.hugeproj.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidDataAPI {
    @GET("reports/total")
    suspend fun getGlobalCovidData(
        @Query("date") date: String
    ): Response<GlobalCovidDataApiResponse>

    @GET("regions?per_page=219&order=iso&sort=asc")
    suspend fun getCountries(): Response<CountryDataApiResponse>

    @GET("reports/total")
    suspend fun getCountryCovidData(
        @Query("date") date: String,
        @Query("iso") iso: String
    ): Response<CountryCovidDataApiResponse>
}
