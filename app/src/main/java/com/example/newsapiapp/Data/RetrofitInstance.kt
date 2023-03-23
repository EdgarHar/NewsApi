package com.example.newsapiapp.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/"
const val VERSION = "v2"

object RetrofitInstance {

    val api: NewsApi by lazy {
        Retrofit.Builder()
            .baseUrl("$BASE_URL$VERSION/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

}