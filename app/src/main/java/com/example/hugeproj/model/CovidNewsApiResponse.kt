package com.example.hugeproj.model

data class CovidNewsApiResponse (
    val articles: List<Article>
)

data class Article(
//    val summary: String?,
//    val country: String?,
    val author: String?,
    val media: String?,
    val title: String?,
//    val published_date: String?,
    val link: String,
//    val media_content: List<String>?,
//    val clean_url: String?,
//    val language: String?
)