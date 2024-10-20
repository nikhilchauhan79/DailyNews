package com.example.dailynews.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DailyNewsViewModel(
  private val newsRepository: NewsRepository
) : ViewModel() {
  private val _topHeadlinesMutableStateFlow: MutableStateFlow<NetworkResult<List<ArticleEntity>>?> =
    MutableStateFlow(null)
  val topHeadlinesStateFlow: StateFlow<NetworkResult<List<ArticleEntity>>?> =
    _topHeadlinesMutableStateFlow

  private val _searchResults: MutableStateFlow<List<ArticleEntity>> = MutableStateFlow(listOf())

  private val _searchQuery = mutableStateOf("")

  val searchQuery: State<String> = _searchQuery

  val searchResult: StateFlow<List<ArticleEntity>> = _searchResults

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

  private fun searchNews(searchQuery: String) {
    viewModelScope.launch {
      newsRepository.searchNews(searchQuery).collectLatest { networkResult ->
        when (networkResult) {
          is NetworkResult.Failed -> {

          }

          is NetworkResult.Loading -> {

          }

          is NetworkResult.Success -> {
            _searchResults.value = networkResult.response ?: listOf()
          }
        }
      }
    }
  }

  fun setSearchQuery(newQuery: String) {
    _searchQuery.value = newQuery
    if (newQuery.isNotBlank()) {
      searchNews(newQuery)
    }
  }

  fun setSearchResults(newList: List<ArticleEntity>) {
    _searchResults.value = newList
  }
}