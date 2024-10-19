package com.example.dailynews.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.utils.Utils

@Composable
fun HomeScreen(modifier: Modifier = Modifier, articles: List<ArticleEntity>) {
  LazyColumn(
    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp),
    modifier = modifier.fillMaxSize()
  ) {
    items(articles) { article ->
      NewsItem(article = article)
    }
  }
}


@Composable
fun NewsItem(modifier: Modifier = Modifier, article: ArticleEntity) {
  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    AsyncImage(
      model = article.urlToImage,
      contentScale = ContentScale.Crop,
      modifier = modifier.fillMaxWidth(),
      contentDescription = null
    )

    Text(
      article.sourceEntity?.name ?: "Unknown",
      modifier = Modifier.wrapContentWidth(),
      style = MaterialTheme.typography.bodySmall,
      fontWeight = FontWeight.Normal
    )

    Text(
      article.title ?: "Not Found", modifier = Modifier.fillMaxWidth(),
      style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
    )

    val formattedDate = if (article.publishedAt == null) {
      "Invalid Date"
    } else {
      Utils.formatDate(article.publishedAt)
    }

    Text(
      formattedDate, modifier = Modifier.wrapContentWidth(),
      style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light
    )
  }
}