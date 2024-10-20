package com.example.dailynews.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.ui.home.HandleArticles
import com.example.dailynews.ui.search.SearchScreen
import com.example.dailynews.ui.sources.SourcesScreen
import com.example.dailynews.viewmodels.DailyNewsViewModel

@Composable
fun MyNavigationHost(
  modifier: Modifier = Modifier,
  navHostController: NavHostController,
  newsViewModel: DailyNewsViewModel
) {
  val articles = newsViewModel.topHeadlinesStateFlow.collectAsState(NetworkResult.Loading())
  val searchResults = newsViewModel.searchResult.collectAsStateWithLifecycle()

  NavHost(navHostController, startDestination = BottomNavItem.Home.route) {
    composable(BottomNavItem.Home.route) {
      HandleArticles(articles.value, modifier)
    }

    composable(BottomNavItem.Search.route) {
      SearchScreen(
        modifier, newsViewModel.searchQuery.value,
        { query ->
          newsViewModel.setSearchQuery(query)
        }, searchResults.value
      )
    }

    composable(BottomNavItem.Sources.route) {
      SourcesScreen(modifier)
    }
  }
}