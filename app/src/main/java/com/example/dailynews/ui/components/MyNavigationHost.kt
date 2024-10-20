package com.example.dailynews.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.ui.home.HandleArticles
import com.example.dailynews.ui.search.SearchScreen
import com.example.dailynews.ui.sources.SourcesScreen

@Composable
fun MyNavigationHost(
  modifier: Modifier = Modifier,
  navHostController: NavHostController,
  articles: NetworkResult<List<ArticleEntity>>?
) {
  NavHost(navHostController, startDestination = BottomNavItem.Home.route) {
    composable(BottomNavItem.Home.route) {
      HandleArticles(articles, modifier)
    }

    composable(BottomNavItem.Search.route) {
      SearchScreen(modifier)
    }

    composable(BottomNavItem.Sources.route) {
      SourcesScreen(modifier)
    }
  }
}