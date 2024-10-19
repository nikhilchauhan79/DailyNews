package com.example.dailynews.data.repository

import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
  fun getTopHeadlines(): Flow<NetworkResult<List<ArticleEntity>>>
}