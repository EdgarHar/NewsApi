package com.example.newsapiapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.newsapiapp.ViewModel.NewsLoaderViewModel
import com.example.newsapiapp.domain.Article

class MainActivity : ComponentActivity() {
    private val newsLoader: NewsLoaderViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            newsLoader.loadNews()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    Mainscreen(navController = navController)
                    }
                composable("filter") {
                    FilterScreen(navController = navController)
                }
            }
        }
    }


    @Composable
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    fun Mainscreen(navController: NavController) {
        Scaffold(topBar = { TopAppBar { Text(text = "News!") } }) {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        FilterButton(navController = navController)
                        SearchBar(onSearch = { s -> Unit }, onFilterClick = {})
                    }
                }
                ListOfArticles(list = newsLoader.articleList.observeAsState().value!!)
            }
        }
    }

}


