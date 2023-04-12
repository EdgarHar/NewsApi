package com.example.newsapiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.newsapiapp.ViewModel.NewsLoaderViewModel

class MainActivity : ComponentActivity() {
    private val newsLoader: NewsLoaderViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsLoader.loadNews()
        setContent {
            Column {
                val isLoading = newsLoader.isLoading.observeAsState().value!!
                if (isLoading) {
                    ProgressIndicator()
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    SearchBar(onSearch = { s -> Unit })
                }
                ListOfArticles(list = newsLoader.list.observeAsState().value!!)
            }

        }
    }

}


