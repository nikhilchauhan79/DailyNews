package com.example.dailynews.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.ui.home.NewsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  searchResults: List<ArticleEntity>
) {
    SearchBar(
      modifier = modifier,
      query = searchQuery,
      onQueryChange = onSearchQueryChange,
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
          NewsItem(article = article)
        }
      }
//      Text("Work in progress", style = MaterialTheme.typography.headlineMedium)
    }
}