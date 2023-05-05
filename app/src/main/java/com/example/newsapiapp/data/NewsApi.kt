package com.example.newsapiapp.data

import com.example.newsapiapp.domain.NewsResponse
import retrofit2.Response
import retrofit2.http.*

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("apiKey") key: String
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") key: String
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getNewsSearchQuery(
        @Query("country") country: String,
        @Query("q") query: String,
        @Query("apiKey") category: String,
    ): Response<NewsResponse>
}