package com.example.dailynews.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.MainActivity
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceXEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.network.enums.NewsCategory
import com.example.dailynews.data.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DailyNewsViewModel(
  private val newsRepository: NewsRepository
) : ViewModel() {
  private val _topHeadlinesMutableStateFlow: MutableStateFlow<NetworkResult<List<ArticleEntity>>?> =
    MutableStateFlow(null)
  val topHeadlinesStateFlow: StateFlow<NetworkResult<List<ArticleEntity>>?> =
    _topHeadlinesMutableStateFlow

  private val _allArticlesMutableStateFlow : MutableStateFlow<List<ArticleEntity>> = MutableStateFlow(listOf())
  val allArticlesStateFlow : StateFlow<List<ArticleEntity>> = _allArticlesMutableStateFlow

  private val _articlesForCategoryMutableStateFlow: MutableStateFlow<NetworkResult<List<ArticleEntity>>?> =
    MutableStateFlow(null)

  val articlesForCategoryStateFlow: StateFlow<NetworkResult<List<ArticleEntity>>?> =
    _articlesForCategoryMutableStateFlow

  private val _searchResults: MutableStateFlow<List<ArticleEntity>> = MutableStateFlow(listOf())
  private val _favouriteArticles: MutableStateFlow<List<ArticleEntity>> = MutableStateFlow(listOf())

  val bookmarkDialogState = mutableStateOf(false)
  val selectedTab = mutableIntStateOf(0)

  val newsCategoryState: MutableState<NewsCategory> = mutableStateOf(NewsCategory.General)

  val favouriteArticles = _favouriteArticles.asStateFlow()

  private val _searchQuery = mutableStateOf("")
  private var searchJob: Job? = null

  val searchQuery: State<String> = _searchQuery
  val searchResult: StateFlow<List<ArticleEntity>> = _searchResults

  private val _sourcesMutableStateFlow: MutableStateFlow<List<SourceXEntity>> =
    MutableStateFlow(listOf())

  val sourcesStateFlow = _sourcesMutableStateFlow.asStateFlow()

  init {
    getAllTopHeadlines()
    getTopHeadlinesFromDB()
    getAllSources()
    getFavouriteArticles()
  }

  private fun getAllTopHeadlines() {
    viewModelScope.launch {
      newsRepository.getTopHeadlines().collect { result ->
        _topHeadlinesMutableStateFlow.value = result
      }
    }
  }

  private fun getTopHeadlinesFromDB() {
    viewModelScope.launch {
      newsRepository.getAllArticlesFromDB().collect {
        _allArticlesMutableStateFlow.value = it
      }
    }
  }

  fun getArticlesForCategory(category: NewsCategory) {
    viewModelScope.launch {
      newsRepository.getNewsForCategory(category).collect { result ->
        _articlesForCategoryMutableStateFlow.value = result
      }
    }
  }

  fun changeSelectedTab(newIndex: Int) {
    selectedTab.intValue = newIndex
    val category = MainActivity.newsCategories.find {
      it.index == newIndex
    } ?: NewsCategory.General

//    newsCategoryState.value = category
    getArticlesForCategory(category = category)
  }

  private fun searchNews(searchQuery: String) {
    searchJob?.cancel()

    searchJob = viewModelScope.launch {
      newsRepository.searchNews(searchQuery).collectLatest { networkResult ->
        when (networkResult) {
          is NetworkResult.Failed -> {

          }

          is NetworkResult.Loading -> {

          }

          is NetworkResult.Success -> {
            Log.i(TAG, "searchNews: got response")
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
    } else {
      _searchResults.value = listOf()
    }
  }

  fun getAllSources() {
    viewModelScope.launch {
      newsRepository.getSources().collect { networkResult ->
        when (networkResult) {
          is NetworkResult.Failed -> {

          }

          is NetworkResult.Loading -> {

          }

          is NetworkResult.Success -> {
            _sourcesMutableStateFlow.value = networkResult.response ?: listOf()
          }
        }
      }
    }
  }

  private fun getFavouriteArticles() {
    viewModelScope.launch {
      newsRepository.getBookmarkedArticles().collect { list ->
        _favouriteArticles.value = list
      }
    }
  }

  fun bookmarkArticle(articleEntity: ArticleEntity, addOrRemove: Int) {
    viewModelScope.launch {
      newsRepository.bookmarkArticle(articleEntity, addOrRemove)
    }
  }

  fun setSearchResults(newList: List<ArticleEntity>) {
    _searchResults.value = newList
  }

  companion object {
    const val TAG = "DailyNewsViewModel"
  }
}