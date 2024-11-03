package com.example.dailynews.ui.uiState

import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult

data class HomeUiState(
  var allArticles: List<ArticleEntity> = listOf(),
  var fetchTopHeadlines: NetworkResult<List<ArticleEntity>>? = null
)
