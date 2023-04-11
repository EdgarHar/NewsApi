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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.newsapiapp.ViewModel.NewsLoaderViewModel
import com.example.newsapiapp.domain.Article
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
                composable(route = "articleDetails/{article}",
                    arguments = listOf(
                        navArgument("article") {
                            type = NavType.StringType
                        }
                    )
                )
                {
                    val articleJson =
                        it.arguments?.getString("article")
                            .let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
                    val articleObject = Gson().fromJson(articleJson, Article::class.java)
                    DisplayArticleDetails(article = articleObject, navController = navController)
                }
            }
        }
    }


    @Composable
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    fun Mainscreen(navController: NavController) {
        Scaffold(topBar = {
            TopAppBar {
                Text(
                    text = "News!",
                    style = MaterialTheme.typography.h5
                )
            }
        }) {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FilterButton(navController = navController)
                        SearchBar(onSearch = {}, onFilterClick = {})
                    }
                }
                ListOfArticles(
                    list = newsLoader.articleList.observeAsState().value!!,
                    navController = navController
                )
            }
        }
    }

    @Composable
    fun FilterScreen(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Select category", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonFilter(navController = navController, category = "Business")
                ButtonFilter(navController = navController, category = "General")
                ButtonFilter(navController = navController, category = "Entertainment")
                ButtonFilter(navController = navController, category = "Health")
            }
        }
    }

    @Composable
    private fun ButtonFilter(navController: NavController, category: String) {
        Button(
            onClick = {
                navController.popBackStack().also { newsLoader.loadNewsWithFilter(category) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = category, style = MaterialTheme.typography.h5)
        }
    }

}


