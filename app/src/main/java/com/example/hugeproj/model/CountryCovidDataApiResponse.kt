package com.example.hugeproj.model

data class CountryCovidDataApiResponse (
    val data: CountryCovidData?
)

data class CountryCovidData(
    val date: String,
    val confirmed: Int,
    val deaths: Int,
    val active: Int,
    val fatality_rate: Float
)
