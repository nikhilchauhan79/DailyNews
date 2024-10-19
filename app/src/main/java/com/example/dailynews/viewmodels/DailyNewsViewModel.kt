package com.example.dailynews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DailyNewsViewModel(
  private val newsRepository: NewsRepository
) : ViewModel() {
  private val _topHeadlinesMutableStateFlow: MutableStateFlow<NetworkResult<List<ArticleEntity>>?> =
    MutableStateFlow(null)

  val topHeadlinesStateFlow: StateFlow<NetworkResult<List<ArticleEntity>>?> =
    _topHeadlinesMutableStateFlow

  init {
    getAllTopHeadlines()
  }

  private fun getAllTopHeadlines() {
    viewModelScope.launch {
      newsRepository.getTopHeadlines().collect { result ->
        _topHeadlinesMutableStateFlow.value = result
      }
    }
  }


//  dailyNewsRepository.getAllArticles().switchMap { articles ->
//    val sources = ArrayList<SourceEntity>()
//    articles.forEach { article ->
//      article.sourceEntity?.let { safeSource -> sources.add(safeSource) }
//    }
//    dailyNewsRepository.insertAllArticles(articles)
//  }
}