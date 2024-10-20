package com.example.dailynews.data.repository

import androidx.lifecycle.LiveData
import com.example.dailynews.data.db.dao.ArticleDao
import com.example.dailynews.data.db.dao.SourceDao
import com.example.dailynews.data.db.entities.ArticleEntity
import com.example.dailynews.data.db.entities.SourceEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
  private val articleDao: ArticleDao,
  private val sourceDao: SourceDao,
  private val ioDispatcher: CoroutineDispatcher,
) : LocalDataSource {
  override fun getAllArticles(): LiveData<List<ArticleEntity>> {
    return articleDao.getAllArticles()
  }

  override fun getAllArticlesFlow(): Flow<List<ArticleEntity>> {
    return articleDao.getAllArticlesFlow()
  }

  override suspend fun insertArticle(articleEntity: ArticleEntity) {
    withContext(ioDispatcher) {
      articleDao.insertArticle(articleEntity)
      articleEntity.sourceEntity?.let { safeSource -> sourceDao.insertSource(safeSource) }
    }
  }

  override suspend fun insertAllArticles(articleEntities: List<ArticleEntity>) {
    withContext(ioDispatcher) {
      val sources: ArrayList<SourceEntity> = arrayListOf()
      articleEntities.forEach { ae ->
        ae.sourceEntity?.let { safeAe -> sources.add(safeAe) }
      }
      if (articleEntities.isNotEmpty() && sources.isNotEmpty()) {
//        deleteAllArticles()
      }
      articleDao.insertArticles(articleEntities)
      sourceDao.insertSources(sources)
    }
  }

  override suspend fun deleteAllArticles() {
    withContext(Dispatchers.IO) {
      articleDao.deleteAllArticles()
      sourceDao.deleteAllSources()
    }
  }

}