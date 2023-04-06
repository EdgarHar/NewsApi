package com.example.newsapiapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.Data.NewsApi
import com.example.newsapiapp.Data.RetrofitInstance
import com.example.newsapiapp.domain.Article
import com.example.newsapiapp.domain.NewsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsLoaderViewModel: ViewModel() {
    private val list: MutableLiveData<List<Article>> = MutableLiveData(listOf())
    private val api: NewsApi = RetrofitInstance.api
    val articleList: LiveData<List<Article>> = list

    fun loadNews() {
        viewModelScope.launch {
            list.postValue(mapToArticles(api.getNews("us", "5189442ea3fd472b94ba50c569f42552"))
                ?: listOf())
        }
    }

    private fun mapToArticles(news: Response<NewsResponse>): List<Article>? {
        return news.body()?.articles

    }
}