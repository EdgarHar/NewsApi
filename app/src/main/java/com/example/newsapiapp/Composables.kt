package com.example.newsapiapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsapiapp.domain.Article
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DisplayArticle(navController: NavController, article: Article) {

    Card(
        shape = RectangleShape,
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                val myObjectString = Gson().toJson(article)
                val encode = URLEncoder.encode(myObjectString, StandardCharsets.UTF_8.toString())
                navController.navigate("articleDetails/$encode")
            }
    ) {
        Column {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            article.title?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun DisplayArticleDetails(article: Article, navController: NavController) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.DarkGray)
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                article.author?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                article.source?.let {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.height(16.dp))

            article.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            article.content?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun ListOfArticles(list: List<Article>, navController: NavController) {
    LazyColumn {
        if (list.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "No Results found")
                }
            }
        } else {
            items(list) {
                DisplayArticle(article = it, navController = navController)
            }
        }
    }
}


@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search") },
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            leadingIcon = {
                IconButton(onClick = { onSearch(query) }) {
                    Icon(Icons.Default.Search, "Search")
                }
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Default.Clear, "Remove")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(100.dp),
            singleLine = true
        )
    }
}


@Composable
fun FilterButton(navController: NavController) {
    IconButton(onClick = { navController.navigate("filter") }) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Filter",
            modifier = Modifier
                .padding(10.dp),

        )
    }
}

@Composable
fun ButtonFilter(navController: NavController, category: String, onFilter: (String) -> Unit) {
    Button(
        onClick = {
            navController.popBackStack().also { onFilter(category) }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = category, style = MaterialTheme.typography.h5)
    }
}

@Composable
fun ProgressIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
