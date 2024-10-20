package com.example.dailynews.data.repository

import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceXEntity
import com.example.dailynews.data.network.NetworkResult
import com.example.dailynews.data.network.model.SourceX
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
  fun getTopHeadlines(): Flow<NetworkResult<List<ArticleEntity>>>
  fun searchNews(query: String): Flow<NetworkResult<List<ArticleEntity>>>
  fun getSources(): Flow<NetworkResult<List<SourceXEntity>>>
}