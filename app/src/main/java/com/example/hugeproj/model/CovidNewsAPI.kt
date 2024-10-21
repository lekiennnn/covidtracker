package com.example.hugeproj.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CovidNewsAPI {
    @GET("articles")
    suspend fun getCovidNews(): Response<CovidNewsApiResponse>
}

//interface CovidNewsAPI {
//    @Headers(
//        "x-rapidapi-key: 50cd79dee3mshbcd05a91f768ddfp19b3cajsnd1acca49e394",
//        "x-rapidapi-host: covid-19-news.p.rapidapi.com"
//    )
//    @GET("v1/covid")
//    suspend fun getCovidNews(
//        @Query("q") query: String = "coronavirus",
//        @Query("lang") lang: String = "en",
//        @Query("media") media: String = "True"
//    ): Response<CovidNewsApiResponse>
//}
