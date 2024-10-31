package com.example.dailynews.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.ui.home.NewsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  searchResults: List<ArticleEntity>,
  onArticleBookmarkChange: (articleEntity: ArticleEntity, addOrRemove: Int) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Top
  ) {
    SearchBar(
      modifier = modifier
        .fillMaxWidth(),
      query = searchQuery,
      onQueryChange = onSearchQueryChange,
      windowInsets = WindowInsets(top = 0.dp),
      active = true,
      placeholder = {
        Text("Search...")
      },
      leadingIcon = {
        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
      },
      trailingIcon = {

      },
      onActiveChange = {

      },
      onSearch = {

      },
    ) {
      LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
      ) {
        items(searchResults) { article ->
          NewsItem(
            article = article, onArticleBookmarkChange = onArticleBookmarkChange
          )
        }
      }
//      Text("Work in progress", style = MaterialTheme.typography.headlineMedium)
    }
  }
}