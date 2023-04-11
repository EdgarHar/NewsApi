package com.example.newsapiapp

import android.graphics.drawable.Icon
import android.inputmethodservice.Keyboard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapiapp.domain.Article

@Composable
fun DisplayArticle(article: Article) {
    Card(
        shape = RectangleShape,
        elevation = 4.dp,
        modifier = Modifier.padding(16.dp)
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
            Text(
                text = article.title,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ListOfArticles(list: List<Article>) {
    LazyColumn {
        items(list) {
            DisplayArticle(article = it)
        }
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    Row (modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = { onSearch(query) },
            content = { Icon(Icons.Filled.Search, "Search") },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search") },
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            shape = RoundedCornerShape(100.dp),
            singleLine = true
        )
    }
}