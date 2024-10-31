package com.example.dailynews.ui.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dailynews.R
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.ui.components.MyCircularIndicator
import com.example.dailynews.utils.Utils

@Composable
fun FavouriteScreen(modifier: Modifier = Modifier, articles: List<ArticleEntity>,
                    onArticleBookmarkChange: (articleEntity: ArticleEntity, addOrRemove: Int) -> Unit
                    ) {
  LazyColumn(
    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = modifier.fillMaxSize()
  ) {
    items(articles) { article ->
      NewsItem(article = article, onArticleBookmarkChange = onArticleBookmarkChange)
    }
  }
}


@Composable
fun NewsItem(modifier: Modifier = Modifier, article: ArticleEntity,
             onArticleBookmarkChange: (articleEntity: ArticleEntity, addOrRemove: Int) -> Unit
             ) {
  OutlinedCard(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
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
        style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold
      )
      val formattedDate = if (article.publishedAt == null) {
        "Invalid Date"
      } else {
        Utils.formatDate(article.publishedAt)
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          formattedDate, modifier = Modifier.wrapContentWidth(),
          style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Light
        )

        IconButton(
          onClick = {
            val addOrRemove: Int = if (article.isBookmarked == 0) 1 else 0
            article.isBookmarked = addOrRemove
            onArticleBookmarkChange.invoke(article, addOrRemove)
          }
        ) {
          Icon(
            imageVector = ImageVector.vectorResource(if (article.isBookmarked == 1) R.drawable.bookmark_filled else R.drawable.baseline_bookmark_border_24),
            modifier = Modifier.size(32.dp),
            contentDescription = null
          )
        }
      }
    }
  }
}