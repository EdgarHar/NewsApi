package com.example.newsapiapp.Data

import com.example.newsapiapp.domain.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

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
}