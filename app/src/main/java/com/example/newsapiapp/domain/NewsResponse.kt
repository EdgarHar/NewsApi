package com.example.newsapiapp.domain


data class NewsResponse (
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>
)

class Article (
    val author: String,
    val title: String,
    val urlToImage: String
)