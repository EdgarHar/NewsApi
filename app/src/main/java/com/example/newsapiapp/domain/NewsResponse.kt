package com.example.newsapiapp.domain

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>
)


data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val urlToImage: String,
    val description: String,
) : java.io.Serializable

data class Source(
    val id: String,
    val name: String
)