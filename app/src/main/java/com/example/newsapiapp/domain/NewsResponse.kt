package com.example.newsapiapp.domain

import com.google.gson.annotations.SerializedName


data class NewsResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val articles: List<Article>
)

data class Article(
    @SerializedName("source")
    val source: Source,
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("urlToImage")
    val urlToImage: String,
    @SerializedName("content")
    val content: String,
) : java.io.Serializable

data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
