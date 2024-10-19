package com.example.dailynews.data.repository

import androidx.lifecycle.LiveData
import com.example.dailynews.data.db.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
  fun getAllArticles(): LiveData<List<ArticleEntity>>
  fun getAllArticlesFlow(): Flow<List<ArticleEntity>>
  suspend fun insertArticle(articleEntity: ArticleEntity)
  suspend fun insertAllArticles(articleEntities: List<ArticleEntity>)
  suspend fun deleteAllArticles()
}