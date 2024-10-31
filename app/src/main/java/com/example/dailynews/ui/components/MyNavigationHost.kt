package com.example.dailynews.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.ui.favourite.FavouriteScreen
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
  val sourcesResults = newsViewModel.sourcesStateFlow.collectAsStateWithLifecycle()
  val favouriteResults = newsViewModel.favouriteArticles.collectAsStateWithLifecycle()

  NavHost(navHostController, startDestination = BottomNavItem.Home.route) {
    composable(BottomNavItem.Home.route) {
      HandleArticles(articles.value, modifier) { articleEntity, addOrRemove ->
        newsViewModel.bookmarkArticle(articleEntity, addOrRemove)
      }
    }

    composable(BottomNavItem.Search.route) {
      SearchScreen(
        modifier, newsViewModel.searchQuery.value,
        { query ->
          newsViewModel.setSearchQuery(query)
        }, searchResults.value
      ){ articleEntity, addOrRemove ->
        newsViewModel.bookmarkArticle(articleEntity, addOrRemove)
      }
    }

    composable(BottomNavItem.Sources.route) {
      SourcesScreen(modifier, sourcesResults.value)
    }

    composable(BottomNavItem.Bookmark.route) {
      FavouriteScreen(modifier, favouriteResults.value) { articleEntity, addOrRemove ->
        newsViewModel.bookmarkArticle(articleEntity, addOrRemove)
      }
    }
  }
}