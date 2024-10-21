package com.example.hugeproj.model

data class CountryDataApiResponse (
    val data: List<Country>
)

data class Country(
    val iso: String,
    val name: String
)