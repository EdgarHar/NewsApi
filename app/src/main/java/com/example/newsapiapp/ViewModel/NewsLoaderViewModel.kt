package com.example.newsapiapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.data.NewsApi
import com.example.newsapiapp.data.RetrofitInstance
import com.example.newsapiapp.domain.Article
import com.example.newsapiapp.domain.NewsResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsLoaderViewModel : ViewModel() {
    private val _list: MutableLiveData<List<Article>> = MutableLiveData(listOf())
    val list: LiveData<List<Article>> = _list

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val api: NewsApi = RetrofitInstance.api

    fun loadNews() {
        viewModelScope.launch {
            delay(1000)
            _list.postValue(
                mapToArticles(api.getNews("us", "5189442ea3fd472b94ba50c569f42552"))
                    ?: listOf()
            )
            _isLoading.postValue(false)
        }
    }

    private fun mapToArticles(news: Response<NewsResponse>): List<Article>? {
        return news.body()?.articles
    }
}