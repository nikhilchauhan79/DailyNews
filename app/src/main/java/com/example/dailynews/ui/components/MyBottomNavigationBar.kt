package com.example.dailynews.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

val bottomNavItems = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Category,BottomNavItem.Sources,
  BottomNavItem.Bookmark)

@Composable
fun MyBottomNavigationBar(
  modifier: Modifier = Modifier,
  navController: NavController
) {
  val navBackStackEntry = navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry.value?.destination?.route

  NavigationBar(
    modifier = modifier
  ) {
    bottomNavItems.forEach { navItem ->
      NavigationBarItem(
        selected = navItem.route == currentRoute,
        onClick = {
          navController.navigate(navItem.route) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
          }
        },
        icon = {
          Icon(imageVector = navItem.icon, contentDescription = navItem.label)
        },
        label = {
          Text(navItem.label, style = MaterialTheme.typography.bodyMedium)
        }
      )
    }
  }
}