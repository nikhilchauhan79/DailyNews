package com.example.dailynews.data.repository

import androidx.lifecycle.LiveData
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceEntity
import com.example.dailynews.data.db.entities.SourceXEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
  fun getAllArticles(): LiveData<List<ArticleEntity>>
  fun getAllArticlesFlow(): Flow<List<ArticleEntity>>
  fun getAllSources(): Flow<List<SourceXEntity>>
  fun getAllBookmarkedArticles(): Flow<List<ArticleEntity>>
  suspend fun insertArticle(articleEntity: ArticleEntity)
  suspend fun insertAllArticles(articleEntities: List<ArticleEntity>)
  suspend fun bookmarkArticle(articleEntity: ArticleEntity, addOrRemove: Int)
  suspend fun insertAllSources(sources: List<SourceXEntity>)
  suspend fun deleteAllArticles()
  suspend fun getSourcesByArticleID(articleID: String): SourceEntity
}