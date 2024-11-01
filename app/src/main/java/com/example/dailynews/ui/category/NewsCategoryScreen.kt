package com.example.dailynews.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.dailynews.MainActivity
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.ui.components.MyCircularIndicator
import com.example.dailynews.ui.home.NewsItem

@Composable
fun ShowNewsForCategory(
  modifier: Modifier = Modifier,
  selectedTabIndex: Int,
  onTabSelected: (Int) -> Unit,
  articleForCategory: NetworkResult<List<ArticleEntity>>?,
  onArticleBookmarkChange: (articleEntity: ArticleEntity, addOrRemove: Int) -> Unit
) {
  if (articleForCategory != null) {
    when (articleForCategory) {
      is NetworkResult.Failed -> {

      }

      is NetworkResult.Loading -> {
        MyCircularIndicator()
      }

      is NetworkResult.Success -> {
        NewsCategoryScreen(
          modifier,
          selectedTabIndex,
          onTabSelected,
          articleForCategory.response ?: listOf(),
          onArticleBookmarkChange
        )
      }
    }
  }
}

@Composable
fun NewsCategoryScreen(
  modifier: Modifier = Modifier,
  selectedTabIndex: Int,
  onTabSelected: (Int) -> Unit,
  articleForCategory: List<ArticleEntity>,
  onArticleBookmarkChange: (articleEntity: ArticleEntity, addOrRemove: Int) -> Unit
) {

  Column(
    modifier = modifier.fillMaxSize()
  ) {
    ScrollableTabRow(
      selectedTabIndex = selectedTabIndex,
    ) {
      MainActivity.newsCategories.forEachIndexed { index, newsCategory ->
        Tab(
          selected = selectedTabIndex == index,
          onClick = {
            onTabSelected(index)
          },
          icon = {
            Icon(
              imageVector = ImageVector.vectorResource(newsCategory.icon),
              contentDescription = null
            )
          }
        )
      }
    }

    LazyColumn(
      contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(articleForCategory) { article ->
        NewsItem(
          article = article, onArticleBookmarkChange = onArticleBookmarkChange
        )
      }
    }
  }
}
