package com.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.dailynews.data.db.DailyNewsDatabase
import com.example.dailynews.data.network.DailyNewsService
import com.example.dailynews.data.repository.LocalDataSourceImpl
import com.example.dailynews.data.repository.NewsRepositoryImpl
import com.example.dailynews.data.repository.RemoteDataSource
import com.example.dailynews.ui.components.MyBottomNavigationBar
import com.example.dailynews.ui.components.MyNavigationHost
import com.example.dailynews.ui.theme.DailyNewsTheme
import com.example.dailynews.utils.MyViewModelFactory
import com.example.dailynews.viewmodels.DailyNewsViewModel
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
  private lateinit var newsViewModel: DailyNewsViewModel

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val newsDatabase = DailyNewsDatabase.getInstance(this)

    newsViewModel = ViewModelProvider(
      this,
      MyViewModelFactory(
        NewsRepositoryImpl(
          LocalDataSourceImpl(
            newsDatabase.articleDao(), newsDatabase.sourceDao(), newsDatabase.sourceXDao(),
            Dispatchers.IO
          ),
          RemoteDataSource(DailyNewsService.getApiService(), Dispatchers.IO), Dispatchers.IO
        )
      )
    )[DailyNewsViewModel::class]

    setContent {
      val navController = rememberNavController()

      DailyNewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
          bottomBar = {
            MyBottomNavigationBar(navController = navController)
          },
          topBar = {
            TopAppBar(title = {
              Text("Daily News")
            })
          }) { innerPadding ->
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            MyNavigationHost(navHostController = navController, newsViewModel = newsViewModel)
          }
        }
      }
    }
  }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  DailyNewsTheme {
    Greeting("Android")
  }
}