package com.example.newsapiapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import checkForInternet
import com.example.newsapiapp.ViewModel.NewsLoaderViewModel
import com.example.newsapiapp.domain.Article
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import localSearch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    private val viewModel: NewsLoaderViewModel by viewModels()
    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val isRefreshing = viewModel.isRefreshing.collectAsState()
            val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing.value)
            viewModel.loadNews()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.loadNews() }) {
                        Mainscreen(navController = navController)
                    }
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
                            .let { val encodedArticle = it
                                val replacedArticle = encodedArticle?.replace(Regex("%"), "%25")
                                URLDecoder.decode(replacedArticle, StandardCharsets.UTF_8.toString()) }
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
                val isLoading = viewModel.isLoading.observeAsState().value!!
                if (isLoading) {
                    ProgressIndicator()
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FilterButton(navController = navController)
                        SearchBar(onSearch = {
                            if (checkForInternet(context)) {
                                viewModel.loadNewsWithSearch(it)
                            }
                            else {
                                localSearch(viewModel.list.value!!, it)
                            }
                        })
                    }
                }
                ListOfArticles(
                    list = viewModel.list.observeAsState().value!!,
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
                ButtonFilter(navController = navController, category = "Business", onFilter = { viewModel.loadNewsWithFilter(it)})
                ButtonFilter(navController = navController, category = "General", onFilter = { viewModel.loadNewsWithFilter(it)})
                ButtonFilter(navController = navController, category = "Entertainment", onFilter = { viewModel.loadNewsWithFilter(it)})
                ButtonFilter(navController = navController, category = "Health", onFilter = { viewModel.loadNewsWithFilter(it)})
            }
        }
    }





}


