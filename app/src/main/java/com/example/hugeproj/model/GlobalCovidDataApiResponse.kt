package com.example.hugeproj.model

data class GlobalCovidDataApiResponse(
    val data: GlobalCovidData?  // Nullable to handle the empty data case
)

data class GlobalCovidData(
    val date: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int,
    val active: Int,
    val fatality_rate: Float
)
