package com.example.newsapiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapiapp.ViewModel.NewsLoaderViewModel
import com.example.newsapiapp.domain.Article

class MainActivity : ComponentActivity() {
    private val newsLoader: NewsLoaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            newsLoader.loadNews()
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    SearchBar(onSearch = { s -> Unit })
                }
                ListOfArticles(list = newsLoader.articleList.observeAsState().value!!)
            }

        }
    }

}


