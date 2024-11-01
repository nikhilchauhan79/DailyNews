package com.example.dailynews.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
  val route: String,
  val icon: ImageVector,
  val label: String
) {
  data object Home : BottomNavItem("home", Icons.Outlined.Home, "Home")
  data object Search : BottomNavItem("search", Icons.Outlined.Search, "Search")
  data object Sources : BottomNavItem("sources", Icons.Outlined.Info, "Sources")
  data object Category : BottomNavItem("category", Icons.Outlined.Build, "Category")
  data object Bookmark : BottomNavItem("bookmark", Icons.Outlined.FavoriteBorder, "Bookmark")
}